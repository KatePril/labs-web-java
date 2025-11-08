package org.kpi.lab1.featuretoggle.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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

  @Around("@annotation(featureToggle)")
  public Object aroundFeatureToggle(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
    FeatureToggles toggle = featureToggle.value();
    String featureName = toggle.getFeatureName();

    if (!featureToggleService.checkFeatureToggle(featureName)) {
      log.warn("Feature toggle {} is disabled", featureName);
      throw new DisabledFeatureToggleException(featureName);
    }

    return joinPoint.proceed();
  }
}
