package org.kpi.lab1.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
public class OrderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
  @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", allocationSize = 1)
  Long id;

  @ManyToMany
  List<ProductItemEntity> items;

  @Column(nullable = false)
  Double total;
}
