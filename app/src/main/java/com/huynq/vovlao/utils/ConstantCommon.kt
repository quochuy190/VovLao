package vn.neo.smsvietlott.common.di.util

object ConstantCommon {
    const val ISO_8601_DATE_TIME_FORMAT_RECEIVE = "yyyy-MM-dd'T'HH:mm:ssZZ"
    const val PREF_FILE_NAME = "com.vbeeon.iot_dbs"
    const val IS_FIRST_OPEN_APP = "IS_FIRST_OPEN_APP"
    const val KEY_USER_NAME = "KEY_USER_NAME"
    const val KEY_CHANGE_LANGUAGE = "KEY_CHANGE_LANGUAGE"
    const val KEY_TOKEN_FIREBASE = "KEY_TOKEN_FIREBASE"
    const val LANGUAGE = "LANGUAGE"
    const val DATE_PATTERN_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val DATE_PATTERN = "dd-MM-yyyy"
    const val DATE_PATTERN_WITH_DOT = "dd.MM.yyyy"
    const val TIME_PATTERN = "HH:mm"
    const val TIME_PATTERN_AM_PM = "hh:mm a"
    const val MIN_TIME_MINUTE = 30
    const val MAX_TIME_MINUTE = 23 * 60
    object Value {
        const val DEFAULT_LANGUAGE_ID = 0
    }

    object RequestCode {
        const val CHANGE_LANGUAGE = 10000
    }

}