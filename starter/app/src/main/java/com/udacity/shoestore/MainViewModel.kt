package com.udacity.shoestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import timber.log.Timber

class MainViewModel : ViewModel() {

    private var _shoes = MutableLiveData(mutableListOf<Shoe>())
    val shoes: LiveData<MutableList<Shoe>>
        get() = _shoes

    private val _uiEvent = MutableLiveData<UiEvent>(UiEvent.Await)
    val uiEvent: LiveData<UiEvent>
        get() = _uiEvent

    fun awaitNextUiEvent() {
        _uiEvent.value = UiEvent.Await
    }

    fun onSaveClicked() {
        Timber.d("Save clicked")
        _uiEvent.value = UiEvent.Save
    }

    fun onCancelClicked() {
        Timber.d("Cancel clicked")
        _uiEvent.value = UiEvent.Cancel
    }

    fun saveValidShoe(shoe: Shoe) {
        Timber.d("Add shoe data to list")
        _shoes.value?.add(shoe)
    }
}