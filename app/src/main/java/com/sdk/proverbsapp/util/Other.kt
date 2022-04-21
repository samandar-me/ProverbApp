package com.sdk.proverbsapp.util

import android.content.Context
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty

fun Context.toast(text: String) {
    Toasty.success(this, text, Toasty.LENGTH_SHORT).show()
}

fun Fragment.toast(text: String) {
    Toasty.success(this.requireContext(), text, Toasty.LENGTH_SHORT).show()
}