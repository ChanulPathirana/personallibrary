package com.chanul.personallibrary.service;

import com.chanul.personallibrary.model.GoogleDriveConnection;
import com.chanul.personallibrary.repository.GoogleDriveConnectionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GoogleDriveConnectionService {

    private final GoogleDriveConnectionRepository repository;

    public GoogleDriveConnectionService(
            GoogleDriveConnectionRepository repository) {
        this.repository = repository;
    }

    public void saveConnection(String refreshToken) {

        GoogleDriveConnection connection =
                new GoogleDriveConnection();

        connection.setId(1L);
        connection.setRefreshToken(refreshToken);
        connection.setConnectedAt(LocalDateTime.now());

        repository.save(connection);
    }
    public boolean isConnected() {
        return repository.existsById(1L);
    }
    public void disconnect() {
        repository.deleteById(1L);
    }
}