package org.springframework.social.nk.api.impl;

import org.apache.shindig.protocol.RestfulCollection;
import org.apache.shindig.social.opensocial.model.Album;
import org.codehaus.jackson.type.TypeReference;

import pl.nk.opensocial.model.ApplicationMediaItem;
import pl.nk.opensocial.model.NkActivity;
import pl.nk.opensocial.model.NkPerson;
import pl.nk.opensocial.model.RestfulObject;

public class TypeReferences {
    public static final TypeReference<RestfulObject<NkActivity>> NK_ACTIVITY_TYPE_REFERENCE = new TypeReference<RestfulObject<NkActivity>>() {
    };
    public static final TypeReference<RestfulCollection<ApplicationMediaItem>> MEDIA_ITEMS_TYPE_REFERENCE = new TypeReference<RestfulCollection<ApplicationMediaItem>>() {
    };
    public static final TypeReference<ApplicationMediaItem> MEDIA_ITEM_TYPE_REFERENCE = new TypeReference<ApplicationMediaItem>() {
    };
    public static final TypeReference<RestfulCollection<Album>> ALBUMS_TYPE_REFERENCE = new TypeReference<RestfulCollection<Album>>() {
    };
    public static final TypeReference<RestfulObject<NkPerson>> NK_PROFILE_TYPE_REFERENCE = new TypeReference<RestfulObject<NkPerson>>() {
    };
    public static final TypeReference<RestfulCollection<NkPerson>> NK_PROFILES_TYPE_REFERENCE = new TypeReference<RestfulCollection<NkPerson>>() {
    };



}
