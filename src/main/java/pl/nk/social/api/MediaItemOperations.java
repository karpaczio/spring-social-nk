package pl.nk.social.api;

import java.io.InputStream;

import org.apache.shindig.protocol.RestfulCollection;

import pl.nk.opensocial.model.ApplicationMediaItem;
import pl.nk.social.api.impl.CommonOpenSocialOperations;

/**
 */
@SuppressWarnings("rawtypes")
public interface MediaItemOperations<T extends MediaItemOperations> extends CommonOpenSocialOperations<T> {

    /**
     * Method getCurrentUserPhotos.
     * @param albumId String
     * @return RestfulCollection<ApplicationMediaItem>
     */
    RestfulCollection<ApplicationMediaItem> getCurrentUserPhotos(String albumId);
    
    /**
     * Method getUserPhotos.
     * @param personId String
     * @param albumId String
     * @return RestfulCollection<ApplicationMediaItem>
     */
    RestfulCollection<ApplicationMediaItem> getUserPhotos(String personId, String albumId);
    
    /**
     * Method addCurrentUserPhoto.
     * @param albumId String
     * @param in InputStream
     * @param mimeType String
     * @param description String
     * @return ApplicationMediaItem
     */
    ApplicationMediaItem addCurrentUserPhoto(String albumId, InputStream in, String mimeType, String description);
    
}
