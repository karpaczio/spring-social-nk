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

public interface Nk extends ApiBinding {

    AbusesOperations<AbusesTemplate> abusesOperations();

    ActivityOperations<ActivityTemplate> activityOperations();

    AlbumOperations<AlbumTemplate> albumOperations();

    ApplicationOperations<ApplicationTemplate> applicationOperations();

    CommentOperations<CommentTemplate> commentOperations();

    GiftOperations<GiftTemplate> giftOperations();

    GroupOperations<GroupTemplate> groupOperations();

    MediaItemOperations<MediaItemTemplate> mediaItemOperations();

    MessageOperations<MessageTemplate> messageOperations();

    PaymentOperations<PaymentTemplate> paymentOperations();

    PeopleOperations<PeopleTemplate> peopleOperations();

    RateOperations<RateTemplate> rateOperations();

    RecommendOperations<RecommendTemplate> recommendOperations();

    PublicOperations publicOperations();
}
