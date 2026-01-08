package com.project.roomie.core.model;

import com.project.roomie.core.model.enums.FrequenciaFestas;
import com.project.roomie.core.model.enums.HabitosLimpeza;
import com.project.roomie.core.model.enums.HorarioSono;

public class InteressesOfertantes {
    private Integer id;
    private FrequenciaFestas frequencia_festas;
    private HabitosLimpeza habitos_limpeza;
    private boolean aceita_pets;
    private HorarioSono horario_sono;
    private boolean aceita_dividir_quarto;


    public InteressesOfertantes() {
    }

    public InteressesOfertantes(Integer id,
                                  FrequenciaFestas frequencia_festas,
                                  HabitosLimpeza habitos_limpeza,
                                  boolean aceita_pets,
                                  HorarioSono horario_sono,
                                  boolean aceita_dividir_quarto) {
        this.id = id;
        this.frequencia_festas = frequencia_festas;
        this.habitos_limpeza = habitos_limpeza;
        this.aceita_pets = aceita_pets;
        this.horario_sono = horario_sono;
        this.aceita_dividir_quarto = aceita_dividir_quarto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FrequenciaFestas getFrequencia_festas() {
        return frequencia_festas;
    }

    public void setFrequencia_festas(FrequenciaFestas frequencia_festas) {
        this.frequencia_festas = frequencia_festas;
    }

    public HabitosLimpeza getHabitos_limpeza() {
        return habitos_limpeza;
    }

    public void setHabitos_limpeza(HabitosLimpeza habitos_limpeza) {
        this.habitos_limpeza = habitos_limpeza;
    }

    public boolean isAceita_pets() {
        return aceita_pets;
    }

    public void setAceita_pets(boolean aceita_pets) {
        this.aceita_pets = aceita_pets;
    }

    public HorarioSono getHorario_sono() {
        return horario_sono;
    }

    public void setHorario_sono(HorarioSono horario_sono) {
        this.horario_sono = horario_sono;
    }

    public boolean isAceita_dividir_quarto() {
        return aceita_dividir_quarto;
    }

    public void setAceita_dividir_quarto(boolean aceita_dividir_quarto) {
        this.aceita_dividir_quarto = aceita_dividir_quarto;
    }
}
