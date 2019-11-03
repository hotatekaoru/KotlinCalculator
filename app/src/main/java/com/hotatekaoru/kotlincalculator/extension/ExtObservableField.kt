package com.hotatekaoru.kotlincalculator.extension

import androidx.databinding.ObservableField

fun ObservableField<String>.plus(additionalString: String) {
    this.get()?.let {
        this.set(it.plus(additionalString))
    }
}

fun ObservableField<String>.minusLastCharacter(): ObservableField<String> {
    this.get()?.let {
        if (it.isNotBlank()) {
            this.set(it.substring(0, it.length - 1))
        }
    }
    return this
}

fun ObservableField<String>.takeLast(number: Number): String {
    this.get()?.let {
        if (it.isNotBlank()) {
            return it.takeLast(number.toInt())
        }
    }
    return ""
}
