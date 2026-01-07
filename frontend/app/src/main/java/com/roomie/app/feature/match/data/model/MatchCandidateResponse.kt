package com.roomie.app.feature.match.data.model

import com.google.gson.annotations.SerializedName

data class MatchCandidateResponse(
    @SerializedName("content") val content: List<MatchCandidate>,
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

data class MatchCandidate(
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
    @SerializedName("interesses") val interesses: Interesses,
    @SerializedName("anuncio") val anuncio: Anuncio
)

data class Interesses(
    @SerializedName("id") val id: Long,
    @SerializedName("frequencia_festas") val frequenciaFestas: String,
    @SerializedName("habitos_limpeza") val habitosLimpeza: String,
    @SerializedName("aceita_pets") val aceitaPets: Boolean,
    @SerializedName("horario_sono") val horarioSono: String,
    @SerializedName("aceita_dividir_quarto") val aceitaDividirQuarto: Boolean
)

data class Anuncio(
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

data class PageableInfo(
    @SerializedName("pageNumber") val pageNumber: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("sort") val sort: SortInfo,
    @SerializedName("offset") val offset: Int,
    @SerializedName("paged") val paged: Boolean,
    @SerializedName("unpaged") val unpaged: Boolean
)

data class SortInfo(
    @SerializedName("empty") val empty: Boolean,
    @SerializedName("sorted") val sorted: Boolean,
    @SerializedName("unsorted") val unsorted: Boolean
)

