package pl.nk.social.api.impl;

import org.springframework.web.client.RestTemplate;

import pl.nk.social.api.PublicOperations;

/**
 */
public class PublicTemplate extends AbstractNkTemplate<PublicTemplate> implements PublicOperations {

    /**
     * Constructor for PublicTemplate.
     * @param restTemplate RestTemplate
     */
    public PublicTemplate(RestTemplate restTemplate) {
        super(restTemplate, true);
    }

}
