package org.springframework.social.nk.api.impl;

import static org.springframework.social.nk.api.impl.TypeReferences.MEDIA_ITEMS_TYPE_REFERENCE;
import static org.springframework.social.nk.api.impl.TypeReferences.MEDIA_ITEM_TYPE_REFERENCE;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.shindig.protocol.RestfulCollection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.nk.api.MediaItemOperations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import pl.nk.opensocial.model.ApplicationMediaItem;
import pl.nk.opensocial.model.ApplicationMediaItemImpl;

/**
 */
public class MediaItemTemplate extends AbstractNkTemplate<MediaItemTemplate> implements
        MediaItemOperations<MediaItemTemplate> {

    /**
     * Constructor for MediaItemTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public MediaItemTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }

    /**
     * Method getCurrentUserPhotos.
     * @param albumId String
     * @return RestfulCollection<ApplicationMediaItem>
     * @see org.springframework.social.nk.api.MediaItemOperations#getCurrentUserPhotos(String)
     */
    public RestfulCollection<ApplicationMediaItem> getCurrentUserPhotos(String albumId) {
        return getWithFieldsCountStartIndex("/mediaItems/@me/@self/" + albumId, MEDIA_ITEMS_TYPE_REFERENCE);
    }

    /**
     * Method getUserPhotos.
     * @param personId String
     * @param albumId String
     * @return RestfulCollection<ApplicationMediaItem>
     * @see org.springframework.social.nk.api.MediaItemOperations#getUserPhotos(String, String)
     */
    public RestfulCollection<ApplicationMediaItem> getUserPhotos(String personId, String albumId) {
        return getWithFieldsCountStartIndex("/mediaItems/" + personId + "/@self/" + albumId, MEDIA_ITEMS_TYPE_REFERENCE);
    }

    /**
     * Method addCurrentUserPhoto.
     * @param albumId String
     * @param in InputStream
     * @param mimeType String
     * @param description String
     * @return ApplicationMediaItem
     * @see org.springframework.social.nk.api.MediaItemOperations#addCurrentUserPhoto(String, InputStream, String, String)
     */
    public ApplicationMediaItem addCurrentUserPhoto(String albumId, InputStream in, String mimeType, String description) {

        try {

            MultiValueMap<String, Object> multipart = new LinkedMultiValueMap<String, Object>();
            multipart.add("request", getRequest(description));
            multipart.add("photo", getPhoto(in, mimeType));

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(multipart,
                    MEDIA_ITEM_JSON_CONTENT_TYPE);

            return post("/mediaItems/@me/@self/" + albumId, entity, MEDIA_ITEM_TYPE_REFERENCE);
        } catch (IOException ioe) {
            return null;
        }
    }

    /**
     * Method getRequest.
     * @param description String
     * @return HttpEntity<ApplicationMediaItem>
     */
    private HttpEntity<ApplicationMediaItem> getRequest(String description) {
        ApplicationMediaItem request = new ApplicationMediaItemImpl();
        request.setDescription(description);
        return new HttpEntity<ApplicationMediaItem>(request, APPLICATION_JSON_CONTENT_TYPE);
    }

    /**
     * Method getPhoto.
     * @param in InputStream
     * @param mimeType String
     * @return HttpEntity<?>
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private HttpEntity<?> getPhoto(InputStream in, String mimeType) throws IOException {
        byte[] photoBytes = IOUtils.toByteArray(in);

        HttpHeaders photoHeaders = new HttpHeaders();
        photoHeaders.setContentType(MediaType.valueOf(mimeType));

        return new HttpEntity(photoBytes, photoHeaders);
    }
}
