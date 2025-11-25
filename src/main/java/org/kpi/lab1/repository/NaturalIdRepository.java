package org.kpi.lab1.repository;

import java.io.Serializable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NaturalIdRepository<T, NID extends Serializable> {
  Optional<T> findByNaturalId(NID naturalId);

  void deleteByNaturalId(NID naturalId);
}
