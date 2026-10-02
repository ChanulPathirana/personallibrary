package com.chanul.personallibrary.repository;

import com.chanul.personallibrary.model.GoogleDriveConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleDriveConnectionRepository
        extends JpaRepository<GoogleDriveConnection, Long> {
}