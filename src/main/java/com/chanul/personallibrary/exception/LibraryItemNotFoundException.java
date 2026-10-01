package com.chanul.personallibrary.exception;

public class LibraryItemNotFoundException extends RuntimeException {

    public LibraryItemNotFoundException(Long id) {
        super("Library item not found with id: " + id);
    }
}