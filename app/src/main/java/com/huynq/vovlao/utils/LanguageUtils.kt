package com.huynq.vovlao.utils

import android.content.res.Configuration
import android.util.Log
import com.huynq.vovlao.R
import com.huynq.vovlao.VoVApplication
import com.huynq.vovlao.data.model.Language
import vn.neo.smsvietlott.common.di.util.ConstantCommon
import java.util.*
import kotlin.collections.ArrayList


class LanguageUtils {
    private var sCurrentLanguage: Language? = null

    //val arrImg : () -> Int = {R.drawable.ic_pause; R.drawable.ic_pause; R.drawable.ic_pause}
    val arrImg: IntArray = intArrayOf(R.drawable.flag_eng, R.drawable.flag_vn, R.drawable.flag_laos)
    fun getCurrentLanguage(): Language? {
        if (sCurrentLanguage == null) {
            sCurrentLanguage = initCurrentLanguage()
        }
        return sCurrentLanguage
    }

    /**
     * check language exist in SharedPrefs, if not exist then default language is English
     */
    private fun initCurrentLanguage(): Language {
        var currentLanguage: Language?
        val isFirs =
            SharedPrefs.instance.get(ConstantCommon.IS_FIRST_OPEN_APP, Boolean::class.java)
        if (!isFirs) {
            currentLanguage = Language(
                ConstantCommon.Value.DEFAULT_LANGUAGE_ID,
                VoVApplication.instance.getString(R.string.language_english),
                VoVApplication.instance.getString(R.string.language_english_code), arrImg[0], true
            )
            SharedPrefs.instance.put(ConstantCommon.LANGUAGE, currentLanguage)
        } else {
            currentLanguage =
                SharedPrefs.instance.get(ConstantCommon.LANGUAGE, Language::class.java)!!
        }
        return currentLanguage
    }

    /**
     * return language list from string.xml
     */
    fun getLanguageData(): List<Language?>? {
        val languageList: MutableList<Language?> = ArrayList()
        val languageNames =
            VoVApplication.instance.getResources().getStringArray(R.array.language_names)
        val languageCodes =
            VoVApplication.instance.getResources().getStringArray(R.array.language_codes)
        if (languageNames.size != languageCodes.size) {
            // error, make sure these arrays are same size
            return languageList
        }
        var i = 0
        val size = languageNames.size
        while (i < size) {
            if (i == getCurrentLanguage()!!.id) {
                languageList.add(Language(i, languageNames[i], languageCodes[i], arrImg[i], true))
            } else
                languageList.add(Language(i, languageNames[i], languageCodes[i], arrImg[i], false))
            i++
        }
        return languageList
    }

    /**
     * load current locale and change language
     */
    fun loadLocale() {
        changeLanguage(initCurrentLanguage())
    }
    /**
     * change app language
     */
    fun changeLanguage(language: Language) {
        Log.e("TAG", "changeLanguage: " + language.code)
        sCurrentLanguage = language
        val locale = Locale(language.code)
        //val configuration = VoVApplication.instance.resources.configuration
        val configuration: Configuration = VoVApplication.instance.getResources().getConfiguration()
       // val configuration = Configuration()
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
    }

}