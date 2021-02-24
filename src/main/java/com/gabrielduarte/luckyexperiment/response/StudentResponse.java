package com.gabrielduarte.luckyexperiment.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class StudentResponse {

    private Long id;

    private Integer position;

    private BigDecimal luckyPerformance;

    private BigDecimal performance;

    private BigDecimal diffBetweenPerformance;

    private BigDecimal totalLucky;

}
