package org.springframework.social.nk.api.impl;

import static org.springframework.social.nk.api.impl.TypeReferences.ALBUMS_TYPE_REFERENCE;

import org.apache.commons.lang.NotImplementedException;
import org.apache.shindig.protocol.RestfulCollection;
import org.apache.shindig.social.opensocial.model.Album;
import org.springframework.social.nk.api.AlbumOperations;
import org.springframework.web.client.RestTemplate;

/**
 */
public class AlbumTemplate extends AbstractNkTemplate<AlbumTemplate> implements AlbumOperations<AlbumTemplate> {
    
    /**
     * Constructor for AlbumTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public AlbumTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }

    /**
     * Method getCurrentUserAlbums.
     * @return RestfulCollection<Album>
     * @see org.springframework.social.nk.api.AlbumOperations#getCurrentUserAlbums()
     */
    @Override
    public RestfulCollection<Album> getCurrentUserAlbums() {
        return getWithFieldsCountStartIndex("/albums/@me/@self", ALBUMS_TYPE_REFERENCE);
    }

    /**
     * Method getGroupAlbums.
     * @param groupId String
     * @return RestfulCollection<Album>
     * @see org.springframework.social.nk.api.AlbumOperations#getGroupAlbums(String)
     */
    @Override
    public RestfulCollection<Album> getGroupAlbums(String groupId) {
        throw new NotImplementedException();
        // TODO This is only draft. Uncomment when nk will implement this feature. 
        // return getWithFieldsCountStartIndex("/nkalbums/nkgroup/@me/@self/" + groupId, ALBUMS_TYPE_REFERENCE);
    }

    /**
     * Method getUserAlbums.
     * @param personId String
     * @return RestfulCollection<Album>
     * @see org.springframework.social.nk.api.AlbumOperations#getUserAlbums(String)
     */
    @Override
    public RestfulCollection<Album> getUserAlbums(String personId) {
        return getWithFieldsCountStartIndex("/albums/" + personId + "/@self", ALBUMS_TYPE_REFERENCE);
    }
}
