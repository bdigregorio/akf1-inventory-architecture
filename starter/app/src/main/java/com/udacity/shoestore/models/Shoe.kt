package com.udacity.shoestore.models

data class Shoe(
    var name: String = "",
    var size: Double = 0.0,
    var company: String = "",
    var description: String = "",
    val images: MutableList<String> = mutableListOf()
)
