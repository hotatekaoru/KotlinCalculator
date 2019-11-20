package com.hotatekaoru.kotlincalculator.extensions

fun Double.toIntAndToString(): String {
    return if (this.toInt().toDouble() == this) {
        this.toInt().toString()
    } else {
        this.toString()
    }
}
