package pl.nk.social.api.http.converter.json;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.MultiValueMap;

import pl.nk.social.api.oauth.consumer.client.NkCoreOAuthConsumerSupport;

/**
 */
public class NkFormHttpMessageConverter extends FormHttpMessageConverter {

    /**
     * Field oauthConsumerSupport.
     */
    private final NkCoreOAuthConsumerSupport oauthConsumerSupport;

    /**
     * Constructor for NkFormHttpMessageConverter.
     * @param oauthConsumerSupport NkCoreOAuthConsumerSupport
     */
    public NkFormHttpMessageConverter(NkCoreOAuthConsumerSupport oauthConsumerSupport) {
        this.oauthConsumerSupport = oauthConsumerSupport;
    }

    /**
     * Nk requires oauth_body_hash in every POST and PUT request.
     * @param map MultiValueMap<String,?>
     * @param contentType MediaType
     * @param outputMessage HttpOutputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    public void write(MultiValueMap<String, ?> map, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        super.write(map, contentType, outputMessage);

        if (isMultipart(map, contentType)) {
            this.oauthConsumerSupport.setOAuthBodyHash(outputMessage.getBody());
            this.oauthConsumerSupport.addAuthorizationHeader(outputMessage);
        }
    }

    /**
     * Private method in subclass.
     * @param map MultiValueMap<String,?>
     * @param contentType MediaType
     * @return boolean
     */
    protected boolean isMultipart(MultiValueMap<String, ?> map, MediaType contentType) {
        if (contentType != null) {
            return MediaType.MULTIPART_FORM_DATA.equals(contentType);
        }
        for (String name : map.keySet()) {
            for (Object value : map.get(name)) {
                if (value != null && !(value instanceof String)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Nk requires filename or will not recognize photo in multipart request.
     * @param part Object
     * @return String
     */
    @Override
    protected String getFilename(Object part) {
        if (part instanceof Resource) {
            Resource resource = (Resource) part;
            return resource.getFilename();
        } else if (part instanceof byte[]) {
            return "";
        } else {
            return null;
        }
    }
}
