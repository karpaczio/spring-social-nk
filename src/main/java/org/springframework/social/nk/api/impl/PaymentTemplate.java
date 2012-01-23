package org.springframework.social.nk.api.impl;

import org.springframework.social.nk.api.PaymentOperations;
import org.springframework.web.client.RestTemplate;

public class PaymentTemplate extends AbstractNkTemplate<PaymentTemplate> implements PaymentOperations<PaymentTemplate> {
    
    public PaymentTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }

}
