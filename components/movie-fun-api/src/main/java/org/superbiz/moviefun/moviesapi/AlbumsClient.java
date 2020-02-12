package org.superbiz.moviefun.moviesapi;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestOperations;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class AlbumsClient {

    private final String albumsUrl;
    private final RestOperations restOperations;

    private static ParameterizedTypeReference<List<AlbumInfo>> albumListType = new ParameterizedTypeReference<List<AlbumInfo>>() {
    };

    public AlbumsClient(String albumsUrl, RestOperations restOperations) {
        this.albumsUrl = albumsUrl;
        this.restOperations = restOperations;
    }

    public void addAlbum(AlbumInfo album) {
        restOperations.postForEntity(albumsUrl, album, AlbumInfo.class);
    }

    public AlbumInfo find(long id) {
        return restOperations.getForObject(albumsUrl + "/" + id, AlbumInfo.class);
    }

    public List<AlbumInfo> getAlbums() {
        return restOperations.exchange(albumsUrl, GET, null, albumListType).getBody();
    }
}
