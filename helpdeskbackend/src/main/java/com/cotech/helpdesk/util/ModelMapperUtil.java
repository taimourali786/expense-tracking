package com.cotech.helpdesk.util;

import com.cotech.helpdesk.model.ConvertableToEntity;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ModelMapperUtil {

    private ModelMapperUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static <T, K> Converter<T, K> createConverter(final ConvertableToEntity<T, K> service) {
        return new Converter<T, K>() {
            @Override
            public K convert(MappingContext<T, K> context) {
                T leadId = context.getSource();
                if (leadId != null) {
                    return service.findEntityById(context.getSource());
                }
                return null;
            }
        };
    }
}
