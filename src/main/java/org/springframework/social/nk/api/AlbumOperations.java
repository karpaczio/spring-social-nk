package org.springframework.social.nk.api;

import org.apache.shindig.protocol.RestfulCollection;
import org.apache.shindig.social.opensocial.model.Album;
import org.springframework.social.nk.api.impl.CommonOpenSocialOperations;

/**
 */
@SuppressWarnings("rawtypes")
public interface AlbumOperations <T extends AlbumOperations> extends CommonOpenSocialOperations<T> {

    /**
     * Method getCurrentUserAlbums.
     * @return RestfulCollection<Album>
     */
    RestfulCollection<Album> getCurrentUserAlbums();
    /**
     * Method getUserAlbums.
     * @param personId String
     * @return RestfulCollection<Album>
     */
    RestfulCollection<Album> getUserAlbums(String personId);
    /**
     * Method getGroupAlbums.
     * @param groupId String
     * @return RestfulCollection<Album>
     */
    RestfulCollection<Album> getGroupAlbums(String groupId); 
}
