package pl.nk.social.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shindig.social.core.model.AddressImpl;
import org.apache.shindig.social.core.model.ListFieldImpl;
import org.apache.shindig.social.core.model.NameImpl;
import org.apache.shindig.social.core.model.UrlImpl;
import org.apache.shindig.social.opensocial.model.Address;
import org.apache.shindig.social.opensocial.model.ListField;
import org.apache.shindig.social.opensocial.model.Name;
import org.apache.shindig.social.opensocial.model.Person.Gender;
import org.apache.shindig.social.opensocial.model.Url;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import pl.nk.opensocial.model.NkPersonImpl;

/**
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
@JsonDeserialize(as = NkPersonImpl.class)
public abstract class NkPersonMixIn {

    /**
     * Field id.
     */
    @JsonProperty("id")
    private String id;

    /**
     * Field age.
     */
    @JsonProperty("age")
    private Integer age;

    /**
     * Field currentLocation.
     */
    @JsonProperty("currentLocation")
    @JsonDeserialize(as = AddressImpl.class)
    private Address currentLocation;

    /**
     * Field displayName.
     */
    @JsonProperty("displayName")
    private String displayName;

    /**
     * Field nkFriendsCount.
     */
    @JsonProperty("nkFriendsCount")
    private Integer nkFriendsCount;

    /**
     * Field gender.
     */
    @JsonProperty("gender")
    private Gender gender;

    /**
     * Field hasApp.
     */
    @JsonProperty("hasApp")
    private Boolean hasApp;

    /**
     * Field name.
     */
    @JsonProperty("name")
    @JsonDeserialize(as = NameImpl.class)
    private Name name;

    /**
     * Field phoneNumbers.
     */
    @JsonProperty("phoneNumbers")
    @JsonDeserialize(as = ArrayList.class, contentAs = ListFieldImpl.class)
    private List<ListField> phoneNumbers;

    /**
     * Field photos.
     */
    @JsonProperty("photos")
    @JsonDeserialize(as = ArrayList.class, contentAs = ListFieldImpl.class)
    private List<ListField> photos;

    /**
     * Field profileUrl.
     */
    @JsonProperty("profileUrl")
    private String profileUrl;

    /**
     * Field thumbnailUrl.
     */
    @JsonProperty("thumbnailUrl")
    private String thumbnailUrl;

    /**
     * Field urls.
     */
    @JsonProperty("urls")
    @JsonDeserialize(as = ArrayList.class, contentAs = UrlImpl.class)
    private List<Url> urls;
}
