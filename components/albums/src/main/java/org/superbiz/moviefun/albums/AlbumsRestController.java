package org.superbiz.moviefun.albums;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumsRestController {

    private AlbumsRepository albumsRepository;

    public AlbumsRestController(AlbumsRepository albumsRepository) {
        this.albumsRepository = albumsRepository;
    }

    @GetMapping("/{albumId}")
    public Album findAlbum(@PathVariable Long albumId) {
        return albumsRepository.find(albumId);
    }

    @GetMapping
    public List<Album> getAlbums() {
        return albumsRepository.getAlbums();
    }

    @PostMapping
    public void addAlbum(@RequestBody Album album) {
        albumsRepository.addAlbum(album);
    }

    @DeleteMapping("/{albumId}")
    public void deleteAlbumId(@PathVariable Long albumId) {
        albumsRepository.deleteAlbumById(albumId);
    }
}