package org.springframework.social.nk.api.impl;

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.social.test.client.RequestMatchers.*;
import static org.springframework.social.test.client.ResponseCreators.*;
 
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.nk.api.NkProfile;
import org.springframework.social.test.client.MockRestServiceServer;



public class NkTemplateTest {

	private NkTemplate nk = new NkTemplate("accesstoken");
	private MockRestServiceServer mockServer = MockRestServiceServer.createServer(nk.getRestTemplate());
	
	@Test
	public void getUserProfile() {
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    mockServer.expect(requestTo("http://java1.omega.nknet/v09/social/rest/people/@me"))
	        .andExpect(method(GET))
	        .andRespond(withResponse(jsonResource("nk-profile"), responseHeaders));

	    NkProfile profile = nk.getUserProfile();
	    assertEquals("person.123", profile.getId());
	    assertEquals("John Doe", profile.getName());
	}

	
	protected Resource jsonResource(String filename) {
		return new ClassPathResource(filename + ".json", getClass());
	}
}
