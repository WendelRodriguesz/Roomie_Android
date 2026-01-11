package com.roomie.app.feature.vaga.presentation

import android.net.Uri

data class UploadImagesState(
    val anuncioId: Long? = null,
    val userId: Long? = null,
    val selectedImages: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isCompleted: Boolean = false,
    val isSkipped: Boolean = false
)