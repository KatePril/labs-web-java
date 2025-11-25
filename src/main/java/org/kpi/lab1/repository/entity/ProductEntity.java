package org.kpi.lab1.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
  @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 50)
  Long id;

  @Column(nullable = false, unique = true)
  String name;

  String description;

  @Column(nullable = false)
  Double price;

  Double rating;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  CategoryEntity category;
}
