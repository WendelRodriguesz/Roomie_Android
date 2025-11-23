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
    val acceptPets: Boolean,
    val rules: String,
    val status: ListingStatus,
    val createdInMillis: Long,
    val tags: List<String> = emptyList(),
    val photos: List<String> = emptyList()
)
