package org.springframework.social.nk.api.impl;

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

@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
@JsonDeserialize(as = NkPersonImpl.class)
public abstract class NkPersonMixIn {

    @JsonProperty("id")
    private String id;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("currentLocation")
    @JsonDeserialize(as = AddressImpl.class)
    private Address currentLocation;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("nkFriendsCount")
    private Integer nkFriendsCount;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("hasApp")
    private Boolean hasApp;

    @JsonProperty("name")
    @JsonDeserialize(as = NameImpl.class)
    private Name name;

    @JsonProperty("phoneNumbers")
    @JsonDeserialize(as = ArrayList.class, contentAs = ListFieldImpl.class)
    private List<ListField> phoneNumbers;

    @JsonProperty("photos")
    @JsonDeserialize(as = ArrayList.class, contentAs = ListFieldImpl.class)
    private List<ListField> photos;

    @JsonProperty("profileUrl")
    private String profileUrl;

    @JsonProperty("thumbnailUrl")
    private String thumbnailUrl;

    @JsonProperty("urls")
    @JsonDeserialize(as = ArrayList.class, contentAs = UrlImpl.class)
    private List<Url> urls;
}
