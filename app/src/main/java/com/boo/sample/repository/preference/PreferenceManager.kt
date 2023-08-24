package com.boo.sample.repository.preference

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import javax.inject.Inject

class PreferenceManager @Inject constructor(application: Application) {
    private val pref: SharedPreferences

    init {
        val sharedPrefName = application.packageName + ".app_shared_preference"
        pref = application.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
    }

    @JvmOverloads
    fun put(key: String, value: Any, isCommitSync: Boolean = DEFAULT_COMMIT_SYNC) {
        Log.d(TAG, "put, key = $key, value = $value")
        //commit() 동기적으로 메모리에 쓰는 행위라 기본스레드에서 쓰는 것 지양!!
        pref.edit(isCommitSync){
            putWithTypeCheck(this, key, value)
        }
    }

    private fun putWithTypeCheck(editor: SharedPreferences.Editor, key: String, value: Any) {
        when(value){
            is CharSequence, Char, String, Double -> editor.putString(key, value.toString())
            is Boolean -> editor.putBoolean(key, value)
            is Int -> editor.putInt(key, value)
            is Long -> editor.putLong(key, value)
            is Float -> editor.putFloat(key, value)
            else -> Log.d(TAG, "putWithTypeCheck: Not Support Type")
        }
    }

    @JvmOverloads
    fun getChar(key: String, def: Char = Char.MIN_VALUE): Char =
        pref.runCatching { getString(key, def.toString()) }.getOrNull()?.getOrNull(0) ?: def

    @JvmOverloads
    fun getCharSequence(key: String, def: CharSequence = ""): CharSequence =
        pref.runCatching { getString(key, def.toString()) }.getOrNull() ?: def

    @JvmOverloads
    fun getString(key: String, def: String = ""): String =
        pref.runCatching { getString(key, def) }.getOrNull() ?: def

    @JvmOverloads
    fun getBoolean(key: String, def: Boolean = false): Boolean =
        pref.runCatching { getBoolean(key, def) }.getOrDefault(defaultValue = def)

    @JvmOverloads
    fun getInt(key: String, def: Int = 0) =
        pref.runCatching { getInt(key, def) }.getOrDefault(defaultValue = def)

    @JvmOverloads
    fun getLong(key: String, def: Long = 0) =
        pref.runCatching { getLong(key, def) }.getOrDefault(defaultValue = def)

    @JvmOverloads
    fun getFloat(key: String, def: Float = 0f): Float =
        pref.runCatching { getFloat(key, def) }.getOrDefault(defaultValue = def)

    @JvmOverloads
    fun getDouble(key: String, def: Double = 0.toDouble()): Double =
        pref.runCatching { getString(key, def.toString()) }.getOrNull()?.toDouble() ?: def

    fun remove(key: String) = pref.edit { this.remove(key).commit() }

    fun clear() = pref.edit{ this.clear().commit()}

    companion object {
        private val TAG = PreferenceManager::class.java.simpleName

        private const val DEFAULT_COMMIT_SYNC = true
    }
}