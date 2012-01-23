package org.springframework.social.nk.api;

import org.apache.shindig.protocol.RestfulCollection;
import org.apache.shindig.social.opensocial.model.Album;
import org.springframework.social.nk.api.impl.CommonOpenSocialOperations;

@SuppressWarnings("rawtypes")
public interface AlbumOperations <T extends AlbumOperations> extends CommonOpenSocialOperations<T> {

    RestfulCollection<Album> getCurrentUserAlbums();
    RestfulCollection<Album> getUserAlbums(String personId);
    RestfulCollection<Album> getGroupAlbums(String groupId); 
}
