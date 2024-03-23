package com.example.again;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class RemoteDataService {

    private final RestTemplate restTemplate;
    private final RemoteServerConfig remoteServerConfig;

    @Autowired
    public RemoteDataService(RestTemplate restTemplate, RemoteServerConfig remoteServerConfig) {
        this.restTemplate = restTemplate;
        this.remoteServerConfig = remoteServerConfig;
    }

    public String fetchDataFromRemoteServer(String serverUrl) {
        ResponseEntity<String> response = restTemplate.getForEntity(serverUrl, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Veri çekme başarısız oldu.");
        }
    }
    public String getDataFromRemoteServerWithAuthentication(String remoteServerUrl) {
        String username = remoteServerConfig.getUsername();
        String password = remoteServerConfig.getPassword();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                remoteServerUrl,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return "Sunucudan veri alınamadı.";
        }
    }
}
