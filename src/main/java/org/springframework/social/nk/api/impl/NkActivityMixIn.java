package org.springframework.social.nk.api.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shindig.social.opensocial.model.MediaItem;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import pl.nk.opensocial.model.NkActivityImpl;
import pl.nk.opensocial.model.NkPersonImpl;

/**
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
@JsonDeserialize(as = NkActivityImpl.class)
@JsonSerialize(include = Inclusion.NON_NULL)
public class NkActivityMixIn {

    /**
     * Field id.
     */
    @JsonProperty("id")
    private String id;

    /**
     * Field appId.
     */
    @JsonProperty("appId")
    private String appId;

    /**
     * Field postedTime.
     */
    @JsonProperty("postedTime")
    private Long postedTime;

    /**
     * Field title.
     */
    @JsonProperty("title")
    private String title;

    /**
     * Field userId.
     */
    @JsonProperty("userId")
    private String userId;

    /**
     * Field nkCommentsCount.
     */
    @JsonProperty("nkCommentsCount")
    private Integer nkCommentsCount;

    /**
     * Field nkStarsCount.
     */
    @JsonProperty("nkStarsCount")
    private Integer nkStarsCount;

    /**
     * Field nkStarsAuthorsIds.
     */
    @JsonProperty("nkStarsAuthrosIds")
    private List<String> nkStarsAuthorsIds;

    /**
     * Field nkAppId.
     */
    @JsonProperty("nkAppId")
    private String nkAppId;

    /**
     * Field nkGroupId.
     */
    @JsonProperty("nkGroupId")
    private String nkGroupId;

    /**
     * Field nkOrigins.
     */
    @JsonProperty("nkOrigins")
    private List<String> nkOrigins;

    /**
     * Field nkImageUrl.
     */
    @JsonProperty("nkImageUrl")
    private String nkImageUrl;

    /**
     * Field nkDefaultDescription.
     */
    @JsonProperty("nkDefaultDescription")
    private String nkDefaultDescription;

    /**
     * Field nkSiteUrl.
     */
    @JsonProperty("nkSiteUrl")
    private String nkSiteUrl;

    /**
     * Field nkSiteDescription.
     */
    @JsonProperty("nkSiteDescription")
    private String nkSiteDescription;

    /**
     * Field nkSiteTitle.
     */
    @JsonProperty("nkSiteTitle")
    private String nkSiteTitle;

    /**
     * Field nkSiteImageUrl.
     */
    @JsonProperty("nkSiteImageUrl")
    private String nkSiteImageUrl;

}
