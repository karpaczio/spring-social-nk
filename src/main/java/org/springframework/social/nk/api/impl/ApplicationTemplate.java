package org.springframework.social.nk.api.impl;

import org.springframework.social.nk.api.ApplicationOperations;
import org.springframework.web.client.RestTemplate;

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
