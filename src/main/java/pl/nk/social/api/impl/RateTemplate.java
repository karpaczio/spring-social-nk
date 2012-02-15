package pl.nk.social.api.impl;

import org.springframework.web.client.RestTemplate;

import pl.nk.social.api.RateOperations;

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
