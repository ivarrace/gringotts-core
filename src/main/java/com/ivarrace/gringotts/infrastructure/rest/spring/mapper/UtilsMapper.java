package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.Month;
import java.util.Locale;

@Mapper
public interface UtilsMapper {

    @Named("nameToKey")
    static String nameToKey(String name) {
        return name.trim().replace(" ", "_").toLowerCase(Locale.ROOT);
    }

    @Named("getMonthOrdinal")
    static int getMonthOrdinal(Month month) {
        return month.getValue();
    }

}
