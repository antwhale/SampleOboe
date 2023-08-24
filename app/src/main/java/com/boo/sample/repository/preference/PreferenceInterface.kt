package com.boo.sample.repository.preference

interface PreferenceInterface {
    fun getRegistrationKey(): String
    fun setRegistrationKey(key: String)

    fun getJwtUserToken(): String
    fun setJwtUserToken(token: String)

    fun isFirst(): Boolean
    fun setFirst(isFirst: Boolean)
}