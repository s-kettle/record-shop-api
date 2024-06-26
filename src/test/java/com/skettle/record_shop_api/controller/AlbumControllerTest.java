package com.skettle.record_shop_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skettle.record_shop_api.model.Album;
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

    List<Album> albums = new ArrayList<>(List.of(
            new Album(1L,"Nothing But Thieves", Genre.ROCK, "Moral Panic", null, 2020, 12),
            new Album(2L,"Aphex Twin", Genre.ELECTRONIC, "Selected Ambient Works 85-92", null, 1992, 8),
            new Album(3L,"Miles Davis", Genre.JAZZ, "Some Kind of Blue", null, 1959, 2),
            new Album(4L,"Jessie Ware", Genre.POP, "What's Your Pleasure?", null, 2020, 6),
            new Album(5L,"Aphex Twin", Genre.ELECTRONIC, "Druqks", null, 2001, 6),
            new Album(6L,"Jon Hopkins", Genre.ELECTRONIC, "Insides", null, 2009, 10)
    ));

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(albumController).build();
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("GET /albums returns all albums")
    void getAllAlbumsTest() throws Exception {

        List<Album> smallAlbums = new ArrayList<>(List.of(
                new Album(1L,"Nothing But Thieves", Genre.ROCK, "Moral Panic", null, 2020, 12),
                new Album(2L,"Aphex Twin", Genre.ELECTRONIC, "Selected Ambient Works 85-92", null, 1992, 8)
        ));

        when(mockAlbumServiceImpl.getAllAlbums()).thenReturn(smallAlbums);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Moral Panic"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Selected Ambient Works 85-92"));

    }

    @Test
    @DisplayName("GET /albums/instock returns all albums in stock")
    void getAllAlbumsInStockTest() throws Exception {

        List<Album> albums = new ArrayList<>(List.of(
                new Album(1L,"Nothing But Thieves", Genre.ROCK, "Moral Panic", null, 2020, 12),
                new Album(2L,"Aphex Twin", Genre.ELECTRONIC, "Selected Ambient Works 85-92", null, 1992, 8),
                new Album(3L,"Miles Davis", Genre.JAZZ, "Some Kind of Blue", null, 1959, 2)
        ));

        when(mockAlbumServiceImpl.getAllAlbumsInStock()).thenReturn(albums);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums/instock"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stockQuantity").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].stockQuantity").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].stockQuantity").value(2));

    }

    @Test
    @DisplayName("GET /albums/instock returns 404 if no albums in stock")
    void getAllAlbumsInStockTest2() throws Exception {

        when(mockAlbumServiceImpl.getAllAlbumsInStock()).thenReturn(new ArrayList<>());

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/albums/instock"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("GET /albums/:id returns correct album by ID")
    void getAlbumByIdTest() throws Exception {

        Album testAlbum = new Album(4L, "Nothing But Thieves", Genre.ROCK, "Welcome to the DCC", null, 2023, 4);

        when(mockAlbumServiceImpl.getAlbumById(4L)).thenReturn(testAlbum);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums/4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Welcome to the DCC"));

    }

    @Test
    @DisplayName("GET albums?artist=* returns albums by artist")
    void getAllAlbumsArtistParamTest() throws Exception {

        List<Album> artistAlbums = new ArrayList<>(List.of(
                new Album(1L,"Aphex Twin", Genre.ELECTRONIC, "Selected Ambient Works 85-92", null, 1992, 8),
                new Album(2L,"Aphex Twin", Genre.ELECTRONIC, "Druqks", null, 2001, 6)
        ));

        when(mockAlbumServiceImpl.getAlbumsByArtist("Aphex+Twin")).thenReturn(artistAlbums);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums?artist=Aphex+Twin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artist").value("Aphex Twin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Selected Ambient Works 85-92"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].artist").value("Aphex Twin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Druqks"));

    }

    @Test
    @DisplayName("GET albums?genre=* returns albums by genre")
    void getAllAlbumsGenreParamTest() throws Exception {

        List<Album> genreAlbums = new ArrayList<>(List.of(
                new Album(1L, "Aphex Twin", Genre.ELECTRONIC, "Druqks", null, 2001, 6),
                new Album(2L, "Jon Hopkins", Genre.ELECTRONIC, "Insides", null, 2009, 10)
        ));

        when(mockAlbumServiceImpl.getAlbumByGenre("Electronic")).thenReturn(genreAlbums);
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums?genre=Electronic"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artist").value("Aphex Twin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value("ELECTRONIC"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].artist").value("Jon Hopkins"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].genre").value("ELECTRONIC"));

    }

    @Test
    @DisplayName("GET albums?year=* returns albums by year")
    void getAllAlbumsYearParamTest() throws Exception {

        List<Album> yearAlbums = new ArrayList<>(List.of(
                new Album(1L,"Nothing But Thieves", Genre.ROCK, "Moral Panic", null, 2020, 12),
                new Album(2L,"Jessie Ware", Genre.POP, "What's Your Pleasure?", null, 2020, 6)
        ));

        when(mockAlbumServiceImpl.getAlbumsByYear(2020)).thenReturn(yearAlbums);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums?year=2020"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artist").value("Nothing But Thieves"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].releaseYear").value(2020))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].artist").value("Jessie Ware"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].releaseYear").value(2020));

    }

    @Test
    @DisplayName("GET albums?name=* returns albums by name")
    void getAllAlbumsNameParamTest() throws Exception {

        List<Album> namedAlbum = new ArrayList<>(List.of(
                new Album(1L, "Nothing But Thieves", Genre.ROCK, "Welcome to the DCC", null, 2023, 4)
        ));

        when(mockAlbumServiceImpl.getAlbumByName("Welcome+to+the+DCC")).thenReturn(namedAlbum);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums?name=Welcome+to+the+DCC"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Welcome to the DCC"));

    }


    @Test
    @DisplayName("POST /album adds new album")
    void addNewAlbumTest() throws Exception {

        Album testAlbum = new Album(5L, "Jon Hopkins", Genre.ELECTRONIC, "Immunity", null, 2013, 5);

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

        Album albumToDelete = new Album(7L, "Half Moon Run", Genre.ALTERNATIVE, "Salt", null, 2023, 9);

        when(mockAlbumServiceImpl.deleteAlbum(albumToDelete.getId())).thenReturn(albumToDelete);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.delete("/api/v1/albums/" + albumToDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}