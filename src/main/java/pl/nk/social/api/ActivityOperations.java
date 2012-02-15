package pl.nk.social.api;


import pl.nk.opensocial.model.NkActivity;
import pl.nk.opensocial.model.RestfulObject;
import pl.nk.social.api.impl.CommonOpenSocialOperations;

/**
 */
@SuppressWarnings("rawtypes")
public interface ActivityOperations <T extends ActivityOperations> extends CommonOpenSocialOperations<T> {

    /**
     * Method addActivityForAll.
     * @param title String
     * @return RestfulObject<NkActivity>
     */
    RestfulObject<NkActivity> addActivityForAll(String title);
    /**
     * Method addActivityForFriends.
     * @param title String
     * @return RestfulObject<NkActivity>
     */
    RestfulObject<NkActivity> addActivityForFriends(String title);
}
