package com.project.roomie.dto.response;

import com.project.roomie.core.model.enums.FrequenciaFestas;
import com.project.roomie.core.model.enums.HabitosLimpeza;
import com.project.roomie.core.model.enums.HorarioSono;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InteressesInteressadosResponseDTO {
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
}
