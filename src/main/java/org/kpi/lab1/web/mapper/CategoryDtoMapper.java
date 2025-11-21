package org.kpi.lab1.web.mapper;

import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.dto.category.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryDtoMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    Category toCategory(CategoryDto categoryDto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    CategoryDto toCategoryDto(Category category);
}
