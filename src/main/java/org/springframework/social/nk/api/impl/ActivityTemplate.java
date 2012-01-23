package org.springframework.social.nk.api.impl;

import static org.springframework.social.nk.api.impl.TypeReferences.NK_ACTIVITY_TYPE_REFERENCE;

import org.springframework.http.HttpEntity;
import org.springframework.social.nk.api.ActivityOperations;
import org.springframework.web.client.RestTemplate;

import pl.nk.opensocial.model.NkActivity;
import pl.nk.opensocial.model.NkActivityImpl;
import pl.nk.opensocial.model.RestfulObject;

public class ActivityTemplate extends AbstractNkTemplate<ActivityTemplate> implements
        ActivityOperations<ActivityTemplate> {

    public ActivityTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }

    @Override
    public RestfulObject<NkActivity> addActivityForAll(String title) {

        return addActivity(title, "@all");
    }

    @Override
    public RestfulObject<NkActivity> addActivityForFriends(String title) {

        return addActivity(title, "@friends");
    }

    private RestfulObject<NkActivity> addActivity(String title, String group) {
        NkActivity activity = new NkActivityImpl();
        activity.setTitle(title);
        HttpEntity<NkActivity> entity = new HttpEntity<NkActivity>(activity, APPLICATION_JSON_CONTENT_TYPE);
        return post("/activities/@me/" + group + "/app.sledzik", entity, NK_ACTIVITY_TYPE_REFERENCE);
    }
}
