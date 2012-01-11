package org.springframework.social.nk.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.nk.api.Nk;
import org.springframework.social.nk.api.NkProfile;

public class NkApiAdapter implements ApiAdapter<Nk> {

	public boolean test(Nk nk) {
		try {
			nk.getUserProfile();
			return true;
		} catch (ApiException e) {

			return false;
		}
	}
	
	public UserProfile fetchUserProfile(Nk nk) {
		NkProfile profile = nk.getUserProfile();
		return new UserProfileBuilder().setName(profile.getDisplayName()).build();
		// TODO set email
	}

	public void setConnectionValues(Nk api, ConnectionValues values) {
		NkProfile profile = api.getUserProfile();
		values.setDisplayName(profile.getDisplayName());
		values.setProviderUserId(profile.getId());
		// TODO

	}

	public void updateStatus(Nk arg0, String arg1) {
		// TODO unsupported

	}

}
