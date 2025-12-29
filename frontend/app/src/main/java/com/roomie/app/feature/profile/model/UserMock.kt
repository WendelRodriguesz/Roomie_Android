package com.roomie.app.feature.profile.model

import com.roomie.app.R

object UserMock {

    val profileYou = UserProfile(
        id = 1L,
        name = "Você",
        email = "voce@example.com",
        age = 22,
        role = ProfileRole.SEEKER,
        city = "São Paulo, SP",
        professionOrCourse = "Engenharia de Software - USP",
        bio = "Estudante de engenharia que busca um ambiente tranquilo para estudar. Gosto de cozinhar e manter a casa sempre limpa!",

        gender = GenderOption.MASCULINO,

        budget = Budget(
            minBudget = 800,
            maxBudget = 1200,
        ),

        lifestyle = LifestylePreferences(
            acceptsPets = true,
            isSmoker = false,
            partyFrequency = PartyFrequency.AS_VEZES,
            cleaningHabit = CleaningHabit.DIARIO,
            isQuiet = true,
            sleepRoutine = SleepRoutine.NOTURNO,
            cleanlinessLevel = 5,
            socialLevel = 3,
            studySchedule = "Noturno",
            acceptsSharedRoom = false,
            tags = listOf("Organizada", "Estudante", "Não fumante", "Gosta de cozinhar"),
        ),

        settings = AccountSettings(
            notificationsEnabled = true,
            showAsOnline = true,
            discoveryEnabled = true,
            darkModeEnabled = true,
        ),

        localPhoto = R.drawable.pessoa3,
        photoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRy_wyA_G8PKv4a-_PRqH6pQN6beLNPEXmN9Q&s",
    )

    val profileRoomie1 = UserProfile(
        id = 2L,
        name = "Lucas",
        email = "lucas@example.com",
        age = 24,
        role = ProfileRole.OFFEROR,
        city = "São Paulo, SP",
        professionOrCourse = "Desenvolvedor Backend",
        bio = "Trabalho remoto e passo a maior parte do tempo em casa. Busco alguém tranquilo e organizado.",

        gender = GenderOption.MASCULINO,

        budget = Budget(
            minBudget = 1000,
            maxBudget = 1600,
        ),

        lifestyle = LifestylePreferences(
            acceptsPets = false,
            isSmoker = false,
            partyFrequency = PartyFrequency.NUNCA,
            cleaningHabit = CleaningHabit.QUINZENAL,
            isQuiet = true,
            sleepRoutine = SleepRoutine.MATUTINO,
            cleanlinessLevel = 4,
            socialLevel = 2,
            studySchedule = "Manhã",
            acceptsSharedRoom = false,
            tags = listOf("Trabalho remoto", "Tranquilo", "Organizado"),
        ),

        settings = AccountSettings(
            notificationsEnabled = true,
            showAsOnline = false,
            discoveryEnabled = true,
            darkModeEnabled = false,
        ),

        localPhoto = R.drawable.pessoa2,
        photoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRy_wyA_G8PKv4a-_PRqH6pQN6beLNPEXmN9Q&s",
    )

    val profileRoomie2 = UserProfile(
        id = 3L,
        name = "Ana",
        email = "ana@example.com",
        age = 21,
        role = ProfileRole.SEEKER,
        city = "Campinas, SP",
        professionOrCourse = "Designer UX - PUC",
        bio = "Extrovertida, gosto de receber amigos de vez em quando e manter tudo bem combinado antes.",

        gender = GenderOption.FEMININO,

        budget = Budget(
            minBudget = 700,
            maxBudget = 1100,
        ),

        lifestyle = LifestylePreferences(
            acceptsPets = true,
            isSmoker = false,
            partyFrequency = PartyFrequency.FREQUENTE,
            cleaningHabit = CleaningHabit.SEMANAL,
            isQuiet = false,
            sleepRoutine = SleepRoutine.FLEXIVEL,
            cleanlinessLevel = 3,
            socialLevel = 5,
            studySchedule = "Tarde",
            acceptsSharedRoom = true,
            tags = listOf("Extrovertida", "Ama pets", "Criativa"),
        ),

        settings = AccountSettings(
            notificationsEnabled = false,
            showAsOnline = true,
            discoveryEnabled = true,
            darkModeEnabled = true,
        ),

        localPhoto = R.drawable.pessoa1,
        photoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRy_wyA_G8PKv4a-_PRqH6pQN6beLNPEXmN9Q&s",
    )

    val all = listOf(profileYou, profileRoomie1, profileRoomie2)
}
