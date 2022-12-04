package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import com.ivarrace.gringotts.domain.accountancy.Category;
import com.ivarrace.gringotts.domain.accountancy.GroupType;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.response.CategoryResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.command.NewCategoryCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy =
        InjectionStrategy.CONSTRUCTOR, uses = {UtilsMapper.class,
        GroupMapper.class})
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponse(List<Category> categories);

    @Mapping(target = "key", source = "command.name", qualifiedByName ="nameToKey")
    @Mapping(target = "group.key", source = "groupKey")
    @Mapping(target = "group.type", source = "groupType")
    @Mapping(target = "group.accountancy.key", source = "accountancyKey")
    Category toDomain(NewCategoryCommand command, String groupKey,
                      GroupType groupType, String accountancyKey);
}