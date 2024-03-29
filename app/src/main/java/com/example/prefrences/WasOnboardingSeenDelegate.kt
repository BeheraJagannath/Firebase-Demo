package com.example.prefrences

import com.pixplicity.easyprefs.library.Prefs
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WasOnboardingSeenDelegate : ReadWriteProperty<AppPreferencesDelegates, Boolean> {

    companion object {
        const val PREF_KEY_WAS_ONBOARDING_SEEN = "was_onboarding_seen"
    }
    override fun getValue(thisRef: AppPreferencesDelegates, property: KProperty<*>): Boolean =
        Prefs.getBoolean(PREF_KEY_WAS_ONBOARDING_SEEN, false)

    override fun setValue(thisRef: AppPreferencesDelegates, property: KProperty<*>, value: Boolean) {
        Prefs.putBoolean(PREF_KEY_WAS_ONBOARDING_SEEN, value)
    }
}