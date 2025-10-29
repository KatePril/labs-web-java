package org.kpi.lab1.featuretoggle;

import org.kpi.lab1.config.FeatureToggleProperties;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class FeatureToggleService {

    private final ConcurrentHashMap<String, Boolean> featureToggleMap;

    public FeatureToggleService(FeatureToggleProperties featureToggleProperties) {
        featureToggleMap = new ConcurrentHashMap<>();
    }

    public boolean checkFeatureToggle(String featureName) {
        return featureToggleMap.getOrDefault(featureName, false);
    }

    public void enableFeatureToggle(String featureName) {
        featureToggleMap.put(featureName, true);
    }

    public void disableFeatureToggle(String featureName) {
        featureToggleMap.put(featureName, false);
    }
}
