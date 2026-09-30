package com.chanul.personallibrary.repository;

import com.chanul.personallibrary.model.LibraryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryItemRepository extends JpaRepository<LibraryItem, Long> {

    
}