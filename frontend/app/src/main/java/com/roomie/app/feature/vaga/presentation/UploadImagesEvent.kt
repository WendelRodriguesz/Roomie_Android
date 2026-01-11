package com.roomie.app.feature.vaga.presentation

import android.net.Uri

sealed interface UploadImagesEvent {
    data class ImageSelected(val uris: List<Uri>) : UploadImagesEvent
    data class ImageRemoved(val index: Int) : UploadImagesEvent
    data object Submit : UploadImagesEvent
    data object ClearError : UploadImagesEvent
    data object Skip : UploadImagesEvent
}