package com.chanul.personallibrary.controller;

import com.chanul.personallibrary.model.ItemType;
import com.chanul.personallibrary.model.LibraryItem;
import com.chanul.personallibrary.model.ReadingStatus;
import com.chanul.personallibrary.service.LibraryItemService;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LibraryItemService libraryItemService;

    @Test
    void shouldGetAllLibraryItems() throws Exception {

        LibraryItem item = new LibraryItem();

        item.setId(1L);
        item.setTitle("Clean Code");
        item.setAuthor("Robert C. Martin");
        item.setType(ItemType.BOOK);
        item.setStatus(ReadingStatus.TO_READ);

        when(libraryItemService.getAllItems())
                .thenReturn(List.of(item));

        mockMvc.perform(get("/api/library"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title")
                        .value("Clean Code"))
                .andExpect(jsonPath("$[0].author")
                        .value("Robert C. Martin"));
    }
}