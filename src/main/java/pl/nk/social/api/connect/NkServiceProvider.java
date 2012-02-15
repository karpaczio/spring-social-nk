package pl.nk.social.api.connect;

import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

import pl.nk.social.api.Nk;
import pl.nk.social.api.impl.NkTemplate;

/**
 */
public class NkServiceProvider extends AbstractOAuth2ServiceProvider<Nk> {

    /**
     * Field resource.
     */
    private final ProtectedResourceDetails resource;

    /**
     * @param clientKey
     * @param clientSecret
     * @param resource
     */
    public NkServiceProvider(String clientKey, String clientSecret, ProtectedResourceDetails resource) {
        super(new OAuth2Template(clientKey, clientSecret, "https://nk.pl/oauth2/login",
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
