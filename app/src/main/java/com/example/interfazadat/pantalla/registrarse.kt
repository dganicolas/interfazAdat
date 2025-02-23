package com.example.interfazadat.pantalla

import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.interfazadat.Dto.UsuarioDTO
import com.example.interfazadat.Dto.UsuarioRegisterDTO
import com.example.interfazadat.Model.Direccion
import com.example.interfazadat.Model.ErrorRespuesta
import com.example.interfazadat.apiservice.RetrofitClient
import com.example.interfazadat.componentes.editText
import com.example.interfazadat.componentes.texto
import com.example.interfazadat.componentes.ventana
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Registrarse(navControlador: NavHostController) {
    var username by remember { mutableStateOf("nico") }
    var email by remember { mutableStateOf("n@n.com") }
    var password by remember { mutableStateOf("nico") }
    var passwordRepeat by remember { mutableStateOf("nico") }
    var calle by remember { mutableStateOf("Avenida Principal") }
    var numero by remember { mutableStateOf("123") }
    var cp by remember { mutableStateOf("28001") }
    var ciudad by remember { mutableStateOf("Barcelona") }
    var municipio by remember { mutableStateOf("Centro") }
    var provincia by remember { mutableStateOf("COMUNIDAD DE MADRID") }
    var titulo by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var estadoventana by remember { mutableStateOf(false) }
    if (estadoventana) {
        ventana(mensaje, titulo, { estadoventana = false })
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(Modifier.size(25.dp))
            texto("bienvenido a registro\n por favor complete los campos")
        }
        item {
            editText(
                texto = username,
                cambioTexto = { username = it },
                marcador = "username",
                username.length > 3
            )
        }
        item {
            editText(
                texto = email,
                cambioTexto = { email = it },
                marcador = "email",
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
            )
        }
        item {
            editText(
                texto = password,
                cambioTexto = { password = it },
                marcador = "password",
                password.length > 6
            )
        }
        item {
            editText(
                texto = passwordRepeat,
                cambioTexto = { passwordRepeat = it },
                marcador = "passwordRepeat",
                passwordRepeat.length > 6
            )
        }
        item {
            editText(
                texto = calle,
                cambioTexto = { calle = it },
                marcador = "calle",
                false
            )
        }
        item {
            editText(
                texto = cp,
                cambioTexto = { cp = it },
                marcador = "cp",
                false
            )
        }
        item {
            editText(
                texto = numero,
                cambioTexto = { numero = it },
                marcador = "numero",
                false
            )
        }
        item {
            editText(
                texto = ciudad,
                cambioTexto = { ciudad = it },
                marcador = "ciudad",
                false
            )
        }
        item {
            editText(
                texto = municipio,
                cambioTexto = { municipio = it },
                marcador = "municipio",
                false
            )
        }
        item {
            editText(
                texto = provincia,
                cambioTexto = { provincia = it },
                marcador = "provincia",
                false
            )
        }
        item {
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            val usuario = registrarUsuario(
                                UsuarioRegisterDTO(
                                    username = username,
                                    email = email,
                                    rol = "USER",
                                    password = password,
                                    passwordRepeat = passwordRepeat,
                                    direccion = Direccion(
                                        calle = calle,
                                        cp = cp,
                                        num = numero,
                                        ciudad = ciudad,
                                        municipio = municipio,
                                        provincia = provincia
                                    )
                                )
                            )

                            titulo = "Éxito"
                            mensaje = usuario?.let {
                                "${it.username}\n${it.email}\n${it.rol}"
                            } ?: "El usuario no se pudo recuperar"
                        } catch (e: Exception) {
                            titulo = "Error"
                            mensaje = "Error al registrar usuario: ${e.message}"
                        }
                        estadoventana = true
                    }



                }
            ) {
                texto("registrarse")
            }
        }
        item {
            Button(onClick = {
                navControlador.navigate("login")
            }) { texto("logearse") }
        }
    }
}

private suspend fun registrarUsuario(usuario: UsuarioRegisterDTO): UsuarioDTO? {
    var usuario2: UsuarioDTO? = null
    return withContext(Dispatchers.IO) {
        val prueba = RetrofitClient.instance.registrarUsuario(
            usuario
        )
        if (prueba.isSuccessful) {
            usuario2 = prueba.body()
            Log.i("saliBien", prueba.body().toString())
            return@withContext usuario2
        } else {
            val errorBody = prueba.errorBody()?.string() ?: "Error desconocido"
            val mensaje = Gson().fromJson(errorBody, ErrorRespuesta::class.java)
            Log.i("salioMal", prueba.code().toString())
            Log.i("cuerpoMal", errorBody)
            throw Exception(mensaje.message)
        }
    }
}

