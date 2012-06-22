package pl.nk.social.api.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import pl.nk.social.api.Nk;

/**
 */
public class NkConnectionFactory extends OAuth2ConnectionFactory<Nk> {

    /**
     * Constructor for NkConnectionFactory.
     * @param clientKey String
     * @param clientSecret String
     */
    public NkConnectionFactory(String clientKey, String clientSecret) {

        super("nk", new NkServiceProvider(clientKey, clientSecret), new NkApiAdapter());
    }
    
    public NkConnectionFactory(String clientKey, String clientSecret, String authenticationServerUrl, String resourceServerUrl) {

        super("nk", new NkServiceProvider(clientKey, clientSecret, authenticationServerUrl, resourceServerUrl), new NkApiAdapter());
    }
    
}
