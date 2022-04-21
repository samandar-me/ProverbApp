package com.sdk.proverbsapp.util

import kotlin.math.log

fun main() {
    val a: Float = 82f

    val check = log(a, 3f)
    if(check.toInt().toFloat() == check){
        print(true)
    } else {
        print(false)
    }
}