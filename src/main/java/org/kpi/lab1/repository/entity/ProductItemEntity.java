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
@Table(name = "product_item")
public class ProductItemEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_item_id_seq")
  @SequenceGenerator(name = "product_item_id_seq", sequenceName = "product_item_id_seq")
  Long id;

  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
  ProductEntity product;

  @Column(nullable = false)
  int quantity;
}
