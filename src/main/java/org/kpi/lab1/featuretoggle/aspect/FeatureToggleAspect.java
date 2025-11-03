package org.kpi.lab1.featuretoggle.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.kpi.lab1.featuretoggle.FeatureToggleService;
import org.kpi.lab1.featuretoggle.FeatureToggles;
import org.kpi.lab1.featuretoggle.annotation.FeatureToggle;
import org.kpi.lab1.featuretoggle.exception.DisabledFeatureToggleException;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {
  private final FeatureToggleService featureToggleService;

  @Before("@annotation(featureToggle)")
  public void checkFeatureToggle(JoinPoint joinPoint, FeatureToggle featureToggle) {
    FeatureToggles toggle = featureToggle.value();
    if (!featureToggleService.checkFeatureToggle(toggle.getFeatureName())) {
      log.warn("Feature toggle {} is disabled", toggle.getFeatureName());
      throw new DisabledFeatureToggleException(toggle.getFeatureName());
    }
    log.debug("Feature toggle {} is enabled", toggle.getFeatureName());
  }
}
