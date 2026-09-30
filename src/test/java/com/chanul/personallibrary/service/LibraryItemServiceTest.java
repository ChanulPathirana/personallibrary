package com.chanul.personallibrary.service;

import com.chanul.personallibrary.model.ItemType;
import com.chanul.personallibrary.model.LibraryItem;
import com.chanul.personallibrary.model.ReadingStatus;
import com.chanul.personallibrary.repository.LibraryItemRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LibraryItemServiceTest {

    @Mock
    private LibraryItemRepository libraryItemRepository;

    @InjectMocks
    private LibraryItemService libraryItemService;

    @Test
    void shouldCreateLibraryItem() {

        LibraryItem item = new LibraryItem();
        item.setTitle("Clean Code");
        item.setAuthor("Robert C. Martin");
        item.setType(ItemType.BOOK);
        item.setStatus(ReadingStatus.TO_READ);

        when(libraryItemRepository.save(item))
                .thenReturn(item);

        LibraryItem result =
                libraryItemService.createItem(item);

        assertEquals("Clean Code", result.getTitle());

        verify(libraryItemRepository).save(item);
    }
}