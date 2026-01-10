package com.project.roomie.adapters.in;

import com.project.roomie.dto.response.MensagemResponseDTO;
import com.project.roomie.mapper.MensagemMapper;
import com.project.roomie.ports.in.MensagemPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mensagem")
public class MensagemAdapterIn {

    private final MensagemPortIn mensagemPortIn;
    private final MensagemMapper mensagemMapper;

    @Autowired
    public MensagemAdapterIn(MensagemPortIn mensagemPortIn,
                         MensagemMapper mensagemMapper) {
        this.mensagemPortIn = mensagemPortIn;
        this.mensagemMapper = mensagemMapper;
    }

    @GetMapping("/visualizarMensagens/{id_chat}")
    public List<MensagemResponseDTO> visualizarMensagens(@PathVariable Integer id_chat){
        return mensagemPortIn.visualizarMensagens(id_chat);
    }
}