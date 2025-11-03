package org.kpi.lab1.featuretoggle;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.kpi.lab1.featuretoggle.annotation.DisabledFeatureToggle;
import org.kpi.lab1.featuretoggle.annotation.EnabledFeatureToggle;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class FeatureToggleExtension implements BeforeEachCallback, AfterEachCallback {

  @Override
  public void beforeEach(ExtensionContext context) {
    context
        .getTestMethod()
        .ifPresent(
            method -> {
              FeatureToggleService featureToggleService = getFeatureToggleService(context);

              if (method.isAnnotationPresent(EnabledFeatureToggle.class)) {
                EnabledFeatureToggle enabledFeatureToggle =
                    method.getAnnotation(EnabledFeatureToggle.class);
                featureToggleService.enableFeatureToggle(
                    enabledFeatureToggle.value().getFeatureName());
              } else if (method.isAnnotationPresent(DisabledFeatureToggle.class)) {
                DisabledFeatureToggle disabledFeatureToggle =
                    method.getAnnotation(DisabledFeatureToggle.class);
                featureToggleService.disableFeatureToggle(
                    disabledFeatureToggle.value().getFeatureName());
              }
            });
  }

  @Override
  public void afterEach(ExtensionContext context) {
    context
        .getTestMethod()
        .ifPresent(
            method -> {
              String featureName = null;
              if (method.isAnnotationPresent(EnabledFeatureToggle.class)) {
                EnabledFeatureToggle enabledFeatureToggle =
                    method.getAnnotation(EnabledFeatureToggle.class);
                featureName = enabledFeatureToggle.value().getFeatureName();
              } else if (method.isAnnotationPresent(DisabledFeatureToggle.class)) {
                DisabledFeatureToggle disabledFeatureToggle =
                    method.getAnnotation(DisabledFeatureToggle.class);
                featureName = disabledFeatureToggle.value().getFeatureName();
              }
              if (featureName != null) {
                FeatureToggleService featureToggleService = getFeatureToggleService(context);
                if (getFeatureNamePropertyAsBoolean(context, featureName)) {
                  featureToggleService.enableFeatureToggle(featureName);
                } else {
                  featureToggleService.disableFeatureToggle(featureName);
                }
              }
            });
  }

  private boolean getFeatureNamePropertyAsBoolean(ExtensionContext context, String featureName) {
    Environment environment = SpringExtension.getApplicationContext(context).getEnvironment();
    return environment.getProperty(
        "application.feature.toggles." + featureName, Boolean.class, Boolean.FALSE);
  }

  private FeatureToggleService getFeatureToggleService(ExtensionContext context) {
    return SpringExtension.getApplicationContext(context).getBean(FeatureToggleService.class);
  }
}
