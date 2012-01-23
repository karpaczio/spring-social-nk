package org.springframework.social.nk.api;

import org.springframework.social.nk.api.impl.CommonOpenSocialOperations;

import pl.nk.opensocial.model.NkActivity;
import pl.nk.opensocial.model.RestfulObject;

@SuppressWarnings("rawtypes")
public interface ActivityOperations <T extends ActivityOperations> extends CommonOpenSocialOperations<T> {

    RestfulObject<NkActivity> addActivityForAll(String title);
    RestfulObject<NkActivity> addActivityForFriends(String title);
}
