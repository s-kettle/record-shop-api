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
    public ResponseEntity<List<Album>> getAllAlbums() {
        return new ResponseEntity<>(albumService.getAllAlbums(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable long id) {
        return new ResponseEntity<>(albumService.getAlbumById(id), HttpStatus.OK);
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
