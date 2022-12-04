package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import com.ivarrace.gringotts.domain.accountancy.Accountancy;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.response.AccountancyResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.command.NewAccountancyCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UtilsMapper.class})
public interface AccountancyMapper {

    AccountancyMapper INSTANCE = Mappers.getMapper(AccountancyMapper.class);

    AccountancyResponse toResponse(Accountancy accountancy);

    List<AccountancyResponse> toResponseList(List<Accountancy> accountancyList);

    @Mapping(target = "key", source = "command.name", qualifiedByName = "nameToKey")
    Accountancy toDomain(NewAccountancyCommand command);

}