package com.chanul.personallibrary.controller;

import com.chanul.personallibrary.dto.*;
import com.chanul.personallibrary.model.LibraryItem;
import com.chanul.personallibrary.service.LibraryItemService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryItemService libraryItemService;

    public LibraryController(LibraryItemService libraryItemService) {
        this.libraryItemService = libraryItemService;
    }

    @PostMapping
    public LibraryItem createItem(
            @Valid @RequestBody CreateLibraryItemRequest request) {

        return libraryItemService.createItem(request);
    }

    @GetMapping
    public List<LibraryItem> getAllItems() {
        return libraryItemService.getAllItems();
    }

    @GetMapping("/{id}")
    public LibraryItem getItemById(@PathVariable Long id) {
        return libraryItemService.getItemById(id);
    }

    @PutMapping("/{id}")
    public LibraryItem updateItem(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLibraryItemRequest item) {

        return libraryItemService.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        libraryItemService.deleteItem(id);
    }
}