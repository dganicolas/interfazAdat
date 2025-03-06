package com.example.interfazadat.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object Usuario {
    var token :String by mutableStateOf("")
    var nombre:String? by mutableStateOf(null)
    var rol: Boolean?  by mutableStateOf(null)
    var contrasena:String? by mutableStateOf(null)
    var email:String? by mutableStateOf(null)
}