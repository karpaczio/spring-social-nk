package pl.nk.social.api.impl;

import java.util.HashMap;

import org.apache.shindig.protocol.RestfulCollection;
import org.apache.shindig.social.core.model.AlbumImpl;
import org.apache.shindig.social.opensocial.model.Album;
import org.apache.shindig.social.opensocial.model.MediaItem;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.util.EnumResolver;

import pl.nk.opensocial.model.ApplicationMediaItem;
import pl.nk.opensocial.model.ApplicationMediaItemImpl;
import pl.nk.opensocial.model.NkActivity;
import pl.nk.opensocial.model.NkActivityImpl;
import pl.nk.opensocial.model.NkPerson;
import pl.nk.opensocial.model.NkPersonImpl;

/**
 */
public class NkModule extends SimpleModule {

    /**
     * Constructor for NkModule.
     */
    public NkModule() {
        super("NkModule", new Version(0, 0, 1, "SNAPSHOT"));
    }

    /**
     * Method setupModule.
     * @param context SetupContext
     */
    @Override
    public void setupModule(SetupContext context) {

        setMixInAnnotation(Album.class, AlbumMixIn.class);
        setMixInAnnotation(AlbumImpl.class, AlbumMixIn.class);
        setMixInAnnotation(ApplicationMediaItem.class, ApplicationMediaItemMixIn.class);
        setMixInAnnotation(ApplicationMediaItemImpl.class, ApplicationMediaItemMixIn.class);
        setMixInAnnotation(NkActivity.class, NkActivityMixIn.class);
        setMixInAnnotation(NkActivityImpl.class, NkActivityMixIn.class);
        setMixInAnnotation(NkPerson.class, NkPersonMixIn.class);
        setMixInAnnotation(NkPersonImpl.class, NkPersonMixIn.class);
        setMixInAnnotation(RestfulCollection.class, RestfulCollectionMixIn.class);

        // This is because instead of IMAGE we receive image in case of MediaItem.Type
        // In jackson-2.0.0 use @JsonValue instead
        context.insertAnnotationIntrospector(new NkJacksonAnnotationIntrospector());

        super.setupModule(context);
    }

    /**
     */
    class CustomEnumResolver<T extends Enum<T>> extends EnumResolver<T> {
        /**
         * Constructor for CustomEnumResolver.
         * @param enumClass Class<T>
         * @param enums T[]
         * @param map HashMap<String,T>
         */
        CustomEnumResolver(Class<T> enumClass, T[] enums, HashMap<String, T> map) {
            super(enumClass, enums, map);
        }
    }

    /**
     */
    class NkJacksonAnnotationIntrospector extends JacksonAnnotationIntrospector {
        /**
         * Method findEnumValue.
         * @param value Enum<?>
         * @return String
         */
        @Override
        public String findEnumValue(Enum<?> value) {
            if (MediaItem.Type.class.equals(value.getClass())) {
                return value.toString();
            }
            return value.name();
        }
    }

}
