package com.chanul.personallibrary.controller;

import com.chanul.personallibrary.model.ItemType;
import com.chanul.personallibrary.model.LibraryItem;
import com.chanul.personallibrary.model.ReadingStatus;
import com.chanul.personallibrary.service.GoogleDriveService;
import com.chanul.personallibrary.service.LibraryItemService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "google.client-id=test-client-id",
        "google.client-secret=test-client-secret",
        "google.redirect-uri=http://localhost:8080/api/google-drive/callback"
})
@AutoConfigureMockMvc
class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LibraryItemService libraryItemService;

    @MockitoBean
    private GoogleDriveService googleDriveService;

    @Test
    void shouldUploadPdfAndCreateLibraryItem() throws Exception {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "clean-code.pdf",
                        "application/pdf",
                        "fake-pdf-content".getBytes()
                );

        when(
                googleDriveService.uploadFile(any())
        ).thenReturn("fake-drive-file-id");

        LibraryItem savedItem = new LibraryItem();

        savedItem.setId(4L);
        savedItem.setTitle("Clean Code");
        savedItem.setAuthor("Robert C. Martin");
        savedItem.setType(ItemType.PDF);
        savedItem.setStatus(ReadingStatus.TO_READ);
        savedItem.setGoogleDriveFileId("fake-drive-file-id");
        savedItem.setGoogleDriveUrl(
                "https://drive.google.com/file/d/fake-drive-file-id/view"
        );

        when(
                libraryItemService.saveLibraryItem(any())
        ).thenReturn(savedItem);

        mockMvc.perform(
                multipart("/api/library/upload")
                        .file(file)
                        .param("title", "Clean Code")
                        .param("author", "Robert C. Martin")
                        .param("type", "PDF")
                        .param("status", "TO_READ")
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(4))
        .andExpect(jsonPath("$.title").value("Clean Code"))
        .andExpect(jsonPath("$.googleDriveFileId")
                .value("fake-drive-file-id"));
    }
}