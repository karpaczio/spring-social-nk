package org.springframework.social.nk.api;

import java.util.List;

import org.apache.shindig.protocol.RestfulCollection;
import org.springframework.social.nk.api.impl.CommonOpenSocialOperations;

import pl.nk.opensocial.model.NkPerson;

/**
 */
public interface PeopleOperations<H> extends CommonOpenSocialOperations<H> {

    /**
     * Fetches profile of the current user. 
     * 
    
     * @return Profile of the current user. */
    NkPerson getCurrentUserProfile();

    /**
     * Fetches profile of the given user. 
     * 
     * @param personId
     *            Identifier of a person
    
     * @return Profile of the given user. */
    NkPerson getUserProfile(String personId);

    /**
     * Fetches profiles of users with given ids.
     * 
     * @param personIds
     *            List of user's identifiers
    
     * @return Profiles of users with given ids. */
    RestfulCollection<NkPerson> getUsersProfiles(List<String> personIds);

    /**
     * Fetches profiles of the current user's friends. 
     * 
    
     * @return Profiles of the current user's friends. */
    RestfulCollection<NkPerson> getCurrentUserFriends();

}
