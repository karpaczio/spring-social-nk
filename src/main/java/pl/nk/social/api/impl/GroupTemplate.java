package pl.nk.social.api.impl;

import org.springframework.web.client.RestTemplate;

import pl.nk.social.api.GroupOperations;

/**
 */
public class GroupTemplate extends AbstractNkTemplate<GroupTemplate> implements GroupOperations<GroupTemplate> {
    
    /**
     * Constructor for GroupTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public GroupTemplate(RestTemplate restTemplate, boolean isAuthorized, String socialResourceUrl, String commonResourceUrl) {
        super(restTemplate, isAuthorized, socialResourceUrl, commonResourceUrl);
    }

}
