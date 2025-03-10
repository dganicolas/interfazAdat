package com.example.interfazadat.pantalla

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.interfazadat.Dto.UsuarioRegisterDTO
import com.example.interfazadat.model.Direccion
import com.example.interfazadat.apiservice.RetrofitClient
import com.example.interfazadat.componentes.editText
import com.example.interfazadat.componentes.texto
import com.example.interfazadat.componentes.ventana
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    var cargando by remember { mutableStateOf(false) }
    var estadoventana by remember { mutableStateOf(false) }
    var estadoVentanaCorrecta by remember { mutableStateOf(false) }
    if (estadoventana) {
        ventana(mensaje, titulo, { estadoventana = false })
    }
    if(estadoVentanaCorrecta){
        ventana(mensaje, titulo) { navControlador.navigate("login") }
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
            Box(){
                if (cargando) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
        item {
            Button(
                enabled = !cargando,
                onClick = {
                    cargando = true
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            val responseUsuarioRegister = RetrofitClient.instance.registrarUsuario(
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
                            if (!responseUsuarioRegister.isSuccessful) {
                                val errorMessage = responseUsuarioRegister.errorBody()?.string() ?: "Error desconocido"
                                throw Exception("Error al registrar usuario: ${responseUsuarioRegister.code()} - $errorMessage")
                            }
                            val usuario =responseUsuarioRegister.body()

                            titulo = "Éxito"
                            mensaje = usuario?.let {
                                "${it.username}\n${it.email}\n${it.rol}"
                            } ?: "El usuario no se pudo recuperar"
                            estadoVentanaCorrecta = true
                        } catch (e: Exception) {
                            titulo = "Error"
                            mensaje = "Error al registrar usuario: ${e.message}"
                        }
                        estadoventana = true
                    }
                    cargando = false

                }
            ) {
                texto("registrarse")
            }
        }
        item {
            Button(
                enabled = !cargando,
                onClick = {
                navControlador.navigate("login")
            }) { texto("logearse") }
        }
    }
}

