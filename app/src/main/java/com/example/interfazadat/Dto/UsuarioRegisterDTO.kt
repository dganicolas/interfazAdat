package com.example.interfazadat.Dto

import com.example.interfazadat.model.Direccion

data class UsuarioRegisterDTO(
    val username: String,
    val email: String,
    var password: String,
    val passwordRepeat: String,
    val rol: String?,
    val direccion: Direccion
)