package org.kpi.lab1.config;

import org.kpi.lab1.service.implementation.ProductServiceImplementation;
import org.kpi.lab1.service.mapper.ProductMapper;
import org.kpi.lab1.web.mapper.ProductDtoMapper;
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
}
