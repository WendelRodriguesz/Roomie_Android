package com.roomie.app.feature.match.model

enum class ListingStatus { ACTIVE, INACTIVE }

data class ListingCard(
    val id: String,
    val offerorUserId: String,
    val title: String,
    val description: String,
    val neighborhood: String,
    val city: String,
    val totalRent: Double,
    val mediaAccounts: Double,
    val numResidents: Int,
    val vacanciesDisp: Int,
    val bedrooms: Int = 0,
    val bathrooms: Int = 0,
    val areaInSquareMeters: Int? = null,
    val acceptPets: Boolean,
    val rules: String,
    val status: ListingStatus,
    val createdInMillis: Long,
    val rating: Double = 0.0,
    val tags: List<String> = emptyList(),
    @androidx.annotation.DrawableRes val localPhoto: Int? = null,
    val photos: List<String> = emptyList()
)
