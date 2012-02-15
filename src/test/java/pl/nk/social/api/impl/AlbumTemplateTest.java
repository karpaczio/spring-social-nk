package pl.nk.social.api.impl;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;
import static pl.nk.social.api.impl.AbstractNkTemplate.SOCIAL_REST_URL_BASE;

import org.apache.shindig.protocol.RestfulCollection;
import org.apache.shindig.social.opensocial.model.Album;
import org.apache.shindig.social.opensocial.model.MediaItem;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import pl.nk.social.api.AlbumOperations;
import pl.nk.social.api.impl.AlbumTemplate;

/**
 */
public class AlbumTemplateTest extends AbstractTemplateTest {

    /**
     * Field oper.
     */
    private AlbumOperations<AlbumTemplate> oper;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        super.setup();
        this.oper = this.nk.albumOperations();
    }

    /**
     * Method getCurrentUserAlbums.
     */
    @Test
    public void getCurrentUserAlbums() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        this.mockServer
                .expect(requestTo(SOCIAL_REST_URL_BASE + "/albums/@me/@self?startIndex=0&count=20"))
                .andExpect(method(GET)).andRespond(withResponse(jsonResource("albums"), responseHeaders));

        RestfulCollection<Album> albums = this.oper.getCurrentUserAlbums();

        assertEquals(2, albums.getTotalResults());
        Album album = albums.getEntry().get(0);
        assertEquals("album.3", album.getId());
        assertEquals("some title", album.getTitle());
        assertEquals("some description", album.getDescription());
        assertEquals("person.abc", album.getOwnerId());
        assertEquals("image/jpeg", album.getMediaMimeType().get(0));
        assertEquals(MediaItem.Type.IMAGE, album.getMediaType().get(0));
        assertEquals(Integer.valueOf(1), album.getMediaItemCount());

    }
    
    /**
     * Method getUserAlbum.
     */
    @Test
    public void getUserAlbum() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        this.mockServer
                .expect(requestTo(SOCIAL_REST_URL_BASE + "/albums/person.123/@self?startIndex=0&count=20"))
                .andExpect(method(GET)).andRespond(withResponse(jsonResource("albums"), responseHeaders));

        this.oper.getUserAlbums("person.123");
    }
    
    // @Test
    /**
     * Method getGroupAlbums.
     */
    public void getGroupAlbums() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        this.mockServer
                .expect(requestTo(SOCIAL_REST_URL_BASE + "/nkalbums/nkgroup/@me/@self/group.123?startIndex=0&count=20"))
                .andExpect(method(GET)).andRespond(withResponse(jsonResource("albums"), responseHeaders));

        this.oper.getGroupAlbums("group.123");
    }
}
