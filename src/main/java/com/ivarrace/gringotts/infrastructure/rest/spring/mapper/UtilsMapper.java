package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import com.ivarrace.gringotts.application.exception.InvalidParameterException;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Locale;
import java.util.UUID;

@Mapper
public interface UtilsMapper {

    @Named("nameToKey")
    static String nameToKey(String name) {
        return name.trim().replace(" ", "_").toLowerCase(Locale.ROOT);
    }

    @Named("stringToUUID")
    static UUID toUUID(String id) throws InvalidParameterException {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new InvalidParameterException("id");
        }
    }
}
