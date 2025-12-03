package com.project.roomie.dto.response;

import com.project.roomie.core.model.enums.Frequencia_festas;
import com.project.roomie.core.model.enums.Habitos_limpeza;
import com.project.roomie.core.model.enums.Horario_sono;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InteressesResponseDTO {
    private Integer id;
    private Integer user_id;
    private Frequencia_festas frequencia_festas;
    private Habitos_limpeza habitos_limpeza;
    private boolean aceita_pets;
    private Horario_sono horario_sono;
    private float orcamento_min;
    private float orcamento_max;
    private boolean aceita_dividir_quarto;
}
