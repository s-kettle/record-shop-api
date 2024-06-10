package com.skettle.record_shop_api.service;

import com.skettle.record_shop_api.exceptions.AlbumAlreadyExistsException;
import com.skettle.record_shop_api.exceptions.AlbumNotFoundException;
import com.skettle.record_shop_api.exceptions.GenreNotAllowedException;
import com.skettle.record_shop_api.model.Album;
import com.skettle.record_shop_api.model.Genre;
import com.skettle.record_shop_api.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        albumRepository.findAll().forEach(albums::add);
        return albums;
    }

    @Override
    public Album getAlbumById(long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album with ID " + id + " not found."));
    }

    @Override
    public List<Album> getAllAlbumsInStock() {
        List<Album> albums = new ArrayList<>();
        albumRepository.findAll().forEach(albums::add);
        return albums.stream().filter(a -> a.getStockQuantity() > 0).toList();
    }

    @Override
    public List<Album> getAlbumsByArtist(String artist) {
        List<Album> albums = new ArrayList<>();
        albumRepository.findAll().forEach(albums::add);
        return albums.stream().filter(a -> a.getArtist().equalsIgnoreCase(artist)).toList();
    }

    @Override
    public List<Album> getAlbumsByYear(int year) {
        List<Album> albums = new ArrayList<>();
        albumRepository.findAll().forEach(albums::add);
        return albums.stream().filter(a -> a.getReleaseYear() == year).toList();
    }

    @Override
    public List<Album> getAlbumByName(String name) {
        List<Album> albums = new ArrayList<>();
        albumRepository.findAll().forEach(albums::add);
        return albums.stream().filter(a -> a.getName().equalsIgnoreCase(name)).toList();
    }

    @Override
    public List<Album> getAlbumByGenre(String genre) {
        String genreUpper = genre.toUpperCase();
        if (!Genre.isValid(genreUpper)) { throw new GenreNotAllowedException("Genre " + genre + " is not permitted."); };

        List<Album> albums = new ArrayList<>();
        albumRepository.findAll().forEach(albums::add);
        return albums.stream().filter(a -> a.getGenre().equals(Genre.valueOf(genreUpper))).toList();

    }

    @Override
    public Album addNewAlbum(Album album) {

        Album existingAlbum = albumRepository.findById(album.getId()).orElse(null);

        if (existingAlbum != null) {
            throw new AlbumAlreadyExistsException("Album with ID " + album.getId() + " already exists.");
        }

        return albumRepository.save(album);
    }

    @Override
    public Album updateAlbum(long id, Album album) {
        Album albumToUpdate = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album with ID" + id + " not found."));

        albumRepository.deleteById(albumToUpdate.getId());
        albumRepository.save(album);
        return album;
    }

    @Override
    public Album deleteAlbum(long id) {
        Album existingAlbum = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album with ID" + id + " not found."));

        albumRepository.deleteById(existingAlbum.getId());
        return existingAlbum;
    }

}
