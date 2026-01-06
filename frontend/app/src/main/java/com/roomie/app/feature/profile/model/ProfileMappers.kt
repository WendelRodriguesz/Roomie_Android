package com.roomie.app.feature.profile.model

import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.profile.data.remote.dto.AtualizarUsuarioBasicoRequest
import com.roomie.app.feature.profile.data.remote.dto.UsuarioInteressadoDto
import com.roomie.app.feature.profile.data.remote.dto.UsuarioOfertanteDto

private fun String?.toPartyFrequency(): PartyFrequency {
    return when (this?.uppercase()) {
        "NUNCA" -> PartyFrequency.NUNCA
        "AS_VEZES", "AS VEZES" -> PartyFrequency.AS_VEZES
        "FREQUENTE", "FREQUENTEMENTE" -> PartyFrequency.FREQUENTE
        else -> PartyFrequency.AS_VEZES
    }
}

private fun String?.toCleaningHabit(): CleaningHabit {
    return when (this?.uppercase()) {
        "DIARIO", "DIÁRIO" -> CleaningHabit.DIARIO
        "SEMANAL" -> CleaningHabit.SEMANAL
        "QUINZENAL" -> CleaningHabit.QUINZENAL
        else -> CleaningHabit.OCASIONAL
    }
}

private fun String?.toSleepRoutine(): SleepRoutine {
    return when (this?.uppercase()) {
        "MANHA", "MANHÃ", "MATUTINO" -> SleepRoutine.MATUTINO
        "NOITE", "NOTURNO" -> SleepRoutine.NOTURNO
        "VESPERTINO", "TARDE" -> SleepRoutine.VESPERTINO
        else -> SleepRoutine.FLEXIVEL
    }
}

private fun String?.toGenderOption(): GenderOption? {
    return when (this?.uppercase()) {
        "MASCULINO" -> GenderOption.MASCULINO
        "FEMININO" -> GenderOption.FEMININO
        "NAO_BINARIO", "NÃO_BINÁRIO", "NÃO BINÁRIO" -> GenderOption.NAO_BINARIO
        "OUTROS" -> GenderOption.PREFIRO_NAO_INFORMAR
        "PREFIRO_NAO_DIZER", "PREFIRO NÃO DIZER", "PREFIRO_NAO_INFORMAR", "PREFIRO NÃO INFORMAR" ->
            GenderOption.PREFIRO_NAO_INFORMAR
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
        isSmoker = false,
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
