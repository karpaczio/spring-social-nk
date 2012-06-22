package pl.nk.social.api.impl;

import org.springframework.web.client.RestTemplate;

import pl.nk.social.api.MessageOperations;

/**
 */
public class MessageTemplate extends AbstractNkTemplate<MessageTemplate> implements MessageOperations<MessageTemplate> {
    
    /**
     * Constructor for MessageTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public MessageTemplate(RestTemplate restTemplate, boolean isAuthorized, String socialResourceUrl, String commonResourceUrl) {
        super(restTemplate, isAuthorized, socialResourceUrl, commonResourceUrl);
    }

}
