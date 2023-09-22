package com.blitzmachine.freetogamecom.views.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UiViewModel: ViewModel() {

    private val _showMainLogo: MutableLiveData<Boolean> = MutableLiveData(true)
    val showMainLogo: LiveData<Boolean> get() = _showMainLogo

    fun showMainLogo(boolean: Boolean) {
        _showMainLogo.postValue(boolean)
    }
}