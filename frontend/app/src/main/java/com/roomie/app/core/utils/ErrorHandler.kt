package com.roomie.app.core.utils

import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorHandler {
    fun processHttpError(response: Response<*>, defaultMessage: String = "Ocorreu um erro. Tente novamente."): String {
        val errorBody = response.errorBody()?.string()
        val httpCode = response.code()
        
        return when (httpCode) {
            400 -> errorBody?.takeIf { it.isNotBlank() } 
                ?: "Dados inválidos. Verifique se todos os campos estão preenchidos corretamente."
            401 -> "Email ou senha incorretos. Verifique suas credenciais e tente novamente."
            403 -> "Acesso negado. Você não tem permissão para realizar esta ação."
            404 -> "Recurso não encontrado. Verifique se a informação está correta."
            409 -> errorBody?.takeIf { it.isNotBlank() } 
                ?: "Conflito: Este registro já existe. Tente fazer login ou use outro email."
            422 -> errorBody?.takeIf { it.isNotBlank() }
                ?: "Dados inválidos. Verifique os campos preenchidos."
            500 -> "Erro interno do servidor. Tente novamente mais tarde."
            502, 503, 504 -> "Serviço temporariamente indisponível. Tente novamente em alguns instantes."
            else -> errorBody?.takeIf { it.isNotBlank() } 
                ?: defaultMessage
        }
    }
    
    fun processNetworkException(exception: Exception): String {
        return when (exception) {
            is ConnectException -> "Não foi possível conectar ao servidor. Verifique sua conexão com a internet."
            is SocketTimeoutException -> "Tempo de conexão esgotado. Verifique sua internet e tente novamente."
            is UnknownHostException -> "Não foi possível conectar ao servidor. Verifique sua conexão."
            else -> exception.message ?: "Ocorreu um erro inesperado. Tente novamente."
        }
    }
}

