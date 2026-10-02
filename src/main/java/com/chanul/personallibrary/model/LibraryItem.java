package com.chanul.personallibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class LibraryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;
    
    @NotNull(message = "Type is required")
    @Enumerated(EnumType.STRING)
    private ItemType type;
    @NotNull(message = "Type is required")

    @Enumerated(EnumType.STRING)
    private ReadingStatus status;
    private String googleDriveFileId;
    private String googleDriveUrl;

    public LibraryItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public ReadingStatus getStatus() {
        return status;
    }

    public void setStatus(ReadingStatus status) {
        this.status = status;
    }
    public String getGoogleDriveFileId() {
    return googleDriveFileId;
}

    public void setGoogleDriveFileId(String googleDriveFileId) {
        this.googleDriveFileId = googleDriveFileId;
    }

    public String getGoogleDriveUrl() {
        return googleDriveUrl;
    }

    public void setGoogleDriveUrl(String googleDriveUrl) {
        this.googleDriveUrl = googleDriveUrl;
    }
}