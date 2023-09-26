package com.blitzmachine.freetogamecom.views.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UiViewModel: ViewModel() {

    private val _showMainLogo: MutableLiveData<Boolean> = MutableLiveData(true)
    val showMainLogo: LiveData<Boolean> get() = _showMainLogo

    private val _showBottomSheet: MutableLiveData<Boolean> = MutableLiveData()
    val showBottomSheet: LiveData<Boolean> get() = _showBottomSheet

    fun showMainLogo(boolean: Boolean) {
        _showMainLogo.postValue(boolean)
    }

    fun showBottomSheet(boolean: Boolean) {
        _showBottomSheet.postValue(boolean)
    }
}