package com.skettle.record_shop_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skettle.record_shop_api.model.Album;
import com.skettle.record_shop_api.model.Artist;
import com.skettle.record_shop_api.model.Genre;
import com.skettle.record_shop_api.service.AlbumServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
class AlbumControllerTest {

    @Mock
    private AlbumServiceImpl mockAlbumServiceImpl;

    @InjectMocks
    private AlbumController albumController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(albumController).build();
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("GET /albums returns all albums")
    void getAllAlbumsTest() throws Exception {

        List<Album> albums = new ArrayList<>(List.of(
                new Album(1L, new Artist(1L, "Nothing But Thieves", null), Genre.ROCK, "Moral Panic", 2020, 12),
                new Album(2L, new Artist(2L, "Aphex Twin", null), Genre.ELECTRONIC, "Selected Ambient Works 85-92", 1992, 8),
                new Album(3L, new Artist(3L, "Miles Davis", null), Genre.JAZZ, "Some Kind of Blue", 1959, 2)
        ));

        when(mockAlbumServiceImpl.getAllAlbums()).thenReturn(albums);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Moral Panic"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Selected Ambient Works 85-92"));

    }

    @Test
    @DisplayName("GET /albums/:id returns correct album by ID")
    void getAlbumByIdTest() throws Exception {

        Album testAlbum = new Album(4L, new Artist(1L, "Nothing But Thieves", null), Genre.ROCK, "Welcome to the DCC", 2023, 4);

        when(mockAlbumServiceImpl.getAlbumById(4L)).thenReturn(testAlbum);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums/4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Welcome to the DCC"));

    }

    @Test
    @DisplayName("POST /album adds new album")
    void addNewAlbumTest() throws Exception {

        Album testAlbum = new Album(5L, new Artist(4L, "Jon Hopkins", null), Genre.ELECTRONIC, "Immunity", 2013, 5);

        when(mockAlbumServiceImpl.addNewAlbum(testAlbum)).thenReturn(testAlbum);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/v1/albums")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(testAlbum)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockAlbumServiceImpl, times(1)).addNewAlbum(testAlbum);

    }

    @Test
    @DisplayName("DELETE /album/:id deletes album")
    void deleteAlbumTest() throws Exception {

        Album albumToDelete = new Album(7L, new Artist(6L, "Half Moon Run", null), Genre.ALTERNATIVE, "Salt", 2023, 9);

        when(mockAlbumServiceImpl.deleteAlbum(albumToDelete.getId())).thenReturn(albumToDelete);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.delete("/api/v1/albums/" + albumToDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}