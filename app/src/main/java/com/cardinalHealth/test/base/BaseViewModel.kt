package com.cardinalHealth.test.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel:ViewModel() {

    val compositeDisposable by lazy { CompositeDisposable() }
    override fun onCleared() {
        compositeDisposable.apply {
            dispose()
            clear()
        }
        super.onCleared()
    }
}