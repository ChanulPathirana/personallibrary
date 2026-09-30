package com.chanul.personallibrary.service;

import com.chanul.personallibrary.model.LibraryItem;
import com.chanul.personallibrary.repository.LibraryItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryItemService {

    private final LibraryItemRepository libraryItemRepository;

    public LibraryItemService(LibraryItemRepository libraryItemRepository) {
        this.libraryItemRepository = libraryItemRepository;
    }

    public LibraryItem createItem(LibraryItem item) {
        return libraryItemRepository.save(item);
    }

    public List<LibraryItem> getAllItems() {
        return libraryItemRepository.findAll();
    }
}