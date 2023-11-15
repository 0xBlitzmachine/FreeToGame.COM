package com.blitzmachine.freetogamecom.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UiViewModel: ViewModel() {

    private val _showSuccessLayout: MutableLiveData<Boolean> = MutableLiveData(true)
    val showSuccessLayout: LiveData<Boolean> get() = _showSuccessLayout

    private val _showLoadingLayout: MutableLiveData<Boolean> = MutableLiveData(false)
    val showLoadingLayout: LiveData<Boolean> get() = _showLoadingLayout

    private val _showErrorLayout: MutableLiveData<Boolean> = MutableLiveData(false)
    val showErrorLayout: LiveData<Boolean> get() = _showErrorLayout


    private val _errorTitle: MutableLiveData<String> = MutableLiveData()
    val errorTitle: MutableLiveData<String> get() = _errorTitle

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: MutableLiveData<String> get() = _errorMessage

    fun enableSuccessScreen() {
        _showSuccessLayout.postValue(true)
        if (showErrorLayout.value == true) {
            _showErrorLayout.postValue(false)
        }
    }

    fun enableErrorScreen(errorTitle: String, errorMessage: String) {
        _errorTitle.postValue(errorTitle)
        _errorMessage.postValue(errorMessage)
        _showErrorLayout.postValue(true)
        if (showSuccessLayout.value == true) {
            _showSuccessLayout.postValue(false)
        }
    }

    fun enableLoadingScreen(delayDuration: Long = 1000L) {
        viewModelScope.launch {
            _showLoadingLayout.postValue(true)
            if (showSuccessLayout.value == true) {
                _showSuccessLayout.postValue(false)
            }

            if (showErrorLayout.value == true) {
                _showErrorLayout.postValue(false)
            }
            delay(delayDuration)
            _showLoadingLayout.postValue(false)
            _showErrorLayout.postValue(false)
            _showSuccessLayout.postValue(true)
        }
    }
}