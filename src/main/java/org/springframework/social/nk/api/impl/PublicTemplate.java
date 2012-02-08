package org.springframework.social.nk.api.impl;

import org.springframework.social.nk.api.PublicOperations;
import org.springframework.web.client.RestTemplate;

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
