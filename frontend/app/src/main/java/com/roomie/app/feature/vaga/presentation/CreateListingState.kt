package com.roomie.app.feature.vaga.presentation

import android.net.Uri
import com.roomie.app.feature.vaga.model.ListingFormData

data class CreateListingState(
    val formData: ListingFormData = ListingFormData(),
    val selectedImages: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val validationError: String? = null
) {
    val isValid: Boolean
        get() = formData.titulo.isNotBlank() &&
                formData.descricao.isNotBlank() &&
                formData.rua.isNotBlank() &&
                formData.numero.isNotBlank() &&
                formData.bairro.isNotBlank() &&
                formData.cidade.isNotBlank() &&
                formData.estado.isNotBlank() &&
                formData.valorAluguel != null &&
                formData.valorContas != null &&
                formData.tipoImovel != null &&
                formData.comodos.isNotEmpty()
}

