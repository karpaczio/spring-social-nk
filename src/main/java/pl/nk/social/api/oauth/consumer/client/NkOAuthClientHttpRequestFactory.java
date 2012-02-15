package pl.nk.social.api.oauth.consumer.client;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.security.oauth.consumer.OAuthConsumerSupport;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthClientHttpRequestFactory;

import pl.nk.social.api.oauth.common.NkOAuthConsumerParameter;
import pl.nk.social.api.util.AccessTokenUtil;

/**
 */
public class NkOAuthClientHttpRequestFactory extends OAuthClientHttpRequestFactory {

    /**
     * Field delegate.
     */
    private final ClientHttpRequestFactory delegate;
    /**
     * Field resource.
     */
    private final ProtectedResourceDetails resource;
    /**
     * Field support.
     */
    private final OAuthConsumerSupport support;

    /**
     * Constructor for NkOAuthClientHttpRequestFactory.
     * @param delegate ClientHttpRequestFactory
     * @param resource ProtectedResourceDetails
     * @param support OAuthConsumerSupport
     */
    public NkOAuthClientHttpRequestFactory(ClientHttpRequestFactory delegate, ProtectedResourceDetails resource,
            OAuthConsumerSupport support) {
        super(delegate, resource, support);
        this.delegate = delegate;
        this.resource = resource;
        this.support = support;
    }

    /**
     * Method createRequest.
     * @param uri URI
     * @param httpMethod HttpMethod
     * @return ClientHttpRequest
     * @throws IOException
     * @see org.springframework.http.client.ClientHttpRequestFactory#createRequest(URI, HttpMethod)
     */
    @Override
    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {


        OAuthConsumerToken accessToken = AccessTokenUtil.getAccessToken(resource);

        boolean useAuthHeader = this.resource.isAcceptsAuthorizationHeader();
        if (!useAuthHeader) {
            String queryString = this.support.getOAuthQueryString(this.resource, null, uri.toURL(), httpMethod.name(),
                    this.getAdditionalOAuthParameters());
            String uriValue = String.valueOf(uri);
            uri = URI.create(uriValue.contains("?") ? uriValue + "&" + queryString : uriValue + "?" + queryString);
        }

        uri = addNkToken(uri, accessToken);

        ClientHttpRequest req = this.delegate.createRequest(uri, httpMethod);
        if (useAuthHeader) {
            String authHeader = this.support.getAuthorizationHeader(this.resource, null, uri.toURL(),
                    httpMethod.name(), this.getAdditionalOAuthParameters());
            req.getHeaders().add("Authorization", authHeader);
        }

        Map<String, String> additionalHeaders = this.resource.getAdditionalRequestHeaders();
        if (additionalHeaders != null) {
            for (Map.Entry<String, String> header : additionalHeaders.entrySet()) {
                req.getHeaders().add(header.getKey(), header.getValue());
            }
        }
        return req;
    }

    /**
     * Method addNkToken.
     * @param uri URI
     * @param requestToken OAuthConsumerToken
     * @return URI
     */
    private URI addNkToken(URI uri, OAuthConsumerToken requestToken) {
        if ((requestToken != null) && (requestToken.getValue() != null)) {
            String uriValue = String.valueOf(uri);
            String queryString = NkOAuthConsumerParameter.nk_token.toString() + "=" + requestToken.getValue();
            return URI.create(uriValue.contains("?") ? uriValue + "&" + queryString : uriValue + "?" + queryString);

        }
        return uri;
    }
}
