package pl.nk.social.api.oauth.consumer.client;

import static pl.nk.social.api.oauth.common.NkOAuthConsumerParameter.oauth_body_hash;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpRequest;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;

import pl.nk.social.api.util.AccessTokenUtil;

/**
 */
public class NkCoreOAuthConsumerSupport extends OAuthConsumerSupport {

    /**
     * Field oauthBodyHash.
     */
    private String oauthBodyHash;
    /**
     * Field resource.
     */
    private final ProtectedResourceDetails resource;

    /**
     * Constructor for NkCoreOAuthConsumerSupport.
     * 
     * @param resource
     *            ProtectedResourceDetails
     */
    public NkCoreOAuthConsumerSupport(ProtectedResourceDetails resource) {
        this.resource = resource;
    }

    /**
     * Method setOAuthBodyHash.
     * 
     * @param out
     *            OutputStream
     */
    public void setOAuthBodyHash(OutputStream out) {
        if (out instanceof ByteArrayOutputStream) {
            ByteArrayOutputStream byteOut = (ByteArrayOutputStream) out;
            oauthBodyHash = new String(Base64.encodeBase64(DigestUtils.sha(byteOut.toByteArray())));
        }
    }

    /**
     * Method getExtraOAuthParameters.
     * 
     * @param details
     *            ProtectedResourceDetails
     * @param requestURL
     *            URL
     * @param requestToken
     *            OAuthConsumerToken
     * @return Map<String,Set<CharSequence>>
     */
    @Override
    protected Map<String, Set<CharSequence>> getExtraOAuthParameters(ProtectedResourceDetails details, URL requestURL,
            OAuthConsumerToken requestToken) {
        Map<String, Set<CharSequence>> oauthParams = new TreeMap<String, Set<CharSequence>>();

        if (oauthBodyHash != null) {
            oauthParams.put(oauth_body_hash.toString(), Collections.singleton((CharSequence) oauthBodyHash));
        }
        return oauthParams;
    }

    /**
     * Method addAuthorizationHeader.
     * 
     * @param outputMessage
     *            HttpOutputMessage
     * @throws IOException
     */
    public void addAuthorizationHeader(HttpOutputMessage outputMessage) throws IOException {

        OAuthConsumerToken accessToken = AccessTokenUtil.getAccessToken(resource);

        if (HttpRequest.class.isAssignableFrom(outputMessage.getClass())) {
            HttpRequest req = (HttpRequest) outputMessage;

            URL url = new URL(req.getURI().toString());
            String httpMethod = req.getMethod().toString();

            String authHeader = this.getAuthorizationHeader(resource, accessToken, url, httpMethod, null);
            req.getHeaders().set("Authorization", authHeader);
        }
    }

}
