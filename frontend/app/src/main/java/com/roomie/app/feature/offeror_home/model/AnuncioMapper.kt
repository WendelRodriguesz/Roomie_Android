package com.roomie.app.feature.offeror_home.model

import com.roomie.app.feature.offeror_home.data.remote.dto.AnuncioResponseDto

fun AnuncioResponseDto.toAnuncio(): Anuncio {
    val status = when (status.uppercase()) {
        "ATIVO" -> StatusAnuncio.ATIVO
        "PAUSADO" -> StatusAnuncio.PAUSADO
        else -> StatusAnuncio.INATIVO
    }
    
    return Anuncio(
        id = id,
        titulo = titulo,
        descricao = descricao,
        rua = rua,
        numero = numero,
        bairro = bairro,
        cidade = cidade,
        estado = estado,
        valorAluguel = valor_aluguel,
        valorContas = valor_contas,
        vagasDisponiveis = vagas_disponiveis,
        tipoImovel = tipo_imovel,
        fotos = fotos ?: emptyList(),
        comodos = comodos ?: emptyList(),
        status = status
    )
}

fun Anuncio.toAtualizarRequest(): com.roomie.app.feature.offeror_home.data.remote.dto.AtualizarAnuncioRequest {
    return com.roomie.app.feature.offeror_home.data.remote.dto.AtualizarAnuncioRequest(
        titulo = titulo,
        descricao = descricao,
        rua = rua,
        numero = numero,
        bairro = bairro,
        cidade = cidade,
        estado = estado,
        valorAluguel = valorAluguel.toFloat(),
        valorContas = valorContas.toFloat(),
        vagasDisponiveis = vagasDisponiveis,
        tipo_imovel = tipoImovel,
        comodos = comodos
    )
}
