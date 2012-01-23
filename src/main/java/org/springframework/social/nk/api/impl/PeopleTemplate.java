package org.springframework.social.nk.api.impl;

import static org.springframework.social.nk.api.impl.TypeReferences.NK_PROFILES_TYPE_REFERENCE;
import static org.springframework.social.nk.api.impl.TypeReferences.NK_PROFILE_TYPE_REFERENCE;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shindig.protocol.RestfulCollection;
import org.springframework.social.nk.api.PeopleOperations;
import org.springframework.web.client.RestTemplate;

import pl.nk.opensocial.model.NkPerson;
import pl.nk.opensocial.model.RestfulObject;

public class PeopleTemplate extends AbstractNkTemplate<PeopleTemplate> implements PeopleOperations<PeopleTemplate> {

    public PeopleTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }

    public NkPerson getCurrentUserProfile() {

        RestfulObject<NkPerson> jsonObject = getWithFields("/people/@me", NK_PROFILE_TYPE_REFERENCE);
        return jsonObject != null ? jsonObject.getEntry() : null;
    }

    public NkPerson getUserProfile(String personId) {

        RestfulObject<NkPerson> jsonObject = getWithFields("/people/" + personId, NK_PROFILE_TYPE_REFERENCE);
        return jsonObject != null ? jsonObject.getEntry() : null;
    }

    public RestfulCollection<NkPerson> getUsersProfiles(List<String> personIds) {

        if (personIds == null || personIds.isEmpty())
            return null;
        String personIdsString = StringUtils.join(personIds, ',');

        return getWithFields("/people/" + personIdsString + "/@self", NK_PROFILES_TYPE_REFERENCE);
    }

    public RestfulCollection<NkPerson> getCurrentUserFriends() {
        return getWithFieldsCountStartIndex("/people/@me/@friends", NK_PROFILES_TYPE_REFERENCE);
    }

}
