package com.exchangerates.best.data.cache.abstraction


interface UserPreferenceManager {
    var user: String?

    var token: String?
    var darkMode: Boolean?

    companion object {
        const val user = "USER_AURA"
        const val token = "TOKEN_USER"
        const val darkMode = "DARK_MODE"
    }
}