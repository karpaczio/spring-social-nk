package org.springframework.social.nk.connect;

import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.social.nk.api.Nk;
import org.springframework.social.nk.api.impl.NkTemplate;
import org.springframework.social.nk.oauth2.NkOAuth2Template;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 */
public class NkServiceProvider extends AbstractOAuth2ServiceProvider<Nk> {

    /**
     * Field resource.
     */
    private final ProtectedResourceDetails resource;

    /**
     * Change to OAuth2Template when bug https://mgt.nkdev.pl/jira/browse/NK-12707 will be fixed.
     * @param clientKey
     * @param clientSecret
     * @param resource
     */
    public NkServiceProvider(String clientKey, String clientSecret, ProtectedResourceDetails resource) {
        super(new NkOAuth2Template(clientKey, clientSecret, "https://nk.pl/oauth2/login",
        // "http://ssl.3pp.omega.nknet/oauth/authenticate",
                "https://nk.pl/oauth2/token"));
        this.resource = resource;

    }

    /**
     * Method getApi.
     * @param accessToken String
     * @return Nk
     * @see org.springframework.social.oauth2.OAuth2ServiceProvider#getApi(String)
     */
    public Nk getApi(String accessToken) {
        return new NkTemplate(accessToken, this.resource);
    }

}
