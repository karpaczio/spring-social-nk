package org.springframework.social.nk.oauth.consumer.client;

import static org.springframework.social.nk.oauth.common.NkOAuthConsumerParameter.oauth_body_hash;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpRequest;
import org.springframework.security.oauth.consumer.AccessTokenRequiredException;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.OAuthSecurityContext;
import org.springframework.security.oauth.consumer.OAuthSecurityContextHolder;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;

public class NkCoreOAuthConsumerSupport extends OAuthConsumerSupport {

    private String oauthBodyHash;
    private final ProtectedResourceDetails resource;

    public NkCoreOAuthConsumerSupport(ProtectedResourceDetails resource) {
        this.resource = resource;
    }

    public void setOAuthBodyHash(byte[] bodyAsBytes) {
        oauthBodyHash = new String(Base64.encodeBase64(DigestUtils.sha(bodyAsBytes)));
    }

    @Override
    protected Map<String, Set<CharSequence>> getExtraOAuthParameters(ProtectedResourceDetails details, URL requestURL,
            OAuthConsumerToken requestToken) {
        Map<String, Set<CharSequence>> oauthParams = new TreeMap<String, Set<CharSequence>>();

        if (oauthBodyHash != null) {
            oauthParams.put(oauth_body_hash.toString(), Collections.singleton((CharSequence) oauthBodyHash));
        }
        return oauthParams;
    }

    public void addAuthorizationHeader(HttpOutputMessage outputMessage) throws IOException {

        OAuthSecurityContext context = OAuthSecurityContextHolder.getContext();
        if (context == null) {
            throw new IllegalStateException(
                    "No OAuth security context has been established. Unable to access resource '"
                            + this.resource.getId() + "'.");
        }

        Map<String, OAuthConsumerToken> accessTokens = context.getAccessTokens();
        OAuthConsumerToken accessToken = accessTokens == null ? null : accessTokens.get(this.resource.getId());
        if (accessToken == null) {
            throw new AccessTokenRequiredException(
                    "No OAuth security context has been established. Unable to access resource '"
                            + this.resource.getId() + "'.", resource);
        }

        if (HttpRequest.class.isAssignableFrom(outputMessage.getClass())) {
            HttpRequest req = (HttpRequest) outputMessage;

            // Are we loosing some info right now ?
            URL url = new URL(req.getURI().toString());
            String httpMethod = req.getMethod().toString();

            String authHeader = this.getAuthorizationHeader(resource, accessToken, url, httpMethod, null);
            req.getHeaders().set("Authorization", authHeader);
        }
    }

}
