package pl.nk.social.api.impl;

import org.springframework.web.client.RestTemplate;

import pl.nk.social.api.RecommendOperations;

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
