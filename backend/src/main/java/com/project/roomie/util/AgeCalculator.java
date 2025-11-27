package com.project.roomie.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class AgeCalculator {

    public Integer calcularIdade(LocalDate dataDeNascimento) {
        if (dataDeNascimento == null) {
            return null;
        }

        return Period.between(dataDeNascimento, LocalDate.now()).getYears();
    }
}