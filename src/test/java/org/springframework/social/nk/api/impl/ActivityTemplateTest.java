package org.springframework.social.nk.api.impl;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;
import static org.springframework.social.nk.api.impl.AbstractNkTemplate.SOCIAL_REST_URL_BASE;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.nk.api.ActivityOperations;

/**
 */
public class ActivityTemplateTest extends AbstractTemplateTest {

    /**
     * Field oper.
     */
    private ActivityOperations<ActivityTemplate> oper;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        super.setup();
        this.oper = this.nk.activityOperations();
    }

    /**
     * Method addActivityForAll.
     */
    @Test
    public void addActivityForAll() {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        this.mockServer.expect(requestTo(SOCIAL_REST_URL_BASE + "/activities/@me/@all/app.sledzik")).andExpect(method(POST))
                .andRespond(withResponse("{\"entry\":{}}", responseHeaders));

        this.oper.addActivityForAll("some title");
    }

    /**
     * Method addActivityForFriends.
     */
    @Test
    public void addActivityForFriends() {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        this.mockServer
                .expect(requestTo(SOCIAL_REST_URL_BASE + "/activities/@me/@friends/app.sledzik"))
                .andExpect(method(POST)).andRespond(withResponse("{\"entry\":{}}", responseHeaders));

        this.oper.addActivityForFriends("some title");
    }

}
