package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import com.ivarrace.gringotts.domain.summary.MonthSummary;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.response.MonthSummaryResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UtilsMapper.class})
public interface MonthSummaryMapper {

    MonthSummaryMapper INSTANCE = Mappers.getMapper(MonthSummaryMapper.class);

    MonthSummaryResponse toResponse(MonthSummary monthSummary);

}