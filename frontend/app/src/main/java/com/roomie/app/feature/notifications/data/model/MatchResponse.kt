package com.roomie.app.feature.notifications.data.model

import com.google.gson.annotations.SerializedName
import com.roomie.app.feature.match.data.model.PageableInfo

data class MatchResponse(
    @SerializedName("content") val content: List<Match>,
    @SerializedName("pageable") val pageable: PageableInfo,
    @SerializedName("last") val last: Boolean,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("totalElements") val totalElements: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("number") val number: Int,
    @SerializedName("first") val first: Boolean,
    @SerializedName("numberOfElements") val numberOfElements: Int,
    @SerializedName("empty") val empty: Boolean
)

data class Match(
    @SerializedName("id") val id: Long,
    @SerializedName("interessado") val interessado: UsuarioInteressadoMatch,
    @SerializedName("ofertante") val ofertante: UsuarioOfertanteMatch,
    @SerializedName("status") val status: String
)

data class UsuarioInteressadoMatch(
    @SerializedName("id") val id: Long,
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("data_de_nascimento") val dataDeNascimento: String,
    @SerializedName("idade") val idade: Int,
    @SerializedName("cidade") val cidade: String,
    @SerializedName("ocupacao") val ocupacao: String,
    @SerializedName("bio") val bio: String,
    @SerializedName("genero") val genero: String,
    @SerializedName("foto_de_perfil") val fotoDePerfil: String?,
    @SerializedName("interesses") val interesses: InteressesInteressado
)

data class InteressesInteressado(
    @SerializedName("id") val id: Long,
    @SerializedName("frequencia_festas") val frequenciaFestas: String,
    @SerializedName("habitos_limpeza") val habitosLimpeza: String,
    @SerializedName("aceita_pets") val aceitaPets: Boolean,
    @SerializedName("horario_sono") val horarioSono: String,
    @SerializedName("orcamento_min") val orcamentoMin: Double?,
    @SerializedName("orcamento_max") val orcamentoMax: Double?,
    @SerializedName("aceita_dividir_quarto") val aceitaDividirQuarto: Boolean,
    @SerializedName("fumante") val fumante: Boolean? = null,
    @SerializedName("consome_bebidas_alcoolicas") val consomeBebidasAlcoolicas: Boolean? = null
)

data class UsuarioOfertanteMatch(
    @SerializedName("id") val id: Long,
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("data_de_nascimento") val dataDeNascimento: String,
    @SerializedName("idade") val idade: Int,
    @SerializedName("cidade") val cidade: String,
    @SerializedName("ocupacao") val ocupacao: String,
    @SerializedName("bio") val bio: String,
    @SerializedName("genero") val genero: String,
    @SerializedName("foto_de_perfil") val fotoDePerfil: String?,
    @SerializedName("interesses") val interesses: InteressesOfertante?,
    @SerializedName("anuncio") val anuncio: AnuncioMatch?
)

data class InteressesOfertante(
    @SerializedName("id") val id: Long,
    @SerializedName("frequencia_festas") val frequenciaFestas: String,
    @SerializedName("habitos_limpeza") val habitosLimpeza: String,
    @SerializedName("aceita_pets") val aceitaPets: Boolean,
    @SerializedName("horario_sono") val horarioSono: String,
    @SerializedName("aceita_dividir_quarto") val aceitaDividirQuarto: Boolean
)

data class AnuncioMatch(
    @SerializedName("id") val id: Long,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("rua") val rua: String,
    @SerializedName("numero") val numero: String,
    @SerializedName("bairro") val bairro: String,
    @SerializedName("cidade") val cidade: String,
    @SerializedName("estado") val estado: String,
    @SerializedName("valor_aluguel") val valorAluguel: Double,
    @SerializedName("valor_contas") val valorContas: Double,
    @SerializedName("vagas_disponiveis") val vagasDisponiveis: Int,
    @SerializedName("tipo_imovel") val tipoImovel: String,
    @SerializedName("fotos") val fotos: List<String>,
    @SerializedName("comodos") val comodos: List<String>
)

