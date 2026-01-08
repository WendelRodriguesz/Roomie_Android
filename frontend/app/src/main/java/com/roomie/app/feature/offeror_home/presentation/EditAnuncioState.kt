package com.roomie.app.feature.offeror_home.presentation

import com.roomie.app.feature.offeror_home.model.Anuncio

data class EditAnuncioState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val anuncio: Anuncio? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
