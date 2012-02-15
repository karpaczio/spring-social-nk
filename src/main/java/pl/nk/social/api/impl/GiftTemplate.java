package pl.nk.social.api.impl;

import org.springframework.web.client.RestTemplate;

import pl.nk.social.api.GiftOperations;

/**
 */
public class GiftTemplate extends AbstractNkTemplate<GiftTemplate> implements GiftOperations<GiftTemplate> {

    /**
     * Constructor for GiftTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public GiftTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }
}
