package com.roomie.app.feature.profile.model

import com.roomie.app.R

object UserMock{

    val profileYou = UserProfile(
        id = "user-1",
        name = "Você",
        age = 22,
        role = ProfileRole.SEEKER,
        city = "São Paulo, SP",
        professionOrCourse = "Engenharia de Software - USP",
        bio = "Estudante de engenharia que busca um ambiente tranquilo para estudar. Gosto de cozinhar e manter a casa sempre limpa!",

        budget = Budget(
            minBudget = 800,
            maxBudget = 1200,
        ),

        lifestyle = LifestylePreferences(
            acceptsPets = true,
            isSmoker = false,
            partyFrequency = PartyFrequency.SOMETIMES,
            isQuiet = true,
            sleepRoutine = SleepRoutine.NIGHT,
            cleanlinessLevel = 5,
            socialLevel = 3,
            studySchedule = "Noturno",
            tags = listOf("Organizada", "Estudante", "Não fumante", "Gosta de cozinhar"),
        ),

        localPhoto = R.drawable.pessoa3,
        photoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRy_wyA_G8PKv4a-_PRqH6pQN6beLNPEXmN9Q&s",

        settings = AccountSettings(
            notificationsEnabled = true,
            showAsOnline = true,
            discoveryEnabled = true,
            darkModeEnabled = true,
        ),
    )

    val profileRoomie1 = UserProfile(
        id = "user-2",
        name = "Lucas",
        age = 24,
        role = ProfileRole.OFFEROR,
        city = "São Paulo, SP",
        professionOrCourse = "Desenvolvedor Backend",
        bio = "Trabalho remoto e passo a maior parte do tempo em casa. Busco alguém tranquilo e organizado.",

        budget = Budget(
            minBudget = 1000,
            maxBudget = 1600,
        ),

        lifestyle = LifestylePreferences(
            acceptsPets = false,
            isSmoker = false,
            partyFrequency = PartyFrequency.NEVER,
            isQuiet = true,
            sleepRoutine = SleepRoutine.MORNING,
            cleanlinessLevel = 4,
            socialLevel = 2,
            studySchedule = "Manhã",
            tags = listOf("Trabalho remoto", "Tranquilo", "Organizado"),
        ),

        localPhoto = R.drawable.pessoa2,
        photoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRy_wyA_G8PKv4a-_PRqH6pQN6beLNPEXmN9Q&s",

        settings = AccountSettings(
            notificationsEnabled = true,
            showAsOnline = false,
            discoveryEnabled = true,
            darkModeEnabled = false,
        ),
    )

    val profileRoomie2 = UserProfile(
        id = "user-3",
        name = "Ana",
        age = 21,
        role = ProfileRole.SEEKER,
        city = "Campinas, SP",
        professionOrCourse = "Designer UX - PUC",
        bio = "Extrovertida, gosto de receber amigos de vez em quando e manter tudo bem combinado antes.",

        budget = Budget(
            minBudget = 700,
            maxBudget = 1100,
        ),

        lifestyle = LifestylePreferences(
            acceptsPets = true,
            isSmoker = false,
            partyFrequency = PartyFrequency.FREQUENT,
            isQuiet = false,
            sleepRoutine = SleepRoutine.FLEXIBLE,
            cleanlinessLevel = 3,
            socialLevel = 5,
            studySchedule = "Tarde",
            tags = listOf("Extrovertida", "Ama pets", "Criativa"),
        ),

        localPhoto = R.drawable.pessoa1,
        photoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRy_wyA_G8PKv4a-_PRqH6pQN6beLNPEXmN9Q&s",

        settings = AccountSettings(
            notificationsEnabled = false,
            showAsOnline = true,
            discoveryEnabled = true,
            darkModeEnabled = true,
        ),
    )

    val all = listOf(profileYou, profileRoomie1, profileRoomie2)
}
