package org.kpi.lab1.service.implementation;

import java.util.concurrent.ThreadLocalRandom;
import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.extern.slf4j.Slf4j;
import org.kpi.lab1.service.RateService;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class RateServiceImplementation implements RateService {

    @Override
    public double getProductById(Long id) {
        double randomValue = ThreadLocalRandom.current().nextDouble(1.0, 5.0);
        return BigDecimal.valueOf(randomValue)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
