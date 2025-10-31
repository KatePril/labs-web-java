package org.kpi.lab1.web;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpi.lab1.AbstractIt;
import org.kpi.lab1.featuretoggle.FeatureToggleExtension;
import org.kpi.lab1.featuretoggle.FeatureToggles;
import org.kpi.lab1.featuretoggle.annotation.DisabledFeatureToggle;
import org.kpi.lab1.featuretoggle.annotation.EnabledFeatureToggle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@DisplayName("Cosmo Cat Controller IT")
@ExtendWith(FeatureToggleExtension.class)
public class CosmoCatControllerIT extends AbstractIt {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @DisabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void testDisabledFeatureToggle() {
        mockMvc.perform(get("/api/v1/products")).andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void testEnabledFeatureToggle() {
        mockMvc.perform(get("/api/v1/products")).andExpect(status().isOk());
    }
}
