package org.superbiz.moviefun.albumsapi;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestOperations;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class AlbumClient {

    private final RestOperations restOperations;
    private final String albumsUrl;

    private static ParameterizedTypeReference<List<AlbumInfo>> albumsListType = new ParameterizedTypeReference<List<AlbumInfo>>()
    {};

    public AlbumClient(RestOperations restOperations, String albumsUrl) {
        this.restOperations = restOperations;
        this.albumsUrl = albumsUrl;
    }

    public void addAlbum(AlbumInfo album) {
        restOperations.postForEntity(albumsUrl, album, AlbumInfo.class);
    }

    public AlbumInfo find(long id) {
        return restOperations.getForObject(albumsUrl + "/" + id, AlbumInfo.class);
    }

    public List<AlbumInfo> getAlbums() {
        return restOperations.exchange(albumsUrl, GET, null, albumsListType).getBody();
    }

    public void deleteAlbum(AlbumInfo album) {
        restOperations.delete(albumsUrl + "/" + album.getId());
    }

    public void updateAlbum(AlbumInfo album) {
        restOperations.postForEntity(albumsUrl, album, AlbumInfo.class);
    }
}
