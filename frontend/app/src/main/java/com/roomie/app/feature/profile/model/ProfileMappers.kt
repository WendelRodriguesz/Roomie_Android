package com.roomie.app.feature.profile.model

import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.preference_registration.model.UserPreferences
import com.roomie.app.feature.profile.data.remote.dto.AtualizarUsuarioBasicoRequest
import com.roomie.app.feature.profile.data.remote.dto.InteressesInteressadoDto
import com.roomie.app.feature.profile.data.remote.dto.InteressesOfertanteDto
import com.roomie.app.feature.profile.data.remote.dto.UsuarioInteressadoDto
import com.roomie.app.feature.profile.data.remote.dto.UsuarioOfertanteDto

private fun PartyFrequency?.toPartyFrequency(): PartyFrequency {
    if (this != null) return this
    return PartyFrequency.AS_VEZES
}

private fun CleaningHabit?.toCleaningHabit(): CleaningHabit {
    if (this != null) return this
    return CleaningHabit.OCASIONAL
}

private fun SleepRoutine?.toSleepRoutine(): SleepRoutine {
    if (this != null) return this
    return SleepRoutine.FLEXIVEL
}

private fun Any?.toPartyFrequency(): PartyFrequency {
    return when (this) {
        is PartyFrequency -> this
        is String -> when (this.uppercase()) {
            "NUNCA" -> PartyFrequency.NUNCA
            "AS_VEZES", "AS VEZES" -> PartyFrequency.AS_VEZES
            "FREQUENTE", "FREQUENTEMENTE" -> PartyFrequency.FREQUENTE
            else -> PartyFrequency.AS_VEZES
        }
        else -> PartyFrequency.AS_VEZES
    }
}

private fun Any?.toCleaningHabit(): CleaningHabit {
    return when (this) {
        is CleaningHabit -> this
        is String -> when (this.uppercase()) {
            "DIARIO", "DIÁRIO" -> CleaningHabit.DIARIO
            "SEMANAL" -> CleaningHabit.SEMANAL
            "QUINZENAL" -> CleaningHabit.QUINZENAL
            else -> CleaningHabit.OCASIONAL
        }
        else -> CleaningHabit.OCASIONAL
    }
}

private fun Any?.toSleepRoutine(): SleepRoutine {
    return when (this) {
        is SleepRoutine -> this
        is String -> when (this.uppercase()) {
            "MANHA", "MANHÃ", "MATUTINO" -> SleepRoutine.MATUTINO
            "NOITE", "NOTURNO" -> SleepRoutine.NOTURNO
            "VESPERTINO", "TARDE" -> SleepRoutine.VESPERTINO
            else -> SleepRoutine.FLEXIVEL
        }
        else -> SleepRoutine.FLEXIVEL
    }
}

private fun Any?.toGenderOption(): GenderOption? {
    return when (this) {
        is GenderOption -> this
        is String -> when (this.uppercase()) {
            "MASCULINO" -> GenderOption.MASCULINO
            "FEMININO" -> GenderOption.FEMININO
            "OUTROS" -> GenderOption.PREFIRO_NAO_INFORMAR
            "NAO_BINARIO", "NÃO_BINÁRIO", "NÃO BINÁRIO" -> GenderOption.PREFIRO_NAO_INFORMAR
            "PREFIRO_NAO_DIZER", "PREFIRO NÃO DIZER",
            "PREFIRO_NAO_INFORMAR", "PREFIRO NÃO INFORMAR" -> GenderOption.PREFIRO_NAO_INFORMAR
            else -> null
        }
        else -> null
    }
}

/**
 * INTERESSADO
 */
fun UsuarioInteressadoDto.toUserProfile(): UserProfile {
    val partyFrequency = interesses?.frequencia_festas.toPartyFrequency()
    val cleaningHabit = interesses?.habitos_limpeza.toCleaningHabit()
    val sleepRoutine = interesses?.horario_sono.toSleepRoutine()
    val genderOption = genero.toGenderOption()

    val acceptsPets = interesses?.aceita_pets == true
    val acceptsSharedRoom = interesses?.aceita_dividir_quarto == true
    val isSmoker = interesses?.fumante == true
    val isDrinker = interesses?.consome_bebidas_alcoolicas == true

    val tags = buildList {
        genderOption?.let { add(it.label) }
        if (acceptsPets) add("Aceita pets")
        if (acceptsSharedRoom) add("Aceita dividir quarto") else add("Prefere quarto individual")

        when (partyFrequency) {
            PartyFrequency.NUNCA -> add("Não gosta de festas")
            PartyFrequency.AS_VEZES -> add("Festas às vezes")
            PartyFrequency.FREQUENTE -> add("Gosta de festas")
        }

        ocupacao?.let { add(it) }
    }

    val lifestyle = LifestylePreferences(
        acceptsPets = acceptsPets,
        isSmoker = isSmoker,
        isDrinker = isDrinker,
        partyFrequency = partyFrequency,
        isQuiet = partyFrequency == PartyFrequency.NUNCA,
        sleepRoutine = sleepRoutine,
        cleaningHabit = cleaningHabit,
        acceptsSharedRoom = acceptsSharedRoom,
        tags = tags,
    )

    val budget = Budget(
        minBudget = interesses?.orcamento_min?.toInt(),
        maxBudget = interesses?.orcamento_max?.toInt(),
    )

    return UserProfile(
        id = id,
        name = nome,
        email = email,
        age = idade,
        role = ProfileRole.SEEKER,
        city = cidade,
        professionOrCourse = ocupacao,
        bio = bio.orEmpty(),
        gender = genderOption,
        budget = budget,
        lifestyle = lifestyle,
        settings = AccountSettings(
            notificationsEnabled = true,
            showAsOnline = true,
            discoveryEnabled = true,
            darkModeEnabled = true,
        ),
        localPhoto = null,
        photoUrl = foto_de_perfil,
    )
}

