package com.chanul.personallibrary.repository;

import com.chanul.personallibrary.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibraryItemRepository extends JpaRepository<LibraryItem, Long> {
    List <LibraryItem> findByStatus(ReadingStatus status);

    
}