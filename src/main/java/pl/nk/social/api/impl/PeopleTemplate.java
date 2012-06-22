package pl.nk.social.api.impl;

import static pl.nk.social.api.impl.TypeReferences.NK_PROFILES_TYPE_REFERENCE;
import static pl.nk.social.api.impl.TypeReferences.NK_PROFILE_TYPE_REFERENCE;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shindig.protocol.RestfulCollection;
import org.springframework.web.client.RestTemplate;

import pl.nk.opensocial.model.NkPerson;
import pl.nk.opensocial.model.RestfulObject;
import pl.nk.social.api.PeopleOperations;

/**
 */
public class PeopleTemplate extends AbstractNkTemplate<PeopleTemplate> implements PeopleOperations<PeopleTemplate> {

    /**
     * Constructor for PeopleTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public PeopleTemplate(RestTemplate restTemplate, boolean isAuthorized, String socialResourceUrl, String commonResourceUrl) {
        super(restTemplate, isAuthorized, socialResourceUrl, commonResourceUrl);
    }

    /**
     * Method getCurrentUserProfile.
     * @return NkPerson
     * @see pl.nk.social.api.PeopleOperations#getCurrentUserProfile()
     */
    public NkPerson getCurrentUserProfile() {

        RestfulObject<NkPerson> jsonObject = getWithFields("/people/@me", NK_PROFILE_TYPE_REFERENCE);
        return jsonObject != null ? jsonObject.getEntry() : null;
    }

    /**
     * Method getUserProfile.
     * @param personId String
     * @return NkPerson
     * @see pl.nk.social.api.PeopleOperations#getUserProfile(String)
     */
    public NkPerson getUserProfile(String personId) {

        RestfulObject<NkPerson> jsonObject = getWithFields("/people/" + personId, NK_PROFILE_TYPE_REFERENCE);
        return jsonObject != null ? jsonObject.getEntry() : null;
    }

    /**
     * Method getUsersProfiles.
     * @param personIds List<String>
     * @return RestfulCollection<NkPerson>
     * @see org.springframework.social.nk.api.PeopleOperations#getUsersProfiles(List<String>)
     */
    @SuppressWarnings("javadoc")
    public RestfulCollection<NkPerson> getUsersProfiles(List<String> personIds) {

        if (personIds == null || personIds.isEmpty())
            return null;
        String personIdsString = StringUtils.join(personIds, ',');

        return getWithFields("/people/" + personIdsString + "/@self", NK_PROFILES_TYPE_REFERENCE);
    }

    /**
     * Method getCurrentUserFriends.
     * @return RestfulCollection<NkPerson>
     * @see pl.nk.social.api.PeopleOperations#getCurrentUserFriends()
     */
    public RestfulCollection<NkPerson> getCurrentUserFriends() {
        return getWithFieldsCountStartIndex("/people/@me/@friends", NK_PROFILES_TYPE_REFERENCE);
    }

}
