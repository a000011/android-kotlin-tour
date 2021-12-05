package com.tours.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: Parcelable> : Fragment() {
    companion object {
        const val ARGS_KEY = "__ARGS__"
    }

    fun getArgs(): T = requireArguments().getParcelable(ARGS_KEY)!!

    fun putArgs(args: T): Bundle = (arguments ?: Bundle()).apply {
        putParcelable(ARGS_KEY, args)
    }
}