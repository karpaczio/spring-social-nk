package org.springframework.social.nk.http.converter.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.social.nk.oauth.consumer.client.NkCoreOAuthConsumerSupport;
import org.springframework.util.MultiValueMap;

public class NkFormHttpMessageConverter extends FormHttpMessageConverter {

    private final NkCoreOAuthConsumerSupport oauthConsumerSupport;

    public NkFormHttpMessageConverter(NkCoreOAuthConsumerSupport oauthConsumerSupport) {
        this.oauthConsumerSupport = oauthConsumerSupport;
    }

    /**
     * Nk requires oauth_body_hash in every POST and PUT request.
     */
    @Override
    public void write(MultiValueMap<String, ?> map, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        super.write(map, contentType, outputMessage);

        if (isMultipart(map, contentType)) {
            OutputStream out = outputMessage.getBody();
            ByteArrayOutputStream byteOut = (ByteArrayOutputStream) out;

            this.oauthConsumerSupport.setOAuthBodyHash(byteOut.toByteArray());
            this.oauthConsumerSupport.addAuthorizationHeader(outputMessage);
        }
    }

    /**
     * Private method in subclass.
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
     * Nk requires filename or it will not recognize it as photo.
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
