package com.chanul.personallibrary.repository;

import com.chanul.personallibrary.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibraryItemRepository extends JpaRepository<LibraryItem, Long> {
    List <LibraryItem> findByStatus(ReadingStatus status);

    List<LibraryItem> findByTitleContainingIgnoreCase(String title);

    List <LibraryItem> findByType(ItemType type);

    
}