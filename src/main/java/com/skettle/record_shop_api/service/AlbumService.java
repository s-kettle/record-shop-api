package com.skettle.record_shop_api.service;

import com.skettle.record_shop_api.model.Album;

import java.util.List;

public interface AlbumService {
    List<Album> getAllAlbums();
    Album getAlbumById(long id);
    List<Album> getAllAlbumsInStock();
    List<Album> getAlbumsByArtist(String artist);
    List<Album> getAlbumsByYear(int year);
    List<Album> getAlbumByName(String name);

    Album addNewAlbum(Album album);
    Album updateAlbum(long id, Album album);
    Album deleteAlbum(long id);

}
