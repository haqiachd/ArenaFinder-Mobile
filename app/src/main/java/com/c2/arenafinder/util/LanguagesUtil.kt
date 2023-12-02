package com.c2.arenafinder.util

import android.app.Activity
import android.os.Build
import java.util.Locale

import com.c2.arenafinder.data.local.DataShared
import com.c2.arenafinder.data.model.LanguageModel

class LanguagesUtil(
    private val activity: Activity,
    private val dataShared: DataShared,
) {

    companion object {
        const val ENGLISH = "en"
        const val INDONESIAN = "id" // def
        const val JAVANESE = "jv"
    }

    @JvmOverloads
    fun changeLanguage(langCode: String = getActivatedLanguage()) {
        val desiredLocale = Locale(langCode)
        val config = activity.resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            config.setLocale(desiredLocale)
        } else {
            @Suppress("DEPRECATION")
            config.locale = desiredLocale
        }
        @Suppress("DEPRECATION")
        activity.resources.updateConfiguration(config, activity.resources.displayMetrics)
    }

    fun getActivatedLanguage(): String {
        return try {
            dataShared.getData(DataShared.KEY.APP_LANGUAGE) ?: INDONESIAN
        } catch (ex: Throwable) {
            ex.printStackTrace()
            INDONESIAN
        }
    }

    fun setActivatedLanguage(langCode: String) {
        dataShared.setData(DataShared.KEY.APP_LANGUAGE, langCode)
    }

    fun getLanguageModel(languageModel: LanguageModel): String {
        return when (getActivatedLanguage()) {
            ENGLISH -> languageModel.english
            INDONESIAN -> languageModel.indonesian
            JAVANESE -> languageModel.javanese
            else -> languageModel.indonesian
        }.toString()
    }

}