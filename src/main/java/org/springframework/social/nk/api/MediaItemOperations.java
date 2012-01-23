package org.springframework.social.nk.api;

import java.io.InputStream;

import org.apache.shindig.protocol.RestfulCollection;
import org.springframework.social.nk.api.impl.CommonOpenSocialOperations;

import pl.nk.opensocial.model.ApplicationMediaItem;

@SuppressWarnings("rawtypes")
public interface MediaItemOperations<T extends MediaItemOperations> extends CommonOpenSocialOperations<T> {

    RestfulCollection<ApplicationMediaItem> getCurrentUserPhotos(String albumId);
    
    RestfulCollection<ApplicationMediaItem> getUserPhotos(String personId, String albumId);
    
    ApplicationMediaItem addCurrentUserPhoto(String albumId, InputStream in, String mimeType, String description);
    
}
