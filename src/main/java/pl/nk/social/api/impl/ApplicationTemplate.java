package pl.nk.social.api.impl;

import org.springframework.web.client.RestTemplate;

import pl.nk.social.api.ApplicationOperations;

/**
 */
public class ApplicationTemplate extends AbstractNkTemplate<ApplicationTemplate> implements ApplicationOperations<ApplicationTemplate> {

    /**
     * Constructor for ApplicationTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public ApplicationTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }
}
