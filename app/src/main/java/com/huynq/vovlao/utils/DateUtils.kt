package com.huynq.vovlao.utils


import vn.neo.smsvietlott.common.di.util.ConstantCommon
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    fun convertDate(dtStart: String) : Date? {
      //  val dtStart = "2010-10-15T09:27:37Z"
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        try {
            val date = format.parse(dtStart)
            return date
        } catch (e: ParseException) {
            return null
            e.printStackTrace()
        }


    }
    fun convertString(dateStart: String): String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        try {
            val date = convertDate(dateStart)
            val dateTime = dateFormat.format(date)
            return dateTime
        } catch (e: Exception) {
            return ""
            e.printStackTrace()
        }
    }

}