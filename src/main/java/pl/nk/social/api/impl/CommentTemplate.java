package pl.nk.social.api.impl;

import org.springframework.web.client.RestTemplate;

import pl.nk.social.api.CommentOperations;

/**
 */
public class CommentTemplate extends AbstractNkTemplate<CommentTemplate> implements CommentOperations<CommentTemplate> {
    
    /**
     * Constructor for CommentTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public CommentTemplate(RestTemplate restTemplate, boolean isAuthorized, String socialResourceUrl, String commonResourceUrl) {
        super(restTemplate, isAuthorized, socialResourceUrl, commonResourceUrl);
    }
}
