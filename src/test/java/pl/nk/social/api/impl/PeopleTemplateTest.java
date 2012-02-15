package pl.nk.social.api.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;
import static pl.nk.social.api.impl.AbstractNkTemplate.SOCIAL_REST_URL_BASE;

import java.util.ArrayList;
import java.util.List;

import org.apache.shindig.protocol.RestfulCollection;
import org.apache.shindig.social.opensocial.model.Person.Gender;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import pl.nk.opensocial.model.NkPerson;
import pl.nk.social.api.PeopleOperations;
import pl.nk.social.api.impl.PeopleTemplate;

/**
 */
public class PeopleTemplateTest extends AbstractTemplateTest {

    /**
     * Field oper.
     */
    private PeopleOperations<PeopleTemplate> oper;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        super.setup();
        this.oper = this.nk.peopleOperations();
    }

    /**
     * Method getCurrentUserProfile.
     */
    @Test
    public void getCurrentUserProfile() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        this.mockServer.expect(requestTo(SOCIAL_REST_URL_BASE + "/people/@me")).andExpect(method(GET))
                .andRespond(withResponse(jsonResource("nk-profile"), responseHeaders));

        NkPerson profile = this.oper.getCurrentUserProfile();

        assertEquals("person.abc", profile.getId());
        assertEquals("Mr Sponge", profile.getDisplayName());
        assertEquals("http://nk.pl/profile/1", profile.getProfileUrl());
        assertEquals(Gender.male, profile.getGender());
        assertTrue(profile.getHasApp());
        assertEquals(Integer.valueOf(31), profile.getAge());
        assertEquals(Integer.valueOf(57), profile.getNkFriendsCount());
        assertEquals("http://photos.nasza-klasa.pl/125/10/thumb/6646b702e7.jpeg", profile.getThumbnailUrl());
        assertEquals("Anna Kowalska (Nowak)", profile.getName().getFormatted());
        assertEquals("Kowalska (Nowak)", profile.getName().getAdditionalName());
        assertEquals("Kowalska", profile.getName().getFamilyName());
        assertEquals("Anna", profile.getName().getGivenName());
        assertEquals("http://nk.pl/profile/1", profile.getUrls().get(0).getValue());
        assertEquals("profile", profile.getUrls().get(0).getType());
        assertEquals("my E51", profile.getPhoneNumbers().get(0).getValue());
        assertEquals("phone_number", profile.getPhoneNumbers().get(0).getType());
        assertEquals("http://photos.nasza-klasa.pl/125/10/thumb/6646b702e7.jpeg", profile.getPhotos().get(0).getValue());
        assertEquals("thumbnail", profile.getPhotos().get(0).getType());
        assertEquals("WrocLove", profile.getCurrentLocation().getRegion());
    }

    /**
     * Method getCurrentUserFriends.
     */
    @Test
    public void getCurrentUserFriends() {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        this.mockServer.expect(requestTo(SOCIAL_REST_URL_BASE + "/people/@me/@friends?startIndex=0&count=20"))
                .andExpect(method(GET)).andRespond(withResponse(jsonResource("friends"), responseHeaders));
        RestfulCollection<NkPerson> friends = this.oper.getCurrentUserFriends();

        assertEquals(2, friends.getItemsPerPage());
        assertEquals(0, friends.getStartIndex());
        assertEquals(39, friends.getTotalResults());
        NkPerson friend = friends.getEntry().get(0);
        assertEquals("person.27295d8aa249a645", friend.getId());
    }
    
    /**
     * Method getUserProfile.
     */
    @Test
    public void getUserProfile() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        this.mockServer.expect(requestTo(SOCIAL_REST_URL_BASE + "/people/person.abc")).andExpect(method(GET))
                .andRespond(withResponse(jsonResource("nk-profile"), responseHeaders));

        this.oper.getUserProfile("person.abc");
    }
    
    /**
     * Method get.
     */
    public void get() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        this.mockServer.expect(requestTo(SOCIAL_REST_URL_BASE + "/people/person.1,person.2")).andExpect(method(GET))
                .andRespond(withResponse(jsonResource("nk-profile"), responseHeaders));

        List<String> personIds = new ArrayList<String>(2);
        personIds.add("person.1");
        personIds.add("person.2");
        this.oper.getUsersProfiles(personIds);
    }
}
