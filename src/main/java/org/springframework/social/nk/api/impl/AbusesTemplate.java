package org.springframework.social.nk.api.impl;

import org.springframework.social.nk.api.AbusesOperations;
import org.springframework.web.client.RestTemplate;

public class AbusesTemplate extends AbstractNkTemplate<AbusesTemplate> implements AbusesOperations<AbusesTemplate> {

    public AbusesTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }
}
