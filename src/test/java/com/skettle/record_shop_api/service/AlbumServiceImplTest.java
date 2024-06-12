package com.skettle.record_shop_api.service;

import com.skettle.record_shop_api.exceptions.AlbumNotFoundException;
import com.skettle.record_shop_api.exceptions.GenreNotAllowedException;
import com.skettle.record_shop_api.model.Album;
import com.skettle.record_shop_api.model.Genre;
import com.skettle.record_shop_api.repository.AlbumRepository;
import org.checkerframework.checker.units.qual.A;
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

    List<Album> albums = new ArrayList<>(List.of(
            new Album(1L, "Jon Hopkins", Genre.ELECTRONIC, "Singularity", 2018, 5),
            new Album(2L, "Four Tet", Genre.ELECTRONIC, "Three", 2024, 0),
            new Album(3L, "Paleface Swiss", Genre.METAL, "Fear & Dagger", 2022, 0),
            new Album(4L, "Nothing But Thieves", Genre.ROCK, "Moral Panic", 2020, 12),
            new Album(5L, "Aphex Twin", Genre.ELECTRONIC, "Selected Ambient Works 85-92", 1992, 8),
            new Album(6L, "Miles Davis", Genre.JAZZ, "Some Kind of Blue", 1959, 2),
            new Album(7L, "Thelonius Monk", Genre.JAZZ, "Straight, No Chaser", 1967, 7),
            new Album(8L, "Carly Rae Jepsen", Genre.POP, "Kiss", 2012, 4),
            new Album(9L, "Thelonius Monk", Genre.JAZZ, "Brilliant Corners", 1957, 18),
            new Album(10L, "Half Moon Run", Genre.ALTERNATIVE, "Salt", 2022, 0)
    ));

    @Test
    @DisplayName("getAllAlbums() returns all albums")
    void getAllAlbumsTest() {

        when(mockAlbumRepository.findAll()).thenReturn(albums);

        List<Album> actualAlbums = albumService.getAllAlbums();

        assertEquals(albums, actualAlbums);

    }

    @Test
    @DisplayName("getAlbumById() returns album by ID")
    void getAlbumByIdTest() {

        Album testAlbum = new Album(4L, "Nothing But Thieves", Genre.ROCK, "Welcome to the DCC", 2023, 4);

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
    @DisplayName("getAllAlbumsInStock() returns only albums in stock")
    void getAllAlbumsInStockTest() {

        List<Album> albumsInStock = new ArrayList<>(List.of(
                new Album(1L, "Jon Hopkins", Genre.ELECTRONIC, "Singularity", 2018, 5),
                new Album(4L, "Nothing But Thieves", Genre.ROCK, "Moral Panic", 2020, 12),
                new Album(5L, "Aphex Twin", Genre.ELECTRONIC, "Selected Ambient Works 85-92", 1992, 8),
                new Album(6L, "Miles Davis", Genre.JAZZ, "Some Kind of Blue", 1959, 2),
                new Album(7L, "Thelonius Monk", Genre.JAZZ, "Straight, No Chaser", 1967, 7),
                new Album(8L, "Carly Rae Jepsen", Genre.POP, "Kiss", 2012, 4),
                new Album(9L, "Thelonius Monk", Genre.JAZZ, "Brilliant Corners", 1957, 18)
        ));

        when(mockAlbumRepository.findAll()).thenReturn(albums);

        List<Album> returnedAlbums = albumService.getAllAlbumsInStock();

        assertEquals(albumsInStock, returnedAlbums);
    }

    @Test
    @DisplayName("getAllAlbumsInStock() returns empty list if none in stock")
    void getAllAlbumsInStockTest2() {

        List<Album> albums = new ArrayList<>(List.of(
                new Album(1L, "Jon Hopkins", Genre.ELECTRONIC, "Singularity", 2018, 0),
                new Album(2L, "Four Tet", Genre.ELECTRONIC, "Three", 2024, 0)
        ));

        List<Album> albumsInStock = new ArrayList<>();

        List<Album> returnedAlbums = albumService.getAllAlbumsInStock();

        assertEquals(albumsInStock, returnedAlbums);

    }

    @Test
    @DisplayName("getAllAlbumsInStock() returns empty list if no albums in database")
    void getAllAlbumsInStockTest3() {

        List<Album> albumsInStock = new ArrayList<>();

        List<Album> returnedAlbums = albumService.getAllAlbumsInStock();

        assertEquals(albumsInStock, returnedAlbums);
    }

    @Test
    @DisplayName("getAlbumsByArtist() returns list of albums by artist")
    void getAlbumsByArtistTest() {

        List<Album> expectedAlbums = new ArrayList<>(List.of(
                new Album(7L, "Thelonius Monk", Genre.JAZZ, "Straight, No Chaser", 1967, 7),
                new Album(9L, "Thelonius Monk", Genre.JAZZ, "Brilliant Corners", 1957, 18)
        ));

        when(mockAlbumRepository.findAll()).thenReturn(albums);

        List<Album> actualAlbums = albumService.getAlbumsByArtist("Thelonius Monk");

        assertEquals(expectedAlbums, actualAlbums);

    }

    @Test
    @DisplayName("getAlbumsByArtist() returns empty list if no matches")
    void getAlbumsByArtistTest2() {

        List<Album> expectedAlbums = new ArrayList<>();

        when(mockAlbumRepository.findAll()).thenReturn(albums);

        List<Album> actualAlbums = albumService.getAlbumsByArtist("Charlie Parker");

        assertEquals(expectedAlbums, actualAlbums);

    }

    @Test
    @DisplayName("getAlbumsByYear() returns list of albums by year")
    void getAlbumsByYearTest() {

        List<Album> expectedAlbums = new ArrayList<>(List.of(
                new Album(3L, "Paleface Swiss", Genre.METAL, "Fear & Dagger", 2022, 0),
                new Album(10L, "Half Moon Run", Genre.ALTERNATIVE, "Salt", 2022, 0)
        ));

        when(mockAlbumRepository.findAll()).thenReturn(albums);

        List<Album> actualAlbums = albumService.getAlbumsByYear(2022);

        assertEquals(expectedAlbums, actualAlbums);

    }

    @Test
    @DisplayName("getAlbumsByYear() returns empty if no matches")
    void getAlbumsByYearTest2() {

        List<Album> expectedAlbums = new ArrayList<>();

        when(mockAlbumRepository.findAll()).thenReturn(albums);

        List<Album> actualAlbums = albumService.getAlbumsByYear(1999);

        assertEquals(expectedAlbums, actualAlbums);

    }

    @Test
    @DisplayName("getAlbumsByName() returns list of correct albums")
    void getAlbumByNameTest() {

        List<Album> expectedAlbum = new ArrayList<>(List.of(
                new Album(1L, "Jon Hopkins", Genre.ELECTRONIC, "Singularity", 2018, 5)
        ));

        when(mockAlbumRepository.findAll()).thenReturn(albums);

        List<Album> actualAlbum = albumService.getAlbumByName("Singularity");

        assertEquals(expectedAlbum, actualAlbum);

    }

    @Test
    @DisplayName("getAlbumsByName() returns empty list if no matches")
    void getAlbumsByNameNoMatchesTest() {

        List<Album> expectedAlbums = new ArrayList<>();

        when(mockAlbumRepository.findAll()).thenReturn(albums);

        List<Album> actualAlbums = albumService.getAlbumByName("Insides");

        assertEquals(expectedAlbums, actualAlbums);

    }

    @Test
    @DisplayName("getAlbumsByGenre() returns list of correct albums")
    void getAlbumByGenreTest() {

        List<Album> expectedAlbums = new ArrayList<>(List.of(
                new Album(1L, "Jon Hopkins", Genre.ELECTRONIC, "Singularity", 2018, 5),
                new Album(2L, "Four Tet", Genre.ELECTRONIC, "Three", 2024, 0),
                new Album(5L, "Aphex Twin", Genre.ELECTRONIC, "Selected Ambient Works 85-92", 1992, 8)
        ));

        when(mockAlbumRepository.findAll()).thenReturn(albums);

        List<Album> actualAlbums = albumService.getAlbumByGenre("Electronic");

        assertEquals(expectedAlbums, actualAlbums);

    }

    @Test
    @DisplayName("getAlbumsByGenre() returns empty list if no matches of valid genre")
    void getAlbumByGenreNoMatchesTest() {

        List<Album> expectedAlbums = new ArrayList<>();

        when(mockAlbumRepository.findAll()).thenReturn(albums);

        List<Album> actualAlbums = albumService.getAlbumByGenre("Classical");

        assertEquals(expectedAlbums, actualAlbums);

    }

    @Test
    @DisplayName("getAlbumsByGenre() throws GenreNotAllowed exception with invalid genre")
    void getAlbumByGenreExceptionTest() {

        assertThrows(GenreNotAllowedException.class, () -> { albumService.getAlbumByGenre("Shoegaze"); });

    }

    @Test
    @DisplayName("addNewAlbum() successfully persists album")
    void addNewAlbumTest() {

        Album testAlbum = new Album(5L, "Jon Hopkins", Genre.ELECTRONIC, "Immunity", 2013, 5);

        when(mockAlbumRepository.save(testAlbum)).thenReturn(testAlbum);

        Album actualAlbum = albumService.addNewAlbum(testAlbum);

        assertEquals(testAlbum, actualAlbum);

    }

    @Test
    @DisplayName("updateAlbum() updates and returns album at same ID")
    void updateAlbum() {

        Album testAlbum = new Album(6L, "Confidence Man", Genre.POP, "Tilt", 2022, 15);
        Album updatedAlbum = new Album(6L, "Confidence Man", Genre.POP, "Re-Tilt", 2022, 15);

        when(mockAlbumRepository.findById(6L)).thenReturn(Optional.of(testAlbum));

        Album returnedAlbum = albumService.updateAlbum(6L, updatedAlbum);

        assertEquals(updatedAlbum, returnedAlbum);
        assertEquals(updatedAlbum.getId(), returnedAlbum.getId());

    }

    @Test
    @DisplayName("updateAlbum() throws AlbumNotFoundException with invalid ID")
    void updateAlbumExceptionTest() {

        Album updatedAlbum = new Album(6L, "Confidence Man", Genre.POP, "Re-Tilt", 2022, 15);

        assertThrows(AlbumNotFoundException.class, () -> {
            albumService.updateAlbum(100L, updatedAlbum);
        });

    }

    @Test
    @DisplayName("deleteAlbumById() deletes album")
    public void deleteAlbumTest() {

        Album albumToDelete = new Album(7L, "Half Moon Run", Genre.ALTERNATIVE, "Salt", 2023, 9);

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
