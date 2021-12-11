package com.tours.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment

typealias InstanceInitializer<A, C> = (args: A) -> C

abstract class BaseFragment<T : Parcelable> : Fragment() {
    companion object {
        const val ARGS_KEY = "__ARGS__"

        fun <A : Parcelable, C : BaseFragment<A>> getInitializer(constructor: () -> C): InstanceInitializer<A, C> {
            val initializer: InstanceInitializer<A, C> = fun(args: A): C {
                val tour = constructor()
                val bundle = Bundle()
                bundle.putParcelable(ARGS_KEY, args)
                tour.arguments = bundle
                tour.initArgs()
                return tour
            }

            return initializer
        }
    }

    lateinit var entity: T

    fun initArgs() {
        entity = requireArguments().getParcelable(ARGS_KEY)!!
    }
}