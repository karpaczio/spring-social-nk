package org.springframework.social.nk.api.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.social.test.client.MockRestServiceServer;

public abstract class AbstractTemplateTest {

    private ProtectedResourceDetails resource;
    protected NkTemplate nk;
    protected MockRestServiceServer mockServer; 
    
    @Before
    public void setup() {
        this.resource = mock(ProtectedResourceDetails.class);
        when(resource.getId()).thenReturn("nk");
        
        this.nk = new NkTemplate("accesstoken", this.resource);
        
        this.mockServer = MockRestServiceServer.createServer(this.nk.getRestTemplate());
    }
    
    protected Resource jsonResource(String filename) {
        return  new ClassPathResource("/" + filename + ".json", getClass());
    }
    
    
}
