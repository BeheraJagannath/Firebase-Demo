package com.example.prefrences

class AppPreferencesDelegates private constructor() {
    var wasOnboardingScreen by WasOnboardingSeenDelegate()

    companion object {
        private var INSTANCE: AppPreferencesDelegates? = null
        fun get(): AppPreferencesDelegates = INSTANCE ?: AppPreferencesDelegates()
    }

}