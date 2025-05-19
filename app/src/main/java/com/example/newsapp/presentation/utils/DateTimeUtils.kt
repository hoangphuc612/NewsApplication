package com.example.newsapp.presentation.utils

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

object DateTimeUtils {

    private const val FORMAT_DD_MM_YY = "dd/MM/yyyy"

    fun formatIsoDateToDdMmYyyy(dateString: String?): String {
        return try {
            val parsedDate = OffsetDateTime.parse(dateString)
            val outputFormatter = DateTimeFormatter.ofPattern(FORMAT_DD_MM_YY, Locale.getDefault())
            parsedDate.format(outputFormatter)
        } catch (e: DateTimeParseException) {
            "Invalid date"
        }
    }
}