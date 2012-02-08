package org.springframework.social.nk.api.impl;

import org.springframework.social.nk.api.RecommendOperations;
import org.springframework.web.client.RestTemplate;

/**
 */
public class RecommendTemplate extends AbstractNkTemplate<RecommendTemplate> implements RecommendOperations<RecommendTemplate> {
    
    /**
     * Constructor for RecommendTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public RecommendTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }

}
