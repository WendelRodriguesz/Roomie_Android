package com.project.roomie.core.model;

import com.project.roomie.core.model.enums.Frequencia_festas;
import com.project.roomie.core.model.enums.Habitos_limpeza;
import com.project.roomie.core.model.enums.Horario_sono;

public class InteressesInteressados {

    private Integer id;
    private Frequencia_festas frequencia_festas;
    private Habitos_limpeza habitos_limpeza;
    private boolean aceita_pets;
    private Horario_sono horario_sono;
    private float orcamento_min;
    private float orcamento_max;
    private boolean aceita_dividir_quarto;


    public InteressesInteressados() {
    }

    public InteressesInteressados(Integer id,
                                  Frequencia_festas frequencia_festas,
                                  Habitos_limpeza habitos_limpeza,
                                  boolean aceita_pets,
                                  Horario_sono horario_sono,
                                  float orcamento_min,
                                  float orcamento_max,
                                  boolean aceita_dividir_quarto) {
        this.id = id;
        this.frequencia_festas = frequencia_festas;
        this.habitos_limpeza = habitos_limpeza;
        this.aceita_pets = aceita_pets;
        this.horario_sono = horario_sono;
        this.orcamento_min = orcamento_min;
        this.orcamento_max = orcamento_max;
        this.aceita_dividir_quarto = aceita_dividir_quarto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Frequencia_festas getFrequencia_festas() {
        return frequencia_festas;
    }

    public void setFrequencia_festas(Frequencia_festas frequencia_festas) {
        this.frequencia_festas = frequencia_festas;
    }

    public Habitos_limpeza getHabitos_limpeza() {
        return habitos_limpeza;
    }

    public void setHabitos_limpeza(Habitos_limpeza habitos_limpeza) {
        this.habitos_limpeza = habitos_limpeza;
    }

    public boolean isAceita_pets() {
        return aceita_pets;
    }

    public void setAceita_pets(boolean aceita_pets) {
        this.aceita_pets = aceita_pets;
    }

    public Horario_sono getHorario_sono() {
        return horario_sono;
    }

    public void setHorario_sono(Horario_sono horario_sono) {
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
}