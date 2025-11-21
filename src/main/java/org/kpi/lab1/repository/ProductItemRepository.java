package org.kpi.lab1.repository;

import org.kpi.lab1.repository.entity.ProductItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends CrudRepository<ProductItemEntity, Long> {
}
