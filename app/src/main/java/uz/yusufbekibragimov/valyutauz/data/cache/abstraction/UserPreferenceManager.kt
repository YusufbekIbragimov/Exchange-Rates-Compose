package uz.yusufbekibragimov.valyutauz.data.cache.abstraction


interface UserPreferenceManager {
    var user: String?

    var token: String?

    companion object {
        const val user = "USER_AURA"
        const val token = "TOKEN_USER"
    }
}