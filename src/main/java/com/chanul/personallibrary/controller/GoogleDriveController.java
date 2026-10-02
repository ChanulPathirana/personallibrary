package com.chanul.personallibrary.controller;

import com.chanul.personallibrary.service.GoogleDriveConnectionService;
import com.chanul.personallibrary.service.GoogleOAuthService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/google-drive")
public class GoogleDriveController {

    private final GoogleDriveConnectionService connectionService;
    private final GoogleOAuthService googleOAuthService;


    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    @Value("${google.client-secret}")
    private String clientSecret;

    public GoogleDriveController(
            GoogleDriveConnectionService connectionService,
         GoogleOAuthService googleOAuthService) {

        this.connectionService = connectionService;
        this.googleOAuthService = googleOAuthService;
    }

    @GetMapping("/connect")
    public ResponseEntity<Void> connectGoogleDrive() {

        String scope =
                "https://www.googleapis.com/auth/drive.file";

        String googleAuthUrl =
                "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id="
                + URLEncoder.encode(
                        clientId,
                        StandardCharsets.UTF_8
                )
                + "&redirect_uri="
                + URLEncoder.encode(
                        redirectUri,
                        StandardCharsets.UTF_8
                )
                + "&response_type=code"
                + "&scope="
                + URLEncoder.encode(
                        scope,
                        StandardCharsets.UTF_8
                )
                + "&access_type=offline"
                + "&prompt=consent";

        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(
                URI.create(googleAuthUrl)
        );

        return new ResponseEntity<>(
                headers,
                HttpStatus.FOUND
        );
    }

    @GetMapping("/callback")
    public ResponseEntity<String> googleCallback(
            @RequestParam("code") String code) {

        MultiValueMap<String, String> body =
                new LinkedMultiValueMap<>();

        body.add("code", code);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add(
                "grant_type",
                "authorization_code"
        );

        RestClient restClient =
                RestClient.create();

        Map<?, ?> response =
                restClient.post()
                        .uri(
                            "https://oauth2.googleapis.com/token"
                        )
                        .body(body)
                        .retrieve()
                        .body(Map.class);

        if (response == null) {
            return ResponseEntity
                    .internalServerError()
                    .body(
                        "Failed to receive tokens from Google"
                    );
        }

        Object refreshTokenObject =
                response.get("refresh_token");

        if (refreshTokenObject == null) {
            return ResponseEntity
                    .badRequest()
                    .body(
                        "Google did not return a refresh token"
                    );
        }

        String refreshToken =
                refreshTokenObject.toString();

        connectionService.saveConnection(
                refreshToken
        );

        return ResponseEntity.ok(
                "Google Drive connected successfully"
        );
    }
    @GetMapping("/status")
    public ResponseEntity<Map<String, Boolean>> getStatus() {

        boolean connected =
                connectionService.isConnected();

        return ResponseEntity.ok(
                Map.of("connected", connected)
        );
    }
    @GetMapping("/test-token")
    public ResponseEntity<String> testToken() {

        googleOAuthService.getAccessToken();

        return ResponseEntity.ok(
                "Access token received successfully"
        );
    }


}