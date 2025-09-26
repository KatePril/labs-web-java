package org.kpi.lab1.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class CategoryListDto {

    @NotNull(message = "Categories list cannot be null")
    List<CategoryDto> categories;
}
