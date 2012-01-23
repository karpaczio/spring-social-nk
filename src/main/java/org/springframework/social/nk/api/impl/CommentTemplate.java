package org.springframework.social.nk.api.impl;

import org.springframework.social.nk.api.CommentOperations;
import org.springframework.web.client.RestTemplate;

public class CommentTemplate extends AbstractNkTemplate<CommentTemplate> implements CommentOperations<CommentTemplate> {
    
    public CommentTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }
}
