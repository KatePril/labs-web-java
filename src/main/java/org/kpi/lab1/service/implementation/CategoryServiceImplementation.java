package org.kpi.lab1.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.dto.category.CategoryDto;
import org.kpi.lab1.repository.CategoryRepository;
import org.kpi.lab1.repository.entity.CategoryEntity;
import org.kpi.lab1.service.CategoryService;
import org.kpi.lab1.service.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryMapper.toCategories(categoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + id));
        return categoryMapper.toCategory(categoryEntity);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public Category addCategory(CategoryDto categoryDto) {
        try {
            return categoryMapper.toCategory(categoryRepository.save(categoryMapper.toCategoryEntity(categoryDto)));
        } catch (Exception e) {
            log.error("Exception occurred while saving customer details");
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
