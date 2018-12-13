package org.superbiz.moviefun.albums

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/albums")
class AlbumsController(private val albumsRepository: AlbumsRepository) {

    @PostMapping
    fun addAlbum(@RequestBody album: Album) {
        albumsRepository.addAlbum(album)
    }

    @GetMapping
    fun index(): List<Album> {
        return albumsRepository.getAlbums()
    }

    @GetMapping("/{albumId}")
    fun details(@PathVariable albumId: Long): Album {
        return albumsRepository.find(albumId)
    }
}
