package org.springframework.social.nk.api.impl;

import org.springframework.social.nk.api.RecommendOperations;
import org.springframework.web.client.RestTemplate;

public class RecommendTemplate extends AbstractNkTemplate<RecommendTemplate> implements RecommendOperations<RecommendTemplate> {
    
    public RecommendTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }

}
