package com.roomie.app.feature.profile.model
import com.roomie.app.feature.profile.data.remote.dto.AtualizarUsuarioInteressadoRequest
import com.roomie.app.feature.profile.data.remote.dto.InteressesUpdateDto
import com.roomie.app.feature.profile.data.remote.dto.UsuarioInteressadoDto

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
        "PREFIRO_NAO_DIZER", "PREFIRO NÃO DIZER" -> GenderOption.PREFIRO_NAO_INFORMAR
        else -> null
    }
}

fun UsuarioInteressadoDto.toUserProfile(): UserProfile {
    val partyFrequency = interesses.frequencia_festas.toPartyFrequency()
    val cleaningHabit = interesses.habitos_limpeza.toCleaningHabit()
    val sleepRoutine = interesses.horario_sono.toSleepRoutine()
    val genderOption = genero.toGenderOption()

    val cleanlinessLevel = when (cleaningHabit) {
        CleaningHabit.DIARIO -> 5
        CleaningHabit.SEMANAL -> 4
        CleaningHabit.QUINZENAL -> 3
        CleaningHabit.OCASIONAL -> 2
    }

    val socialLevel = when (partyFrequency) {
        PartyFrequency.NUNCA -> 1
        PartyFrequency.AS_VEZES -> 3
        PartyFrequency.FREQUENTE -> 5
    }

    val tags = buildList {
        genderOption?.let { add(it.label) }

        if (interesses.aceita_pets) {
            add("Aceita pets")
        }

        if (!interesses.aceita_dividir_quarto) {
            add("Prefere quarto individual")
        } else {
            add("Aceita dividir quarto")
        }

        when (partyFrequency) {
            PartyFrequency.NUNCA -> add("Não gosta de festas")
            PartyFrequency.AS_VEZES -> add("Festas às vezes")
            PartyFrequency.FREQUENTE -> add("Gosta de festas")
        }

        ocupacao?.let { add(it) }
    }

    val lifestyle = LifestylePreferences(
        acceptsPets = interesses.aceita_pets,
        isSmoker = false, // API ainda não envia isso
        partyFrequency = partyFrequency,
        isQuiet = partyFrequency == PartyFrequency.NUNCA,
        sleepRoutine = sleepRoutine,
        cleanlinessLevel = cleanlinessLevel,
        socialLevel = socialLevel,
        cleaningHabit = cleaningHabit,
        studySchedule = null, // API não manda
        acceptsSharedRoom = interesses.aceita_dividir_quarto,
        tags = tags,
    )

    val budget = Budget(
        minBudget = interesses.orcamento_min?.toInt(),
        maxBudget = interesses.orcamento_max?.toInt(),
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
            notificationsEnabled = true,   // ainda local
            showAsOnline = true,
            discoveryEnabled = true,
            darkModeEnabled = true,
        ),

        localPhoto = null,
        photoUrl = foto_de_perfil,
    )
}

private fun PartyFrequency.toApiValue(): String {
    return when (this) {
        PartyFrequency.NUNCA -> "NUNCA"
        PartyFrequency.AS_VEZES -> "AS_VEZES"
        PartyFrequency.FREQUENTE -> "FREQUENTE"
    }
}

private fun SleepRoutine.toApiValue(): String {
    return when (this) {
        SleepRoutine.MATUTINO -> "MANHA"
        SleepRoutine.NOTURNO -> "NOITE"
        SleepRoutine.VESPERTINO -> "TARDE"
        SleepRoutine.FLEXIVEL -> "FLEXIVEL"
    }
}

fun UserProfile.toUpdateRequest(): AtualizarUsuarioInteressadoRequest {
    val habitosLimpeza = when {
        lifestyle.cleanlinessLevel >= 5 -> "DIARIO"
        lifestyle.cleanlinessLevel >= 4 -> "SEMANAL"
        lifestyle.cleanlinessLevel >= 3 -> "QUINZENAL"
        else -> "OCASIONAL"
    }

    val interessesDto = InteressesUpdateDto(
        frequencia_festas = lifestyle.partyFrequency.toApiValue(),
        habitos_limpeza = habitosLimpeza,
        aceita_pets = lifestyle.acceptsPets,
        horario_sono = lifestyle.sleepRoutine.toApiValue(),
        orcamento_min = budget.minBudget?.toDouble(),
        orcamento_max = budget.maxBudget?.toDouble(),
        aceita_dividir_quarto = lifestyle.acceptsSharedRoom,
    )

    return AtualizarUsuarioInteressadoRequest(
        nome = name,
        email = email,
        cidade = city,
        ocupacao = professionOrCourse,
        bio = bio,
        genero = gender?.name,        
        foto_de_perfil = photoUrl,
        interesses = interessesDto,
    )
}
