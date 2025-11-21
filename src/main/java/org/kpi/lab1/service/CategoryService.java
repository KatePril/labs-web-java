package org.kpi.lab1.service;

import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.dto.category.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category addCategory(CategoryDto categoryDto);
    void deleteCategoryById(Long id);

}
