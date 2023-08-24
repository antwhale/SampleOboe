package com.boo.sample.base.config

import android.app.Application
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.lang.Exception

class Config {
    @SerializedName("API_URL")
    val apiUrl = ""

    @SerializedName("FILE_URL")
    val fileUrl = ""

    @SerializedName("WEB_VIEW_URL")
    val webUrl = ""

    @SerializedName("READ_TIME_OUT")
    val readTimeOut = 10_000L

    @SerializedName("WRITE_TIME_OUT")
    val writeTimeOut = 10_000L

    @SerializedName("CONNECT_TIME_OUT")
    val connectTimeOut = 10_000L

    companion object {
        lateinit var INSTANCE: Config

        fun init(application: Application, path: String) {
            val configJson = try {
                application.assets.open(path).bufferedReader().use { it.readText() }
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
            INSTANCE = Gson().fromJson(configJson, Config::class.java)
        }
    }


}