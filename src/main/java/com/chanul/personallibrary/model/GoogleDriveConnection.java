package com.chanul.personallibrary.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class GoogleDriveConnection {

    @Id
    private Long id;

    @Column(nullable = false, length = 2000)
    private String refreshToken;

    private LocalDateTime connectedAt;

    public GoogleDriveConnection() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(LocalDateTime connectedAt) {
        this.connectedAt = connectedAt;
    }
}