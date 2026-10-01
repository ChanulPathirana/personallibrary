package com.chanul.personallibrary.controller;

import com.chanul.personallibrary.dto.*;
import com.chanul.personallibrary.model.LibraryItem;
import com.chanul.personallibrary.service.LibraryItemService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryItemService libraryItemService;

    public LibraryController(LibraryItemService libraryItemService) {
        this.libraryItemService = libraryItemService;
    }

    @PostMapping
    public ResponseEntity<LibraryItem> createItem(
            @Valid @RequestBody CreateLibraryItemRequest request) {

        LibraryItem createditem=libraryItemService.createItem(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createditem);

    }

    @GetMapping
    public ResponseEntity<List<LibraryItem>> getAllItems() {
        List <LibraryItem> items =  libraryItemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryItem> getItemById(@PathVariable Long id) {
        LibraryItem item = libraryItemService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibraryItem> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLibraryItemRequest item) {

        LibraryItem updateditem = libraryItemService.updateItem(id, item);
        return ResponseEntity.ok(updateditem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        libraryItemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}