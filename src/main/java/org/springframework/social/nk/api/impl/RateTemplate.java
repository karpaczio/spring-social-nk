package org.springframework.social.nk.api.impl;

import org.springframework.social.nk.api.RateOperations;
import org.springframework.web.client.RestTemplate;

/**
 */
public class RateTemplate extends AbstractNkTemplate<RateTemplate> implements RateOperations<RateTemplate> {
    
    /**
     * Constructor for RateTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public RateTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }

}
