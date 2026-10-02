package com.chanul.personallibrary.service;

import com.chanul.personallibrary.model.*;
import com.chanul.personallibrary.repository.LibraryItemRepository;
import org.springframework.stereotype.Service;
import com.chanul.personallibrary.exception.LibraryItemNotFoundException;
import com.chanul.personallibrary.dto.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class LibraryItemService {

    private final LibraryItemRepository libraryItemRepository;

    public LibraryItemService(LibraryItemRepository libraryItemRepository) {
        this.libraryItemRepository = libraryItemRepository;
    }

    public LibraryItem createItem(CreateLibraryItemRequest request) {
        LibraryItem item = new LibraryItem();
        item.setTitle(request.getTitle());
        item.setAuthor(request.getAuthor());
        item.setType(request.getType());
        item.setStatus(request.getStatus());

        return libraryItemRepository.save(item);
        
    }

    public List<LibraryItem> getAllItems() {
        return libraryItemRepository.findAll();
    }
    public LibraryItem getItemById(Long id) {
        return libraryItemRepository.findById(id)
                .orElseThrow(() -> new LibraryItemNotFoundException(id)); 
    }
    public LibraryItem updateItem(Long id, UpdateLibraryItemRequest request) {

        LibraryItem existingItem = getItemById(id);

        existingItem.setTitle(request.getTitle());
        existingItem.setAuthor(request.getAuthor());
        existingItem.setType(request.getType());
        existingItem.setStatus(request.getStatus());

        return libraryItemRepository.save(existingItem);
    }

    public void deleteItem(Long id) {

        LibraryItem item = getItemById(id);

        libraryItemRepository.delete(item);
    }

    public List<LibraryItem> getItemByStatus(ReadingStatus status){

        return libraryItemRepository.findByStatus(status);


    }

    public List<LibraryItem> getItemByTitle(String title){

        return libraryItemRepository.findByTitleContainingIgnoreCase(title);


    }
    public List<LibraryItem> getItemByType(ItemType type){

        return libraryItemRepository.findByType(type);


    }
    public Page<LibraryItem> getAllItemsByPage(Pageable pageable) {
        return libraryItemRepository.findAll(pageable);
    }
    public LibraryItem saveLibraryItem(LibraryItem item) {
        return libraryItemRepository.save(item);
    }


    
}