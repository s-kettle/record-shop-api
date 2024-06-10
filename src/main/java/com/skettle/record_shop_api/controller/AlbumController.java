package com.skettle.record_shop_api.controller;

import com.skettle.record_shop_api.model.Album;
import com.skettle.record_shop_api.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @GetMapping
    public ResponseEntity<?> getAlbumsByFilterElseAll(
            @RequestParam(required = false) String artist,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String name) {

        // Check for filters first
        if (artist != null) {
            List<Album> albums = albumService.getAlbumsByArtist(artist);
            return albums.isEmpty() ?
                    new ResponseEntity<>("No albums with artist " + artist, HttpStatus.NOT_FOUND) :
                    new ResponseEntity<>(albumService.getAlbumsByArtist(artist), HttpStatus.OK);
        }

        if (year != null) {
            List<Album> albums = albumService.getAlbumsByYear(Integer.parseInt(year));
            return albums.isEmpty() ?
                    new ResponseEntity<>("No albums of year " + year, HttpStatus.NOT_FOUND) :
                    new ResponseEntity<>(albumService.getAlbumsByYear(Integer.parseInt(year)), HttpStatus.OK);
        }

        if (genre != null) {
            List<Album> albums = albumService.getAlbumByGenre(genre);
            return albums.isEmpty() ?
                    new ResponseEntity<>("No albums of genre: " + genre, HttpStatus.NOT_FOUND) :
                    new ResponseEntity<>(albumService.getAlbumByGenre(genre), HttpStatus.OK);
        }

        if (name != null) {
            List<Album> albums = albumService.getAlbumByName(name);
            return albums.isEmpty() ?
                    new ResponseEntity<>("No albums with name: " + name, HttpStatus.NOT_FOUND) :
                    new ResponseEntity<>(albumService.getAlbumByName(name), HttpStatus.OK);
        }

        // If no request parameters are specified, return all albums by default
        return new ResponseEntity<>(albumService.getAllAlbums(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable long id) {
        return new ResponseEntity<>(albumService.getAlbumById(id), HttpStatus.OK);
    }

    @GetMapping("/instock")
    public ResponseEntity<?> getAllAlbumsInStock() {
        List<Album> albums = albumService.getAllAlbumsInStock();

        if (albums.isEmpty()) {
            return new ResponseEntity<>("No albums in stock", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Album> addNewAlbum(@RequestBody Album album) {
        Album newAlbum = albumService.addNewAlbum(album);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("album", "/api/v1/albums/" + newAlbum.getId());
        return new ResponseEntity<>(newAlbum, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@RequestBody Album album, @PathVariable long id) {
        Album updatedAlbum = albumService.updateAlbum(id, album);
        return new ResponseEntity<>(updatedAlbum, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Album> deleteAlbum(@PathVariable long id) {
        return new ResponseEntity<>(albumService.deleteAlbum(id), HttpStatus.OK);
    }

}
