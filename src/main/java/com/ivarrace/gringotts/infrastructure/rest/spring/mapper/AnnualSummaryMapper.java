package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import com.ivarrace.gringotts.domain.summary.AnnualSummary;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.AnnualSummaryResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UtilsMapper.class})
public interface AnnualSummaryMapper {

    AnnualSummaryMapper INSTANCE = Mappers.getMapper(AnnualSummaryMapper.class);

    AnnualSummaryResponse toResponse(AnnualSummary annualSummary);

}