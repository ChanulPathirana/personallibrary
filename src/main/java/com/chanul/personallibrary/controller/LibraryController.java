package com.chanul.personallibrary.controller;

import com.chanul.personallibrary.model.LibraryItem;
import com.chanul.personallibrary.service.LibraryItemService;
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
    public LibraryItem createItem(@RequestBody LibraryItem item) {
        return libraryItemService.createItem(item);
    }

    @GetMapping
    public List<LibraryItem> getAllItems() {
        return libraryItemService.getAllItems();
    }
}