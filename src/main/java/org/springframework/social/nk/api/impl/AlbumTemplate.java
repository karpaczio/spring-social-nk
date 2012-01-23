package org.springframework.social.nk.api.impl;

import static org.springframework.social.nk.api.impl.TypeReferences.ALBUMS_TYPE_REFERENCE;

import org.apache.commons.lang.NotImplementedException;
import org.apache.shindig.protocol.RestfulCollection;
import org.apache.shindig.social.opensocial.model.Album;
import org.springframework.social.nk.api.AlbumOperations;
import org.springframework.web.client.RestTemplate;

public class AlbumTemplate extends AbstractNkTemplate<AlbumTemplate> implements AlbumOperations<AlbumTemplate> {
    
    public AlbumTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }

    @Override
    public RestfulCollection<Album> getCurrentUserAlbums() {
        return getWithFieldsCountStartIndex("/albums/@me/@self", ALBUMS_TYPE_REFERENCE);
    }

    @Override
    public RestfulCollection<Album> getGroupAlbums(String groupId) {
        throw new NotImplementedException();
        // TODO This is only draft. Uncomment when nk will implement this feature. 
        // return getWithFieldsCountStartIndex("/nkalbums/nkgroup/@me/@self/" + groupId, ALBUMS_TYPE_REFERENCE);
    }

    @Override
    public RestfulCollection<Album> getUserAlbums(String personId) {
        return getWithFieldsCountStartIndex("/albums/" + personId + "/@self", ALBUMS_TYPE_REFERENCE);
    }
}
