package com.project.roomie.core.model;

import com.project.roomie.core.model.enums.FrequenciaFestas;
import com.project.roomie.core.model.enums.HabitosLimpeza;
import com.project.roomie.core.model.enums.HorarioSono;

public class InteressesInteressados {

    private Integer id;
    private FrequenciaFestas frequencia_festas;
    private HabitosLimpeza habitos_limpeza;
    private boolean aceita_pets;
    private HorarioSono horario_sono;
    private float orcamento_min;
    private float orcamento_max;
    private boolean aceita_dividir_quarto;
    private boolean fumante;
    private boolean consome_bebidas_alcoolicas;


    public InteressesInteressados() {
    }

    public InteressesInteressados(Integer id,
                                  FrequenciaFestas frequencia_festas,
                                  HabitosLimpeza habitos_limpeza,
                                  boolean aceita_pets,
                                  HorarioSono horario_sono,
                                  float orcamento_min,
                                  float orcamento_max,
                                  boolean aceita_dividir_quarto,
                                  boolean fumante,
                                  boolean consome_bebidas_alcoolicas) {
        this.id = id;
        this.frequencia_festas = frequencia_festas;
        this.habitos_limpeza = habitos_limpeza;
        this.aceita_pets = aceita_pets;
        this.horario_sono = horario_sono;
        this.orcamento_min = orcamento_min;
        this.orcamento_max = orcamento_max;
        this.aceita_dividir_quarto = aceita_dividir_quarto;
        this.fumante = fumante;
        this.consome_bebidas_alcoolicas = consome_bebidas_alcoolicas;
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

    public float getOrcamento_min() {
        return orcamento_min;
    }

    public void setOrcamento_min(float orcamento_min) {
        this.orcamento_min = orcamento_min;
    }

    public float getOrcamento_max() {
        return orcamento_max;
    }

    public void setOrcamento_max(float orcamento_max) {
        this.orcamento_max = orcamento_max;
    }

    public boolean isAceita_dividir_quarto() {
        return aceita_dividir_quarto;
    }

    public void setAceita_dividir_quarto(boolean aceita_dividir_quarto) {
        this.aceita_dividir_quarto = aceita_dividir_quarto;
    }
    public boolean getFumante() {
        return fumante;
    }

    public void setFumante(boolean fumante) {
        this.fumante = fumante;
    }

    public boolean getConsome_bebidas_alcoolicas() {
        return consome_bebidas_alcoolicas;
    }

    public void setConsome_bebidas_alcoolicas(boolean consome_bebidas_alcoolicas) {
        this.consome_bebidas_alcoolicas = consome_bebidas_alcoolicas;
    }
}