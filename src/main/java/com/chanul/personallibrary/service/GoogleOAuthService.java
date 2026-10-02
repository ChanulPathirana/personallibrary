package com.chanul.personallibrary.service;

import com.chanul.personallibrary.model.GoogleDriveConnection;
import com.chanul.personallibrary.repository.GoogleDriveConnectionRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class GoogleOAuthService {

    private final GoogleDriveConnectionRepository connectionRepository;

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    public GoogleOAuthService(
            GoogleDriveConnectionRepository connectionRepository) {

        this.connectionRepository = connectionRepository;
    }

    public String getAccessToken() {

        GoogleDriveConnection connection =
                connectionRepository.findById(1L)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Google Drive is not connected"
                                )
                        );

        String refreshToken =
                connection.getRefreshToken();

        MultiValueMap<String, String> body =
                new LinkedMultiValueMap<>();

        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("refresh_token", refreshToken);
        body.add("grant_type", "refresh_token");

        RestClient restClient =
                RestClient.create();

        Map<?, ?> response =
                restClient.post()
                        .uri("https://oauth2.googleapis.com/token")
                        .body(body)
                        .retrieve()
                        .body(Map.class);

        if (response == null) {
            throw new RuntimeException(
                    "Failed to get access token from Google"
            );
        }

        Object accessTokenObject =
                response.get("access_token");

        if (accessTokenObject == null) {
            throw new RuntimeException(
                    "Google did not return an access token"
            );
        }

        return accessTokenObject.toString();
    }
}