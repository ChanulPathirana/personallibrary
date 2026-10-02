package com.chanul.personallibrary.service;

import com.chanul.personallibrary.model.GoogleDriveConnection;
import com.chanul.personallibrary.repository.GoogleDriveConnectionRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoogleDriveConnectionServiceTest {

    @Mock
    private GoogleDriveConnectionRepository repository;

    @InjectMocks
    private GoogleDriveConnectionService service;

    @Test
    void shouldReturnTrueWhenGoogleDriveIsConnected() {

        when(repository.existsById(1L))
                .thenReturn(true);

        boolean result = service.isConnected();

        assertTrue(result);

        verify(repository).existsById(1L);
    }

    @Test
    void shouldReturnFalseWhenGoogleDriveIsNotConnected() {

        when(repository.existsById(1L))
                .thenReturn(false);

        boolean result = service.isConnected();

        assertFalse(result);

        verify(repository).existsById(1L);
    }

    @Test
    void shouldSaveGoogleDriveConnection() {

        String refreshToken = "test-refresh-token";

        service.saveConnection(refreshToken);

        verify(repository).save(
                argThat(connection ->
                        connection.getId().equals(1L)
                        &&
                        connection.getRefreshToken()
                                .equals(refreshToken)
                        &&
                        connection.getConnectedAt() != null
                )
        );
    }

    @Test
    void shouldDisconnectGoogleDrive() {

        service.disconnect();

        verify(repository).deleteById(1L);
    }
}