package com.roomie.app.feature.profile.model
import com.roomie.app.feature.profile.data.remote.dto.AtualizarUsuarioInteressadoRequest
import com.roomie.app.feature.profile.data.remote.dto.InteressesUpdateDto
import com.roomie.app.feature.profile.data.remote.dto.UsuarioInteressadoDto

private fun String?.toPartyFrequency(): PartyFrequency {
    return when (this?.uppercase()) {
        "NUNCA" -> PartyFrequency.NEVER
        "AS_VEZES", "AS VEZES" -> PartyFrequency.SOMETIMES
        "FREQUENTE", "FREQUENTEMENTE" -> PartyFrequency.FREQUENT
        else -> PartyFrequency.SOMETIMES
    }
}

private fun String?.toCleaningHabit(): CleaningHabit {
    return when (this?.uppercase()) {
        "DIARIO", "DIÁRIO" -> CleaningHabit.DAILY
        "SEMANAL" -> CleaningHabit.WEEKLY
        "QUINZENAL" -> CleaningHabit.BIWEEKLY
        else -> CleaningHabit.OCCASIONAL
    }
}

private fun String?.toSleepRoutine(): SleepRoutine {
    return when (this?.uppercase()) {
        "MANHA", "MANHÃ", "MATUTINO" -> SleepRoutine.MORNING
        "NOITE", "NOTURNO" -> SleepRoutine.NIGHT
        "FLEXIVEL", "FLEXÍVEL" -> SleepRoutine.FLEXIBLE
        else -> SleepRoutine.FLEXIBLE
    }
}

private fun String?.toGenderOption(): GenderOption? {
    return when (this?.uppercase()) {
        "MASCULINO" -> GenderOption.MASCULINO
        "FEMININO" -> GenderOption.FEMININO
        "NAO_BINARIO", "NÃO_BINÁRIO", "NÃO BINÁRIO" -> GenderOption.NAO_BINARIO
        "PREFIRO_NAO_DIZER", "PREFIRO NÃO DIZER" -> GenderOption.PREFIRO_NAO_DIZER
        else -> null
    }
}

fun UsuarioInteressadoDto.toUserProfile(): UserProfile {
    val partyFrequency = interesses.frequencia_festas.toPartyFrequency()
    val cleaningHabit = interesses.habitos_limpeza.toCleaningHabit()
    val sleepRoutine = interesses.horario_sono.toSleepRoutine()
    val genderOption = genero.toGenderOption()

    val cleanlinessLevel = when (cleaningHabit) {
        CleaningHabit.DAILY -> 5
        CleaningHabit.WEEKLY -> 4
        CleaningHabit.BIWEEKLY -> 3
        CleaningHabit.OCCASIONAL -> 2
    }

    val socialLevel = when (partyFrequency) {
        PartyFrequency.NEVER -> 1
        PartyFrequency.SOMETIMES -> 3
        PartyFrequency.FREQUENT -> 5
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
            PartyFrequency.NEVER -> add("Não gosta de festas")
            PartyFrequency.SOMETIMES -> add("Festas às vezes")
            PartyFrequency.FREQUENT -> add("Gosta de festas")
        }

        ocupacao?.let { add(it) }
    }

    val lifestyle = LifestylePreferences(
        acceptsPets = interesses.aceita_pets,
        isSmoker = false, // API ainda não envia isso
        partyFrequency = partyFrequency,
        isQuiet = partyFrequency == PartyFrequency.NEVER,
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
        PartyFrequency.NEVER -> "NUNCA"
        PartyFrequency.SOMETIMES -> "AS_VEZES"
        PartyFrequency.FREQUENT -> "FREQUENTE"
    }
}

private fun SleepRoutine.toApiValue(): String {
    return when (this) {
        SleepRoutine.MORNING -> "MANHA"
        SleepRoutine.NIGHT -> "NOITE"
        SleepRoutine.FLEXIBLE -> "FLEXIVEL"
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
