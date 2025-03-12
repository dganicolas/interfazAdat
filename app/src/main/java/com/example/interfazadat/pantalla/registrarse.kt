package com.example.interfazadat.pantalla

import android.util.Log
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
import com.example.interfazadat.viewModel.UsuariosViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Registrarse(navControlador: NavHostController, usuarioViewModel: UsuariosViewModel) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var calle by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var cp by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var municipio by remember { mutableStateOf("") }
    var provincia by remember { mutableStateOf("") }
    var titulo by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }
    var estadoventana by remember { mutableStateOf(false) }
    var estadoVentanaCorrecta by remember { mutableStateOf(false) }
    if (estadoventana) {
        ventana(mensaje, titulo, { estadoventana = false })
    }
    if (estadoVentanaCorrecta) {
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
        // los diferentes campos de textos
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
                calle.isNotBlank()
            )
        }
        item {
            editText(
                texto = cp,
                cambioTexto = { cp = it },
                marcador = "cp",
                cp.isNotBlank()
            )
        }
        item {
            editText(
                texto = numero,
                cambioTexto = { numero = it },
                marcador = "numero",
                numero.isNotBlank()
            )
        }
        item {
            editText(
                texto = ciudad,
                cambioTexto = { ciudad = it },
                marcador = "ciudad",
                ciudad.isNotBlank()
            )
        }
        item {
            editText(
                texto = municipio,
                cambioTexto = { municipio = it },
                marcador = "municipio",
                municipio.isNotBlank()
            )
        }
        item {
            editText(
                texto = provincia,
                cambioTexto = { provincia = it },
                marcador = "provincia",
                provincia.isNotBlank()
            )
        }
        //icono de carga para que el usuario no piense que esta pilla la interfaz
        item {
            Box() {
                if (cargando) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
        // boton que compruebo que todo este relleno o no este cargando una solicitud para que el ususario no sobresature la api
        item {
            Button(
                enabled = !cargando && username.isNotBlank() && email.isNotBlank()
                        && password.isNotBlank() && passwordRepeat.isNotBlank()
                        && calle.isNotBlank() && numero.isNotBlank()
                        && cp.isNotBlank() && ciudad.isNotBlank()
                        && municipio.isNotBlank() && provincia.isNotBlank(),
                onClick = {
                    cargando = true
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            //enviamos la peticion a la pai
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
                            //miramos lo que nos devuelve
                            Log.i("msg_src", responseUsuarioRegister.toString())
                            //si la operacion es fallida, entonces devolvermos un error
                            if (!responseUsuarioRegister.isSuccessful) {
                                val errorMessage = responseUsuarioRegister.errorBody()?.string()
                                    ?: "Error desconocido"
                                throw Exception("Error al registrar usuario: ${responseUsuarioRegister.code()} - $errorMessage")
                            }
                            //recogemos el usuario que nos ha devuelto la api
                            val usuario = responseUsuarioRegister.body()

                            //configuramos el mensaje y titulo de la ventana
                            titulo = "Éxito"
                            mensaje = usuario?.let {
                                "${it.username}\n${it.email}\n${it.rol}"
                            } ?: "El usuario no se pudo recuperar"
                            estadoVentanaCorrecta = true
                        } catch (e: Exception) {
                            //lanzamos una ventana error
                            titulo = "Error"
                            mensaje = "Error al registrar usuario: ${e.message}"
                        }
                        estadoventana = true
                        cargando = false
                    }


                }
            ) {
                texto("registrarse")
            }
        }
        //boton de volver a pantalla de login
        item {
            Button(
                enabled = !cargando,
                onClick = {
                    navControlador.navigate("login")
                }) { texto("logearse") }
        }
    }
}

