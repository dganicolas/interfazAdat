package com.example.interfazadat.model

data class Tarea(
    val _id:String?,
    val nombre: String,
    val descripcion:String,
    var estado:Boolean,
    val autor:String,
)