package com.company.app.core.utils

import java.text.Normalizer


fun String.isValidEmail(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.stripAccents(): String {
    val normalized: String = Normalizer.normalize(this, Normalizer.Form.NFD)
    return  normalized.replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
}