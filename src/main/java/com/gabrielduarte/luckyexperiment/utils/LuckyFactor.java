package com.gabrielduarte.luckyexperiment.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Component
public class LuckyFactor {

    public BigDecimal luckOrSetback() {
        Random random = new Random();
        double randomDouble = (random.nextDouble() * 2) - 1;
        return BigDecimal.valueOf(randomDouble);
    };

    public BigDecimal createPerformance() {
        double start = 500;
        double end = 620;
        double random = new Random().nextDouble();
        double result = start + (random * (end - start));

        return new BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN);
    }
}
