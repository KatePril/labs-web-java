package org.kpi.lab1.web;

import jakarta.validation.Valid;
import java.util.List;
import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.dto.category.CategoryDto;
import org.kpi.lab1.service.CategoryService;
import org.kpi.lab1.web.mapper.CategoryDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
  private final CategoryDtoMapper categoryDtoMapper;
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService, CategoryDtoMapper categoryDtoMapper) {
    this.categoryService = categoryService;
    this.categoryDtoMapper = categoryDtoMapper;
  }

  @PostMapping
  public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto category) {
    Category created = categoryService.addCategory(category);
    return new ResponseEntity<>(categoryDtoMapper.toCategoryDto(created), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<CategoryDto>> getCategories() {
    List<CategoryDto> dtos =
        categoryService.getAllCategories().stream().map(categoryDtoMapper::toCategoryDto).toList();
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
    Category category = categoryService.getCategoryById(id);
    if (category == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(categoryDtoMapper.toCategoryDto(category));
  }

  @GetMapping("/{name}")
  public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable String name) {
    Category category = categoryService.findByNaturalId(name);
    if (category == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(categoryDtoMapper.toCategoryDto(category));
  }
}
