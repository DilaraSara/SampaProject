package com.example.again;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RemoteServerConfig {
    @Value("${remote.server.url}")
    private String serverUrl;

    @Value("${remote.server.username}")
    private String username;

    @Value("${remote.server.password}")
    private String password;

    public String getServerUrl() {
        return serverUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

