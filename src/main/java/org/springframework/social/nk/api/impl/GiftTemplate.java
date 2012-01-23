package org.springframework.social.nk.api.impl;

import org.springframework.social.nk.api.GiftOperations;
import org.springframework.web.client.RestTemplate;

public class GiftTemplate extends AbstractNkTemplate<GiftTemplate> implements GiftOperations<GiftTemplate> {

    public GiftTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }
}
