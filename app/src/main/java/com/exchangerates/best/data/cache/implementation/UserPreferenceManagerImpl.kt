package com.exchangerates.best.data.cache.implementation

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import com.exchangerates.best.data.cache.abstraction.UserPreferenceManager
import javax.inject.Inject

class UserPreferenceManagerImpl @Inject constructor(
    @ApplicationContext context: Context
) : UserPreferenceManager {

    private val preference = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    override var user: String?
        get() {
            val json = preference.getString(UserPreferenceManager.user, null)
            return json
        }
        set(value) {
            preference.edit {
                putString(UserPreferenceManager.user, value)
            }
        }

    override var token: String?
        get() {
            return preference.getString("", "")
        }
        set(value) {
            preference.edit {
                putString(UserPreferenceManager.token, "")
            }
        }

    override var darkMode: Boolean?
        get() {
            return preference.getBoolean(UserPreferenceManager.darkMode, false)
        }
        set(value) {
            preference.edit {
                putString(UserPreferenceManager.darkMode, "")
            }
        }

}