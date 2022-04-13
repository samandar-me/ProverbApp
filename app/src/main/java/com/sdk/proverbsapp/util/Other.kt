package com.sdk.proverbsapp.util

import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty

fun Fragment.toast(text: String) {
    Toasty.success(this.requireContext(), text, Toasty.LENGTH_SHORT).show()
}