package org.kpi.lab1.config;

import org.kpi.lab1.repository.impl.NaturalIdRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = "org.kpi.lab1.repository",
        repositoryBaseClass = NaturalIdRepositoryImpl.class
)
public class JpaRepositoryConfiguration {}
