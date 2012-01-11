package org.springframework.social.nk.api.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import java.io.FileReader;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.social.nk.api.NkProfile;
import org.springframework.social.test.client.MockRestServiceServer;

public class NkTemplateTest {

	private ProtectedResourceDetails resource = mock(ProtectedResourceDetails.class);
	private NkTemplate nk = new NkTemplate("accesstoken", resource);
	private MockRestServiceServer mockServer = MockRestServiceServer
			.createServer(nk.getRestTemplate());

	@Test
	public void getUserProfile() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);

		mockServer
				.expect(requestTo("http://java1.omega.nknet/v09/social/rest/people/@me"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("nk-profile"),
								responseHeaders));

		NkProfile profile = nk.getUserProfile();
		assertEquals("person.1f68d19f92386d8c", profile.getId()); 
		assertEquals("Lukasz Uruski", profile.getDisplayName());
	}

	protected Resource jsonResource(String filename) {
		return new ClassPathResource(filename + ".json", getClass());
	}
}
