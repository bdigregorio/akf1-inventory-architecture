package com.udacity.shoestore

sealed class UiEvent {
    object Await : UiEvent()
    object Save : UiEvent()
    object Cancel : UiEvent()
}