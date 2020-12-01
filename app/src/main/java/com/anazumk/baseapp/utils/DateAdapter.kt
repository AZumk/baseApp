package com.anazumk.baseapp.utils

import com.squareup.moshi.*
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter : JsonAdapter<Date>() {

    companion object {
        fun parse(dateString: String): Date? {
            return parseISO(dateString) ?: parseFullString(dateString)
        }

        private fun parseISO(dateString: String): Date? {
            return try {
                DateFormat.getDateInstance().parse(dateString)
            } catch (e: Exception){
                null
            }
        }

        private fun parseFullString(dateString: String): Date? {
            return try {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.UK)
                sdf.parse(dateString)
            } catch (e: Exception) {
                null
            }
        }
    }

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return parse(reader.nextString())
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        val jsonString = value?.let {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.UK)
            formatter.format(it) }
            ?: ""

        writer.value(jsonString)
    }
}
