package com.hotatekaoru.kotlincalculator.extension

import androidx.databinding.ObservableField

fun <T> ObservableField<String>.plus(additionalString: String) {
    this.get()?.let {
        this.set(it.plus(additionalString))
    }
}
