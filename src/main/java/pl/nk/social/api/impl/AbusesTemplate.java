package pl.nk.social.api.impl;

import org.springframework.web.client.RestTemplate;

import pl.nk.social.api.AbusesOperations;

/**
 */
public class AbusesTemplate extends AbstractNkTemplate<AbusesTemplate> implements AbusesOperations<AbusesTemplate> {

    /**
     * Constructor for AbusesTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public AbusesTemplate(RestTemplate restTemplate, boolean isAuthorized, String socialResourceUrl, String commonResourceUrl) {
        super(restTemplate, isAuthorized, socialResourceUrl, commonResourceUrl);
    }
}
