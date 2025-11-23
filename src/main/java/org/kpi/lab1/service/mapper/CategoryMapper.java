package org.kpi.lab1.service.mapper;

import java.util.List;
import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.dto.category.CategoryDto;
import org.kpi.lab1.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    List<Category> toCategories(List<CategoryEntity> categories);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    Category toCategory(CategoryEntity category);

    CategoryEntity toCategoryEntity(CategoryDto category);
}