/**
 * OFERTANTE (placeholder: orçamento não existe no back pro ofertante, então fica null/null)
 */
fun UsuarioOfertanteDto.toUserProfile(): UserProfile {
    val genderOption = genero.toGenderOption()

    val partyFrequency = interesses?.frequencia_festas.toPartyFrequency()
    val cleaningHabit = interesses?.habitos_limpeza.toCleaningHabit()
    val sleepRoutine = interesses?.horario_sono.toSleepRoutine()

    val acceptsPets = run {
        val value = interesses?.aceita_pets
        value == true
    }

    val acceptsSharedRoom = run {
        val value = interesses?.aceita_dividir_quarto
        value == true
    }

    val tags = buildList {
        genderOption?.let { add(it.label) }
        if (acceptsPets) add("Aceita pets")
        if (acceptsSharedRoom) add("Aceita dividir quarto") else add("Prefere quarto individual")
        ocupacao?.let { add(it) }
    }

    val lifestyle = LifestylePreferences(
        acceptsPets = acceptsPets,
        isSmoker = false,
        isDrinker = false,
        partyFrequency = partyFrequency,
        isQuiet = partyFrequency == PartyFrequency.NUNCA,
        sleepRoutine = sleepRoutine,
        cleaningHabit = cleaningHabit,
        acceptsSharedRoom = acceptsSharedRoom,
        tags = tags,
    )

    return UserProfile(
        id = id,
        name = nome,
        email = email,
        age = idade,
        role = ProfileRole.OFFEROR,
        city = cidade,
        professionOrCourse = ocupacao,
        bio = bio.orEmpty(),
        gender = genderOption,
        budget = Budget(minBudget = null, maxBudget = null),
        lifestyle = lifestyle,
        settings = AccountSettings(
            notificationsEnabled = true,
            showAsOnline = true,
            discoveryEnabled = true,
            darkModeEnabled = true,
        ),
        localPhoto = null,
        photoUrl = foto_de_perfil,
    )
}

fun UserProfile.toBasicUpdateRequest(): AtualizarUsuarioBasicoRequest {
    val generoValue = run {
        if (gender == null) null else gender.apiValue
    }

    return AtualizarUsuarioBasicoRequest(
        nome = name,
        email = email,
        cidade = city,
        ocupacao = professionOrCourse,
        bio = bio,
        genero = generoValue,
    )
}

fun UserProfile.toUserPreferences(drinksAlcohol: Boolean = false): UserPreferences {
    return UserPreferences(
        partyFrequency = lifestyle.partyFrequency,
        cleaningHabit = lifestyle.cleaningHabit,
        acceptsPets = lifestyle.acceptsPets,
        sleepRoutine = lifestyle.sleepRoutine,
        acceptsRoomSharing = lifestyle.acceptsSharedRoom,
        budget = budget,
        isSmoker = lifestyle.isSmoker,
        drinksAlcohol = drinksAlcohol,
    )
}

fun InteressesInteressadoDto?.toUserPreferences(): UserPreferences? {
    if (this == null) return null
    
    return UserPreferences(
        partyFrequency = frequencia_festas.toPartyFrequency(),
        cleaningHabit = habitos_limpeza.toCleaningHabit(),
        acceptsPets = aceita_pets ?: false,
        sleepRoutine = horario_sono.toSleepRoutine(),
        acceptsRoomSharing = aceita_dividir_quarto ?: false,
        budget = Budget(
            minBudget = orcamento_min?.toInt(),
            maxBudget = orcamento_max?.toInt()
        ),
        isSmoker = fumante ?: false,
        drinksAlcohol = consome_bebidas_alcoolicas ?: false
    )
}

fun InteressesOfertanteDto?.toUserPreferences(): UserPreferences? {
    if (this == null) return null
    
    return UserPreferences(
        partyFrequency = frequencia_festas.toPartyFrequency(),
        cleaningHabit = habitos_limpeza.toCleaningHabit(),
        acceptsPets = aceita_pets ?: false,
        sleepRoutine = horario_sono.toSleepRoutine(),
        acceptsRoomSharing = aceita_dividir_quarto ?: false,
        budget = Budget(minBudget = null, maxBudget = null), // Ofertante não tem orçamento
        isSmoker = false, // Ofertante não tem esses campos
        drinksAlcohol = false // Ofertante não tem esses campos
    )
}
