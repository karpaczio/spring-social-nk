package org.springframework.social.nk.connect;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.nk.api.Nk;

import pl.nk.opensocial.model.NkPerson;

public class NkApiAdapter implements ApiAdapter<Nk> {

    private static final XLogger LOGGER = XLoggerFactory.getXLogger(NkApiAdapter.class);

    public boolean test(Nk nk) {
        try {
            nk.peopleOperations().getCurrentUserProfile();
            return true;
        } catch (ApiException e) {
            LOGGER.info("Connection test failed. ", e);
            return false;
        }
    }

    public UserProfile fetchUserProfile(Nk nk) {
        NkPerson person = nk.peopleOperations().getCurrentUserProfile();
        return new UserProfileBuilder().setName(person.getDisplayName()).build();
        // TODO set email
    }

    public void setConnectionValues(Nk api, ConnectionValues values) {
        NkPerson person = api.peopleOperations().getCurrentUserProfile();
        values.setDisplayName(person.getDisplayName());
        values.setProviderUserId(person.getId());
        // TODO

    }

    public void updateStatus(Nk arg0, String arg1) {
        // TODO unsupported

    }

}
