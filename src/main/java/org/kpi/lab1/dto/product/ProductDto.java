package org.kpi.lab1.dto.product;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.kpi.lab1.dto.category.CategoryDto;
import org.kpi.lab1.dto.validation.ExtendedValidation;
import org.kpi.lab1.dto.validation.ValidDescription;

@Value
@Builder(toBuilder = true)
@Jacksonized
@GroupSequence({ProductDto.class, ExtendedValidation.class})
public class ProductDto {

  @NotBlank(message = "Name is mandatory field")
  @Size(max = 90, message = "Name cannot exceed 90 characters")
  String name;

  @ValidDescription(groups = ExtendedValidation.class)
  String description;

  @NotNull(message = "Price cannot be null")
  Double price;

  @NotNull(message = "Rating cannot be null")
  Double rating;

  CategoryDto category;
}
