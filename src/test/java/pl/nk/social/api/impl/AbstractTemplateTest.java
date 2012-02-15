package pl.nk.social.api.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.social.test.client.MockRestServiceServer;

import pl.nk.social.api.impl.NkTemplate;

/**
 */
public abstract class AbstractTemplateTest {

    /**
     * Field resource.
     */
    private ProtectedResourceDetails resource;
    /**
     * Field nk.
     */
    protected NkTemplate nk;
    /**
     * Field mockServer.
     */
    protected MockRestServiceServer mockServer; 
    
    /**
     * Method setup.
     */
    @Before
    public void setup() {
        this.resource = mock(ProtectedResourceDetails.class);
        when(resource.getId()).thenReturn("nk");
        
        this.nk = new NkTemplate("accesstoken", this.resource);
        
        this.mockServer = MockRestServiceServer.createServer(this.nk.getRestTemplate());
    }
    
    /**
     * Method jsonResource.
     * @param filename String
     * @return Resource
     */
    protected Resource jsonResource(String filename) {
        return  new ClassPathResource("/" + filename + ".json", getClass());
    }
    
    
}
