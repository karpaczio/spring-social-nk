package org.springframework.social.nk.api.impl;

import org.springframework.social.nk.api.MessageOperations;
import org.springframework.web.client.RestTemplate;

/**
 */
public class MessageTemplate extends AbstractNkTemplate<MessageTemplate> implements MessageOperations<MessageTemplate> {
    
    /**
     * Constructor for MessageTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public MessageTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }

}
