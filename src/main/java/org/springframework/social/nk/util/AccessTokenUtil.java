package org.springframework.social.nk.util;

import java.util.Map;

import org.springframework.security.oauth.consumer.AccessTokenRequiredException;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.OAuthSecurityContext;
import org.springframework.security.oauth.consumer.OAuthSecurityContextHolder;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;

public class AccessTokenUtil {

    public static OAuthConsumerToken getAccessToken(ProtectedResourceDetails resource) {
        OAuthSecurityContext context = OAuthSecurityContextHolder.getContext();
        if (context == null) {
            throw new IllegalStateException(
                    "No OAuth security context has been established. Unable to access resource '" + resource.getId()
                            + "'.");
        }

        Map<String, OAuthConsumerToken> accessTokens = context.getAccessTokens();
        OAuthConsumerToken accessToken = accessTokens == null ? null : accessTokens.get(resource.getId());
        if (accessToken == null) {
            throw new AccessTokenRequiredException(
                    "No OAuth security context has been established. Unable to access resource '" + resource.getId()
                            + "'.", resource);
        }
        return accessToken;
    }
}
