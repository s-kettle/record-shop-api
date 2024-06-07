package com.skettle.record_shop_api.service;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class AlbumServiceImplTest {

    @Mock
    AlbumRepository mockAlbumRepository;

    @InjectMocks
    AlbumServiceImpl albumService;

    @Test
    @DisplayName("GET /albums returns all albums")
    void getAllAlbumsTest() {

        List<Album> albums = new ArrayList<>(List.of(
           new Album(1L, new Artist(1L, "Nothing But Thieves", null), new Genre(1L, null, "Rock"), "Moral Panic", 2020, 12),
           new Album(2L, new Artist(2L, "Aphex Twin", null), new Genre(2L, null, "Electronic"), "Selected Ambient Works 85-92", 1992, 8),
           new Album(3L, new Artist(3L, "Miles Davis", null), new Genre(3L, null, "Jazz"), "Some Kind of Blue", 1959, 2)
        ));

        when(mockAlbumRepository.findAll()).thenReturn(albums);

        List<Album> actualAlbums = albumService.getAllAlbums();

        assertEquals(albums, actualAlbums);

    }
}