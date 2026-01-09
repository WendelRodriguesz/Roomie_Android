package com.roomie.app.feature.offeror_home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roomie.app.feature.offeror_home.data.AnuncioRepository

class OfferorHomeViewModelFactory(
    private val repository: AnuncioRepository,
    private val anuncioId: Long,
    private val token: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OfferorHomeViewModel(repository, anuncioId, token) as T
    }
}
