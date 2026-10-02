package com.chanul.personallibrary.controller;

import com.chanul.personallibrary.service.GoogleDriveConnectionService;
import com.chanul.personallibrary.service.GoogleOAuthService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "google.client-id=test-client-id",
        "google.client-secret=test-client-secret",
        "google.redirect-uri=http://localhost:8080/api/google-drive/callback"
})
@AutoConfigureMockMvc
class GoogleDriveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GoogleDriveConnectionService connectionService;

    @MockitoBean
    private GoogleOAuthService googleOAuthService;
    

    @Test
    void shouldReturnConnectedTrue() throws Exception {

        when(connectionService.isConnected())
                .thenReturn(true);

        mockMvc.perform(
                get("/api/google-drive/status")
        )
        .andExpect(status().isOk())
        .andExpect(
                jsonPath("$.connected").value(true)
        );

        verify(connectionService).isConnected();
    }

    @Test
    void shouldReturnConnectedFalse() throws Exception {

        when(connectionService.isConnected())
                .thenReturn(false);

        mockMvc.perform(
                get("/api/google-drive/status")
        )
        .andExpect(status().isOk())
        .andExpect(
                jsonPath("$.connected").value(false)
        );

        verify(connectionService).isConnected();
    }

    @Test
    void shouldDisconnectGoogleDrive() throws Exception {

        mockMvc.perform(
                post("/api/google-drive/disconnect")
        )
        .andExpect(status().isOk())
        .andExpect(
                content().string(
                        "Google Drive disconnected successfully"
                )
        );

        verify(connectionService).disconnect();
    }
}