package com.chanul.personallibrary.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.io.ByteArrayOutputStream;
@Service
public class GoogleDriveService {

    private final GoogleOAuthService googleOAuthService;

    public GoogleDriveService(
            GoogleOAuthService googleOAuthService) {

        this.googleOAuthService = googleOAuthService;
    }

   public String uploadFile(MultipartFile file) throws IOException {

    String accessToken =
            googleOAuthService.getAccessToken();

    String boundary = "personalLibraryBoundary";

    String metadataJson =
            "{\"name\":\"" + file.getOriginalFilename() + "\"}";

    byte[] fileBytes = file.getBytes();

    ByteArrayOutputStream outputStream =
            new ByteArrayOutputStream();

    outputStream.write(
            ("--" + boundary + "\r\n").getBytes()
    );

    outputStream.write(
            "Content-Type: application/json; charset=UTF-8\r\n\r\n"
                    .getBytes()
    );

    outputStream.write(metadataJson.getBytes());
    outputStream.write("\r\n".getBytes());

    outputStream.write(
            ("--" + boundary + "\r\n").getBytes()
    );

    outputStream.write(
            ("Content-Type: " + file.getContentType() + "\r\n\r\n")
                    .getBytes()
    );

    outputStream.write(fileBytes);
    outputStream.write("\r\n".getBytes());

    outputStream.write(
            ("--" + boundary + "--").getBytes()
    );

    RestClient restClient =
            RestClient.create();

    Map<?, ?> response =
            restClient.post()
                    .uri(
                            "https://www.googleapis.com/upload/drive/v3/files"
                                    + "?uploadType=multipart"
                                    + "&fields=id"
                    )
                    .header(
                            "Authorization",
                            "Bearer " + accessToken
                    )
                    .header(
                            "Content-Type",
                            "multipart/related; boundary=" + boundary
                    )
                    .body(outputStream.toByteArray())
                    .retrieve()
                    .body(Map.class);

    if (response == null ||
            response.get("id") == null) {

        throw new RuntimeException(
                "Google Drive upload failed"
        );
    }

    return response.get("id").toString();
}
}