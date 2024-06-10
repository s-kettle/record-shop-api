package com.skettle.record_shop_api.service;

import com.skettle.record_shop_api.exceptions.AlbumNotFoundException;
import com.skettle.record_shop_api.model.Album;
import com.skettle.record_shop_api.model.Artist;
import com.skettle.record_shop_api.model.Genre;
import com.skettle.record_shop_api.repository.AlbumRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class AlbumServiceImplTest {

    @Mock
    AlbumRepository mockAlbumRepository;

    @InjectMocks
    AlbumServiceImpl albumService;

    @Test
    @DisplayName("getAllAlbums() returns all albums")
    void getAllAlbumsTest() {

        List<Album> albums = new ArrayList<>(List.of(
           new Album(1L, new Artist(1L, "Nothing But Thieves", null), Genre.ROCK, "Moral Panic", 2020, 12),
           new Album(2L, new Artist(2L, "Aphex Twin", null), Genre.ELECTRONIC, "Selected Ambient Works 85-92", 1992, 8),
           new Album(3L, new Artist(3L, "Miles Davis", null), Genre.JAZZ, "Some Kind of Blue", 1959, 2)
        ));

        when(mockAlbumRepository.findAll()).thenReturn(albums);

        List<Album> actualAlbums = albumService.getAllAlbums();

        assertEquals(albums, actualAlbums);

    }

    @Test
    @DisplayName("getAlbumById() returns album by ID")
    void getAlbumByIdTest() {

        Album testAlbum = new Album(4L, new Artist(1L, "Nothing But Thieves", null), Genre.ROCK, "Welcome to the DCC", 2023, 4);

        when(mockAlbumRepository.findById(4L)).thenReturn(Optional.of(testAlbum));

        Album actualAlbum = albumService.getAlbumById(4L);

        assertEquals(testAlbum, actualAlbum);

    }

    @Test
    @DisplayName("getAlbumById() throws AlbumNotFoundException with invalid ID")
    void getAlbumByIdExceptionTest() {

        assertThrows(AlbumNotFoundException.class, () -> {
            albumService.getAlbumById(100L);
        });

    }

    @Test
    @DisplayName("addNewAlbum() successfully persists album")
    void addNewAlbumTest() {

        Album testAlbum = new Album(5L, new Artist(4L, "Jon Hopkins", null), Genre.ELECTRONIC, "Immunity", 2013, 5);

        when(mockAlbumRepository.save(testAlbum)).thenReturn(testAlbum);

        Album actualAlbum = albumService.addNewAlbum(testAlbum);

        assertEquals(testAlbum, actualAlbum);

    }

    @Test
    @DisplayName("updateAlbum() updates and returns album")
    void updateAlbum() {

        Album testAlbum = new Album(6L, new Artist(5L, "Confidence Man", null), Genre.POP, "Tilt", 2022, 15);
        Album updatedAlbum = new Album(6L, new Artist(5L, "Confidence Man", null), Genre.POP, "Re-Tilt", 2022, 15);

        when(mockAlbumRepository.findById(6L)).thenReturn(Optional.of(testAlbum));

        Album returnedAlbum = albumService.updateAlbum(6L, updatedAlbum);

        assertEquals(updatedAlbum, returnedAlbum);

    }

    @Test
    @DisplayName("updateAlbum() throws AlbumNotFoundException with invalid ID")
    void updateAlbumExceptionTest() {

        Album updatedAlbum = new Album(6L, new Artist(5L, "Confidence Man", null), Genre.POP, "Re-Tilt", 2022, 15);

        assertThrows(AlbumNotFoundException.class, () -> {
            albumService.updateAlbum(100L, updatedAlbum);
        });

    }

    @Test
    @DisplayName("deleteAlbumById() deletes album")
    public void deleteAlbumTest() {

        Album albumToDelete = new Album(7L, new Artist(6L, "Half Moon Run", null), Genre.ALTERNATIVE, "Salt", 2023, 9);

        when(mockAlbumRepository.findById(albumToDelete.getId())).thenReturn(Optional.of(albumToDelete));

        Album returnedAlbum = albumService.deleteAlbum(albumToDelete.getId());

        verify(mockAlbumRepository, times(1)).deleteById(albumToDelete.getId());
    }

    @Test
    @DisplayName("deleteAlbumById() throws AlbumNotFoundException if ID not found")
    public void deleteAlbumExceptionTest() {

        assertThrows(AlbumNotFoundException.class, () -> {
            albumService.deleteAlbum(100L);
        });

        verify(mockAlbumRepository, times(0)).deleteById(100L);
    }
}
