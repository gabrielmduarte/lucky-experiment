package com.gabrielduarte.luckyexperiment.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer position;

    private BigDecimal performance;

    private BigDecimal familyPressure;

    private BigDecimal selfPressure;

    private BigDecimal goodSleep;

    private BigDecimal healthy;

    private BigDecimal guessedAnswers;

}
