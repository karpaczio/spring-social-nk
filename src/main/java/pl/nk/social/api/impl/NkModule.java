package pl.nk.social.api.impl;

import org.apache.shindig.protocol.RestfulCollection;
import org.apache.shindig.social.core.model.AlbumImpl;
import org.apache.shindig.social.opensocial.model.Album;
import org.apache.shindig.social.opensocial.model.MediaItem;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.map.module.SimpleModule;

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
        super("NkModule", new Version(1, 0, 1, "SNAPSHOT"));
    }

    /**
     * Method setupModule.
     * @param context SetupContext
     */
    @Override
    public void setupModule(SetupContext context) {

        
        setMixInAnnotation(context, Album.class, AlbumMixIn.class);
        setMixInAnnotation(context, AlbumImpl.class, AlbumMixIn.class);
        setMixInAnnotation(context, ApplicationMediaItem.class, ApplicationMediaItemMixIn.class);
        setMixInAnnotation(context, ApplicationMediaItemImpl.class, ApplicationMediaItemMixIn.class);
        setMixInAnnotation(context, NkActivity.class, NkActivityMixIn.class);
        setMixInAnnotation(context, NkActivityImpl.class, NkActivityMixIn.class);
        setMixInAnnotation(context, NkPerson.class, NkPersonMixIn.class);
        setMixInAnnotation(context, NkPersonImpl.class, NkPersonMixIn.class);
        setMixInAnnotation(context, RestfulCollection.class, RestfulCollectionMixIn.class);

        
        // This is because instead of IMAGE we receive image in case of MediaItem.Type
        // In jackson-2.0.0 use @JsonValue instead
        context.insertAnnotationIntrospector(new NkJacksonAnnotationIntrospector());

        
        super.setupModule(context);
    }
    
    private void setMixInAnnotation(SetupContext context, Class<?> target, Class<?> mixinSource) {
        context.setMixInAnnotations(target, mixinSource);
        // context.getSerializationConfig().addMixInAnnotations(target, mixinSource);
        // context.getDeserializationConfig().addMixInAnnotations(target, mixinSource);
    }

/*    *//**
     *//*
    class CustomEnumResolver<T extends Enum<T>> extends EnumResolver<T> {
        *//**
         * Constructor for CustomEnumResolver.
         * @param enumClass Class<T>
         * @param enums T[]
         * @param map HashMap<String,T>
         *//*
        CustomEnumResolver(Class<T> enumClass, T[] enums, HashMap<String, T> map) {
            super(enumClass, enums, map);
        }
    }
*/
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
