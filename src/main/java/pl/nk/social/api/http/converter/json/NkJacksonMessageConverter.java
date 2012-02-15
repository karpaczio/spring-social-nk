package pl.nk.social.api.http.converter.json;

import java.io.IOException;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import pl.nk.social.api.oauth.consumer.client.NkCoreOAuthConsumerSupport;

/**
 * Nk requires oauth_body_hash in each PUT and POST requests.
 * @author luruski
 *
 * @version $Revision: 1.0 $
 */
public class NkJacksonMessageConverter extends TypeReferenceJacksonMessageConverter {

    /**
     * Field oauthConsumerSupport.
     */
    private final NkCoreOAuthConsumerSupport oauthConsumerSupport;

    /**
     * Constructor for NkJacksonMessageConverter.
     * @param oauthConsumerSupport NkCoreOAuthConsumerSupport
     */
    public NkJacksonMessageConverter(NkCoreOAuthConsumerSupport oauthConsumerSupport) {
        super();
        this.oauthConsumerSupport = oauthConsumerSupport;
    }
    
    /**
     * Method writeInternal.
     * @param object Object
     * @param outputMessage HttpOutputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        super.writeInternal(object, outputMessage);

        this.oauthConsumerSupport.setOAuthBodyHash(outputMessage.getBody());
        this.oauthConsumerSupport.addAuthorizationHeader(outputMessage);
    }

}
