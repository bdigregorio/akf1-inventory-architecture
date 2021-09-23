package com.udacity.shoestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import timber.log.Timber

class MainViewModel : ViewModel() {

    private var _shoes = MutableLiveData<MutableList<Shoe>>()
    val shoes: LiveData<MutableList<Shoe>>
        get() = _shoes

    init {
        Timber.d("Initializing new shoe list")
        _shoes.value = mutableListOf()
    }

    fun saveNewShoeEntry(shoe: Shoe) {
        Timber.d("Adding entry to shoe viewmodel")
        _shoes.value?.add(shoe)
        Timber.d("Shoe list now contains ${shoes.value?.size} elements: ${shoes.value}")
    }
}