package org.kpi.lab1.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class CategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
  @SequenceGenerator(name = "category_id_seq", sequenceName = "category_id_seq", allocationSize = 50)
  Long id;

  @NaturalId
  @NonNull
  String name;

  String description;
}
