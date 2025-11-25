package org.kpi.lab1.repository;

import org.kpi.lab1.repository.entity.ProductEntity;
import org.kpi.lab1.repository.projection.ProductDetailsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
       select new org.kpi.lab1.repository.projection.ProductDetailsProjection(
           p.name,
           p.price,
           p.description
       )
       from ProductEntity p
       where p.category.name = :category
       order by p.name
       """)
  Page<ProductDetailsProjection> findProductByCategory(String category, Pageable pageable);
}
