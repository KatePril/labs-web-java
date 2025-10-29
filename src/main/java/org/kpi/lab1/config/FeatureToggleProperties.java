package org.kpi.lab1.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application.feature")
public class FeatureToggleProperties {
    Map<String, Boolean> featuresMap;

    public boolean isEnabled(String feature) {
        return  featuresMap.getOrDefault(feature, false);
    }
}
