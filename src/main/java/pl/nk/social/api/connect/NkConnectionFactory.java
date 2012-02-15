package pl.nk.social.api.connect;

import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import pl.nk.social.api.Nk;

/**
 */
public class NkConnectionFactory extends OAuth2ConnectionFactory<Nk> {

    /**
     * Constructor for NkConnectionFactory.
     * @param clientKey String
     * @param clientSecret String
     * @param resource ProtectedResourceDetails
     */
    public NkConnectionFactory(String clientKey, String clientSecret, ProtectedResourceDetails resource) {

        super("nk", new NkServiceProvider(clientKey, clientSecret, resource), new NkApiAdapter());
    }
}
