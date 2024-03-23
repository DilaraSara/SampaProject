package com.example.again;
import  org.springframework.web.client.RestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.crypto.codec.Base64;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.nio.charset.Charset;
@Configuration
@SpringBootApplication
public class AgainApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgainApplication.class, args);

        // RestTemplate oluştur
        RestTemplate restTemplate = new RestTemplate();

        // Sunucu URL'si
        String serverUrl = "http://10.0.60.30:2700/drk15/";
        String username = "MESSTAJER";
        String password = "12345";

        // Kimlik doğrulama bilgilerini eklemek için bir interceptor oluştur
        List<ClientHttpRequestInterceptor> interceptors = Collections.singletonList(
                new BasicAuthInterceptor(username, password)
        );
        restTemplate.setInterceptors(interceptors);

        // Sunucuya GET isteği gönder
        ResponseEntity<String> response = restTemplate.getForEntity(serverUrl, String.class);

        // Yanıtı alma
        String responseBody = response.getBody();

        // Yanıtı işleme veya yazdırma
        System.out.println("Sunucu Yanıtı: " + responseBody);
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    static class BasicAuthInterceptor implements ClientHttpRequestInterceptor {
        private final String username;
        private final String password;

        BasicAuthInterceptor(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            HttpHeaders headers = request.getHeaders();
            String auth = username + ":" + password;
            byte[] authBytes = auth.getBytes(Charset.forName("UTF-8"));
            byte[] encodedAuth = Base64.encode(authBytes);
            headers.add("Authorization", "Basic " + new String(encodedAuth));
            return execution.execute(request, body);
        }
    }
}
