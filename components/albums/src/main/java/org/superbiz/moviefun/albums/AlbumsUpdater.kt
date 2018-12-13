package org.superbiz.moviefun.albums

import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.superbiz.moviefun.CsvUtils
import org.superbiz.moviefun.blobstore.Blob
import org.superbiz.moviefun.blobstore.BlobStore

import java.io.IOException
import java.util.Optional
import java.util.stream.Stream

import com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType
import com.fasterxml.jackson.dataformat.csv.CsvSchema.builder

@Service
class AlbumsUpdater(private val blobStore: BlobStore, private val albumsRepository: AlbumsRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val objectReader: ObjectReader

    init {

        val schema = builder()
                .addColumn("artist")
                .addColumn("title")
                .addColumn("year", ColumnType.NUMBER)
                .addColumn("rating", ColumnType.NUMBER)
                .build()

        objectReader = CsvMapper().readerFor(Album::class.java).with(schema)
    }

    @Throws(IOException::class)
    fun update() {
        val maybeBlob = blobStore.get("albums.csv")

        if (!maybeBlob.isPresent) {
            logger.info("No albums.csv found when running AlbumsUpdater!")
            return
        }

        val albumsToHave = CsvUtils.readFromCsv<Album>(objectReader, maybeBlob.get().inputStream)
        val albumsWeHave = albumsRepository.getAlbums()

        createNewAlbums(albumsToHave, albumsWeHave)
        deleteOldAlbums(albumsToHave, albumsWeHave)
        updateExistingAlbums(albumsToHave, albumsWeHave)
    }


    private fun createNewAlbums(albumsToHave: List<Album>, albumsWeHave: List<Album>) {
        val albumsToCreate = albumsToHave
                .filter { album -> albumsWeHave.none { album.isEquivalent(it) } }

        albumsToCreate.forEach { albumsRepository.addAlbum(it) }
    }

    private fun deleteOldAlbums(albumsToHave: List<Album>, albumsWeHave: List<Album>) {
        val albumsToDelete = albumsWeHave
                .filter { album -> albumsToHave.none { album.isEquivalent(it) }}

        albumsToDelete.forEach { albumsRepository.deleteAlbum(it) }
    }

    private fun updateExistingAlbums(albumsToHave: List<Album>, albumsWeHave: List<Album>) {
        val albumsToUpdate = albumsToHave
                .map { album -> addIdToAlbumIfExists(albumsWeHave, album) }
                .filter { it.hasId() }

        albumsToUpdate.forEach { albumsRepository.updateAlbum(it) }
    }

    private fun addIdToAlbumIfExists(existingAlbums: List<Album>, album: Album): Album {
        val maybeExisting = existingAlbums.filter( { album.isEquivalent(it) } ).first()
        if (maybeExisting != null) album.id = maybeExisting.id
        return album
    }
}
