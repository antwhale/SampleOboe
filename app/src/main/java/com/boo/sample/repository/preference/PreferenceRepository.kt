package com.boo.sample.repository.preference

import com.boo.sample.repository.preference.PreferenceInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceRepository @Inject constructor(
    private val pref: PreferenceManager
): PreferenceInterface {
    override fun getRegistrationKey(): String = pref.getString(PREF_REGISTRATION_KEY, def = "")
    override fun setRegistrationKey(key: String) = pref.put(PREF_REGISTRATION_KEY, key)

    override fun getJwtUserToken(): String = pref.getString(PREF_JWT_USER_TOKEN, def = "")
    override fun setJwtUserToken(token: String) = pref.put(PREF_JWT_USER_TOKEN, token)

    override fun isFirst(): Boolean = pref.getBoolean(PREF_IS_FIRST, true)
    override fun setFirst(isFirst: Boolean) = pref.put(PREF_IS_FIRST, isFirst)

    companion object {
        private const val PREF_REGISTRATION_KEY = "9cf5fa7e-e6fa-4ead-b76a-90843eed5c69"
        private const val PREF_JWT_USER_TOKEN = "dcf1cf23-cd75-48e4-ba51-08511c660918"

        private const val PREF_IS_FIRST = "88abed4a-06d6-48fc-ba38-1bdc03265e96"
    }
}