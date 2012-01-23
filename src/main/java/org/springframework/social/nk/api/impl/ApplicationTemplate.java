package org.springframework.social.nk.api.impl;

import org.springframework.social.nk.api.ApplicationOperations;
import org.springframework.web.client.RestTemplate;

public class ApplicationTemplate extends AbstractNkTemplate<ApplicationTemplate> implements ApplicationOperations<ApplicationTemplate> {

    public ApplicationTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }
}
