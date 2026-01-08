package com.roomie.app.navigation

object Routes {
    const val HOME = "home"
    const val CHAT = "chat"
    const val MATCH = "match"
    const val NOTIFICATIONS = "notifications"
    const val PROFILE = "profile"

    const val MY_LISTINGS = "my_listings"

    const val WELCOME_SCREEN = "welcome"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val REGISTER_ROLE = "register_role"
    const val EDIT_PROFILE = "edit_profile"
    const val EDIT_PREFERENCES = "edit_preferences"
    const val ADD_VAGA = "addvaga"
    const val PREFERENCES_REGISTRATION = "preference_registration"
    const val LISTING_DETAIL = "listing_detail/{listing_id}"

    val BOTTOM_BAR_ROUTES = listOf(HOME, CHAT, MATCH, NOTIFICATIONS, PROFILE, MY_LISTINGS)
}