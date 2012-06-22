package pl.nk.social.api.connect;

import org.springframework.security.oauth.common.signature.SharedConsumerSecret;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

import pl.nk.social.api.Nk;
import pl.nk.social.api.impl.NkTemplate;

/**
 * @author karpaczio
 */
public class NkServiceProvider extends AbstractOAuth2ServiceProvider<Nk> {

    private final static String AUTHENTICATION_SERVER_URL_DEFAULT = "https://nk.pl";
    private final static String LOGIN_ENDPOINT_SUFFIX = "/oauth2/login";
    private final static String TOKEN_ENDPOINT_SUFFIX = "/oauth2/token";

    /**
     * Field resource.
     */
    private final ProtectedResourceDetails resource;

    private final String resourceServerUrl;

    public NkServiceProvider(String clientKey, String clientSecret) {
        this(clientKey, clientSecret, AUTHENTICATION_SERVER_URL_DEFAULT, null);
    }

    public NkServiceProvider(String clientKey, String clientSecret, String authenticationServerUrl,
            String resourceServerUrl) {
        super(new OAuth2Template(clientKey, clientSecret, authenticationServerUrl + LOGIN_ENDPOINT_SUFFIX,
                authenticationServerUrl + TOKEN_ENDPOINT_SUFFIX));
        BaseProtectedResourceDetails baseResource = new BaseProtectedResourceDetails();
        baseResource.setConsumerKey(clientKey);
        SignatureSecret secret = new SharedConsumerSecret(clientSecret);
        baseResource.setSharedSecret(secret);
        baseResource.setId("nk");
        this.resource = baseResource;
        this.resourceServerUrl = resourceServerUrl;
    }

    /**
     * Method getApi.
     * 
     * @param accessToken
     *            String
     * @return Nk
     * @see org.springframework.social.oauth2.OAuth2ServiceProvider#getApi(String)
     */
    public Nk getApi(String accessToken) {
        if (resourceServerUrl != null) {
            return new NkTemplate(accessToken, this.resource, this.resourceServerUrl);
        } else {
            return new NkTemplate(accessToken, this.resource);
        }
    }

}
