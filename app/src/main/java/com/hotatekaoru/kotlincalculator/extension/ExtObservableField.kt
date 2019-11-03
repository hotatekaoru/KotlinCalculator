package com.hotatekaoru.kotlincalculator.extension

import androidx.databinding.ObservableField

fun ObservableField<String>.plus(additionalString: String) {
    this.get()?.let {
        this.set(it.plus(additionalString))
    }
}

fun ObservableField<String>.minusLastCharacter() {
    this.get()?.let {
        if (it.isNotBlank()) {
            this.set(it.substring(0, it.length - 1))
        }
    }
}
