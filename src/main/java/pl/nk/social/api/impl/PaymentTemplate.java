package pl.nk.social.api.impl;

import org.springframework.web.client.RestTemplate;

import pl.nk.social.api.PaymentOperations;

/**
 */
public class PaymentTemplate extends AbstractNkTemplate<PaymentTemplate> implements PaymentOperations<PaymentTemplate> {
    
    /**
     * Constructor for PaymentTemplate.
     * @param restTemplate RestTemplate
     * @param isAuthorized boolean
     */
    public PaymentTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }

}
