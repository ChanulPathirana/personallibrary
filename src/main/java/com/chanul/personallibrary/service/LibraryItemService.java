package com.chanul.personallibrary.service;

import com.chanul.personallibrary.model.LibraryItem;
import com.chanul.personallibrary.repository.LibraryItemRepository;
import org.springframework.stereotype.Service;
import com.chanul.personallibrary.exception.LibraryItemNotFoundException;
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
    public LibraryItem getItemById(Long id) {
        return libraryItemRepository.findById(id)
                .orElseThrow(() -> new LibraryItemNotFoundException(id)); 
    }
    public LibraryItem updateItem(Long id, LibraryItem updatedItem) {

        LibraryItem existingItem = getItemById(id);

        existingItem.setTitle(updatedItem.getTitle());
        existingItem.setAuthor(updatedItem.getAuthor());
        existingItem.setType(updatedItem.getType());
        existingItem.setStatus(updatedItem.getStatus());

        return libraryItemRepository.save(existingItem);
    }

    public void deleteItem(Long id) {

        LibraryItem item = getItemById(id);

        libraryItemRepository.delete(item);
    }

    
}