package com.roomie.app.core.model

enum class ProfileRole {
    SEEKER,   // INTERESSADO
    OFFEROR,  // OFERTANTE
}

fun profileRoleFromApi(raw: String?): ProfileRole? {
    if (raw == null) return null

    val value = raw.trim().uppercase()

    return when (value) {
        "INTERESSADO" -> ProfileRole.SEEKER
        "OFERTANTE" -> ProfileRole.OFFEROR
        else -> null
    }
}
