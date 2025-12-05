package org.kpi.lab1.config;

import org.kpi.lab1.service.mapper.CategoryMapper;
import org.kpi.lab1.service.mapper.OrderMapper;
import org.kpi.lab1.service.mapper.ProductItemMapper;
import org.kpi.lab1.service.mapper.ProductMapper;
import org.kpi.lab1.web.mapper.CategoryDtoMapper;
import org.kpi.lab1.web.mapper.OrderDtoMapper;
import org.kpi.lab1.web.mapper.ProductDtoMapper;
import org.kpi.lab1.web.mapper.ProductItemDtoMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MappersTestConfiguration {

  @Bean
  public ProductMapper productMapper() {
    return Mappers.getMapper(ProductMapper.class);
  }

  @Bean
  public ProductDtoMapper productDtoMapper() {
    return Mappers.getMapper(ProductDtoMapper.class);
  }

  @Bean
  public CategoryMapper categoryMapper() {
    return Mappers.getMapper(CategoryMapper.class);
  }

  @Bean
  public CategoryDtoMapper categoryDtoMapper() {
    return Mappers.getMapper(CategoryDtoMapper.class);
  }

  @Bean
  public ProductItemMapper productItemMapper() {
    return Mappers.getMapper(ProductItemMapper.class);
  }

  @Bean
  public ProductItemDtoMapper productItemDtoMapper() {
    return Mappers.getMapper(ProductItemDtoMapper.class);
  }

  @Bean
  public OrderMapper orderMapper() {
    return Mappers.getMapper(OrderMapper.class);
  }

  @Bean
  public OrderDtoMapper orderDtoMapper() {
    return Mappers.getMapper(OrderDtoMapper.class);
  }
}
