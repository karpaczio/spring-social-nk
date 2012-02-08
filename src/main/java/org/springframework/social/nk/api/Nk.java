package org.springframework.social.nk.api;

import org.springframework.social.ApiBinding;
import org.springframework.social.nk.api.impl.AbusesTemplate;
import org.springframework.social.nk.api.impl.ActivityTemplate;
import org.springframework.social.nk.api.impl.AlbumTemplate;
import org.springframework.social.nk.api.impl.ApplicationTemplate;
import org.springframework.social.nk.api.impl.CommentTemplate;
import org.springframework.social.nk.api.impl.GiftTemplate;
import org.springframework.social.nk.api.impl.GroupTemplate;
import org.springframework.social.nk.api.impl.MediaItemTemplate;
import org.springframework.social.nk.api.impl.MessageTemplate;
import org.springframework.social.nk.api.impl.PaymentTemplate;
import org.springframework.social.nk.api.impl.PeopleTemplate;
import org.springframework.social.nk.api.impl.RateTemplate;
import org.springframework.social.nk.api.impl.RecommendTemplate;

/**
 */
public interface Nk extends ApiBinding {

    /**
     * Report abuses. 
     * Required scopes: NOT AVAILABLE YET
     * @return AbusesOperations<AbusesTemplate>
     */
    AbusesOperations<AbusesTemplate> abusesOperations();

    /**
     * Create and fetch activities. <br/>
     * Required scopes: 
     * <ul>
     * <li>{@link org.springframework.social.nk.api.impl.Scope#CREATE_PHOTOS_ROLE}</li>
     * </ul>
     * @return ActivityOperations<ActivityTemplate>
     */
    ActivityOperations<ActivityTemplate> activityOperations();

    /**
     * Fetch photo albums.
     * Required scopes: 
     * <ul>
     * <li>{@link org.springframework.social.nk.api.impl.Scope#PICTURES_PROFILE_ROLE}</li>
     * <li>{@link org.springframework.social.nk.api.impl.Scope#CREATE_PHOTOS_ROLE}</li>
     * </ul>
     * @return AlbumOperations<AlbumTemplate>
     */
    AlbumOperations<AlbumTemplate> albumOperations();

    /**
     * Fetch registered applications. 
     * Required scopes: NOT AVAILABLE YET
     * @return ApplicationOperations<ApplicationTemplate>
     */
    ApplicationOperations<ApplicationTemplate> applicationOperations();

    /**
     * Add new comments. Fetch existing ones.
     * Required scopes: NOT AVAILABLE YET 
     * @return CommentOperations<CommentTemplate>
     */
    CommentOperations<CommentTemplate> commentOperations();

    /**
     * Send gift. 
     * Required scopes: NOT AVAILABLE YET
     * @return GiftOperations<GiftTemplate>
     */
    GiftOperations<GiftTemplate> giftOperations();

    /**
     * Fetch registered groups. 
     * Required scopes: NOT AVAILABLE YET
     * @return GroupOperations<GroupTemplate>
     */
    GroupOperations<GroupTemplate> groupOperations();

    /**
     * Add and fetch photos.
     * Required scopes: 
     * <ul>
     * <li>{@link org.springframework.social.nk.api.impl.Scope#CREATE_PHOTOS_ROLE}</li>
     * </ul>
     * @return MediaItemOperations<MediaItemTemplate>
     */
    MediaItemOperations<MediaItemTemplate> mediaItemOperations();

    /**
     * Send and fetch messages. 
     * Required scopes: 
     * <ul>
     * <li>{@link org.springframework.social.nk.api.impl.Scope#CREATE_SHOUTS_ROLE}</li>
     * </ul>
     * @return MessageOperations<MessageTemplate>
     */
    MessageOperations<MessageTemplate> messageOperations();

    /**
     * Transfer currency from one account to another.
     * Required scopes: NOT AVAILABLE YET
     * @return PaymentOperations<PaymentTemplate>
     */
    PaymentOperations<PaymentTemplate> paymentOperations();

    /**
     * Retrieve users' profiles.
     * Required scopes:  
     * <ul>
     * <li>{@link org.springframework.social.nk.api.impl.Scope#BASIC_PROFILE_ROLE}</li>
     * <li>{@link org.springframework.social.nk.api.impl.Scope#PERSON_FRIENDS_ROLE}</li>
     * <li>{@link org.springframework.social.nk.api.impl.Scope#PICTURES_PROFILE_ROLE}</li>
     * </ul>
     * @return PeopleOperations<PeopleTemplate>
     */
    PeopleOperations<PeopleTemplate> peopleOperations();

    /**
     * Rate photos.
     * Required scopes: NOT AVAILABLE YET
     * @return RateOperations<RateTemplate>
     */
    RateOperations<RateTemplate> rateOperations();

    /**
     * Recommend photos.
     * Required scopes: NOT AVAILABLE YET
     * @return RecommendOperations<RecommendTemplate>
     */
    RecommendOperations<RecommendTemplate> recommendOperations();

    /**
     * Fetch container's and applications' properties.
     * Required scopes: DOES NOT NEED ANY
     * @return PublicOperations
     */
    PublicOperations publicOperations();
}
