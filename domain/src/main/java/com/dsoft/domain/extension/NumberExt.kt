package com.dsoft.domain.extension

fun Double.kelvinToCelsius(): Double {
    return this - 273.15
}

fun Number.shrinkToTwoDigits(): String {
    return String.format("%.2f", this)
}

fun Double.convertToFahrenheit(): Double {
    return ((this * 9) / 5) + 32
}