package com.example.interfazadat.pantalla

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.interfazadat.Dto.LoginUsuarioDTO
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
fun Login(navControlador: NavHostController) {
    var username by remember { mutableStateOf("nico") }
    var password by remember { mutableStateOf("nico") }
    var titulo by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var estadoventana by remember { mutableStateOf(false) }
    if (estadoventana) {
        ventana(mensaje, titulo, { estadoventana = false })
    }
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        texto("entrar a la app")
        editText(
            texto = username,
            cambioTexto = { username = it },
            marcador = "username",
            username.length > 3
        )
        editText(
            texto = password,
            cambioTexto = { password = it },
            marcador = "username",
            password.length > 3
        )
        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val usuario = loginUsuario(
                            LoginUsuarioDTO(
                                username = username,
                                password = password,
                            )
                        )

                        titulo = "Éxito"
                        mensaje = usuario?.let {
                            "${it["token"]}"
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
            Button(onClick = {
                navControlador.navigate("registrarse")
            }) { texto("registrarse") }
    }
}
private suspend fun loginUsuario(usuario: LoginUsuarioDTO): Map<String,String>? {
    return withContext(Dispatchers.IO) {
        val prueba = RetrofitClient.instance.loginUsuario(
            usuario
        )
        if (prueba.isSuccessful) {
            Log.i("saliBien", prueba.body().toString())
            return@withContext prueba.body()
        } else {
            val errorBody = prueba.errorBody()?.string() ?: "Error desconocido"
            Log.i("XXXXXXXXXXXXX",errorBody)
            val mensaje = Gson().fromJson(errorBody, ErrorRespuesta::class.java)
            Log.i("salioMal", prueba.code().toString())
            Log.i("cuerpoMal", errorBody)
            throw Exception(mensaje.message)
        }
    }
}

@Composable
@Preview
fun previewLogin(){
    Login(rememberNavController())
}