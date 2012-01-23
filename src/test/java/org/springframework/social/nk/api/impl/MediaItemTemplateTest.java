package org.springframework.social.nk.api.impl;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;
import static org.springframework.social.nk.api.impl.AbstractNkTemplate.API_URL_BASE;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.shindig.protocol.RestfulCollection;
import org.apache.shindig.social.opensocial.model.MediaItem;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.nk.api.MediaItemOperations;

import pl.nk.opensocial.model.ApplicationMediaItem;

public class MediaItemTemplateTest extends AbstractTemplateTest {

    private MediaItemOperations<MediaItemTemplate> oper;

    @Before
    public void setup() {
        super.setup();
        this.oper = this.nk.mediaItemOperations();
    }

    @Test
    public void getCurrentUserPhotos() {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        this.mockServer
                .expect(requestTo(API_URL_BASE + "/mediaItems/@me/@self/album.1?startIndex=0&count=20"))
                .andExpect(method(GET)).andRespond(withResponse(jsonResource("mediaitems"), responseHeaders));

        RestfulCollection<ApplicationMediaItem> mediaItems = this.oper.getCurrentUserPhotos("album.1");

        assertEquals(2, mediaItems.getTotalResults());
        ApplicationMediaItem mediaItem = mediaItems.getEntry().get(0);
        assertEquals("mediaitem.82", mediaItem.getId());
        assertEquals("album.1", mediaItem.getAlbumId());
        assertEquals("0", mediaItem.getNumVotes());
        assertEquals("0", mediaItem.getRating());
        assertEquals("2010-03-23T10:41:51.000Z", mediaItem.getCreated());
        assertEquals("http://photos.nasza-klasa.pl/3/3/thumb/8e5e0bfd4a.jpeg", mediaItem.getUrl());
        assertEquals("http://photos.nk-net.pl/22/75/thumb/8cce9c2424.jpeg", mediaItem.getThumbnailUrl());
        assertEquals(MediaItem.Type.IMAGE, mediaItem.getType());
        assertEquals("image/jpeg", mediaItem.getMimeType());
        assertEquals("person.1332123", mediaItem.getAddedBy());
        assertEquals("jhgujk", mediaItem.getDescription());
        assertEquals("0", mediaItem.getNumSuperVotes());
        assertEquals(Long.valueOf(1306926434000L), mediaItem.getNkCreatedTime());
    }
    
    @Test
    public void addCurrentUserPhoto() throws Exception {
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        this.mockServer
                .expect(requestTo(API_URL_BASE + "/mediaItems/@me/@self/album.1"))
                .andExpect(method(POST)).andRespond(withResponse(jsonResource("mediaitems"), responseHeaders));
    
        File f = new File("c:\\tmp\\avatar.jpeg");
        InputStream in = new FileInputStream(f);
        this.oper.addCurrentUserPhoto("album.1", in, "image/jpg", "some description");
        
    }
}
