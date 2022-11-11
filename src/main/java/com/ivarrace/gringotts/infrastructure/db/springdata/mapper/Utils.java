package com.ivarrace.gringotts.infrastructure.db.springdata.mapper;

import com.ivarrace.gringotts.domain.exception.InvalidParameterException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static UUID toUUID(String id) throws InvalidParameterException {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new InvalidParameterException("id", id);
        }
    }

    public static String nameToKey(String name) {
        return name.trim().replace(" ", "_").toLowerCase(Locale.ROOT);
    }
}
