package com.udacity.shoestore

import java.lang.RuntimeException

sealed class UiEvent {
    object Await : UiEvent()
    object Save : UiEvent()
    object Cancel : UiEvent()
}

class UnhandledUiEventException(message: String = "") : RuntimeException(message)