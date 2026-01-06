package com.project.roomie.dto.update;

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
public class InteressesOfertantesUpdateDTO {
    private FrequenciaFestas frequencia_festas;
    private HabitosLimpeza habitos_limpeza;
    private boolean aceita_pets;
    private HorarioSono horario_sono;
    private boolean aceita_dividir_quarto;
}
