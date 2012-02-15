package pl.nk.social.api.impl;

import static pl.nk.social.api.impl.TypeReferences.ALBUMS_TYPE_REFERENCE;

import org.apache.commons.lang.NotImplementedException;
import org.apache.shindig.protocol.RestfulCollection;
import org.apache.shindig.social.opensocial.model.Album;
import org.springframework.web.client.RestTemplate;

import pl.nk.social.api.AlbumOperations;

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
     * @see pl.nk.social.api.AlbumOperations#getCurrentUserAlbums()
     */
    @Override
    public RestfulCollection<Album> getCurrentUserAlbums() {
        return getWithFieldsCountStartIndex("/albums/@me/@self", ALBUMS_TYPE_REFERENCE);
    }

    /**
     * Method getGroupAlbums.
     * @param groupId String
     * @return RestfulCollection<Album>
     * @see pl.nk.social.api.AlbumOperations#getGroupAlbums(String)
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
     * @see pl.nk.social.api.AlbumOperations#getUserAlbums(String)
     */
    @Override
    public RestfulCollection<Album> getUserAlbums(String personId) {
        return getWithFieldsCountStartIndex("/albums/" + personId + "/@self", ALBUMS_TYPE_REFERENCE);
    }
}
