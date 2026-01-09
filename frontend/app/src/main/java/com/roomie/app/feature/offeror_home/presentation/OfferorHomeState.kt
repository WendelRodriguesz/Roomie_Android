package com.roomie.app.feature.offeror_home.presentation

import com.roomie.app.feature.offeror_home.model.Anuncio

data class OfferorHomeState(
    val isLoading: Boolean = false,
    val anuncio: Anuncio? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
