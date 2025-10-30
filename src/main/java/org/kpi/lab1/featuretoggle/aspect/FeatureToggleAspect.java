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

    @Around(value = "@annotation(featureToggle)")
    public Object checkFeatureToggle(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        return checkToggle(joinPoint, featureToggle);
    }

    private Object checkToggle(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        FeatureToggles toggle = featureToggle.value();
        if (featureToggleService.checkFeatureToggle(toggle.getFeatureName())) {
            return joinPoint.proceed();
        }
        log.warn("Feature toggle {} is not enabled!", toggle.getFeatureName());
        throw new DisabledFeatureToggleException(toggle.getFeatureName());
    }
}
