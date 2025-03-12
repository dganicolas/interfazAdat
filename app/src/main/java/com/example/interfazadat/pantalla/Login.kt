package com.example.interfazadat.pantalla

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.interfazadat.Dto.LoginUsuarioDTO
import com.example.interfazadat.R
import com.example.interfazadat.apiservice.RetrofitClient
import com.example.interfazadat.componentes.editText
import com.example.interfazadat.componentes.texto
import com.example.interfazadat.componentes.ventana
import com.example.interfazadat.utils.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

@Composable
fun Login(navControlador: NavHostController) {
    var username by remember { mutableStateOf("userAdmin") }
    var password by remember { mutableStateOf("contrasenaSegura123") }
    var titulo by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }
    var estadoBoton by remember { mutableStateOf(true) }
    val current = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //imagen
        Image(
            painter = painterResource(id = R.drawable.hacking_etico_jpg),
            contentDescription = "Descripción de la imagen",

        )
        //icono de cuando carga la ui
        if (cargando) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        texto("entrar a la app")
        //campos de textos del login
        editText(
            texto = username,
            cambioTexto = { username = it },
            marcador = "username",
            username.length > 3
        )
        editText(
            texto = password,
            cambioTexto = { password = it },
            marcador = "contraseña",
            password.length >= 6
        )
        Button(
            enabled = username.isNotBlank() && password.isNotBlank() && estadoBoton,
            onClick = {
                //lanzo la peticion
                cargando = true
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        estadoBoton = false
                        val usuario = RetrofitClient.instance.loginUsuario(
                            LoginUsuarioDTO(username = username, password = password)
                        )
                        Log.i("msg_src", usuario.toString())
                        //si sale exitoso
                        if (usuario.isSuccessful) {
                            titulo = "Éxito"
                            Usuario.token = ("bearer " + usuario.body()?.get("token"))
                            Log.i("msg_src",Usuario.token)
                            Usuario.nombre = username
                            Usuario.contrasena = password
                            Usuario.email = RetrofitClient.instance.obtenerEmail(
                                Usuario.token
                            ).body()?.get("mensaje")
                        }
                        //si sale exitoso igualo el token y reviso que no esta vacio
                        if (Usuario.token == "") {
                            throw Exception(usuario.errorBody()?.string() ?:"" )
                        }
                        val esAdmin = RetrofitClient.instance.esAdmin(Usuario.token).body()
                        Usuario.rol = esAdmin
                        Usuario.nombre = username
                        //muestro un toast de que todo vaya bien
                        withContext(Dispatchers.Main) {
                            Toast.makeText(current, "sesion iniciada", Toast.LENGTH_SHORT)
                                .show()
                            navControlador.navigate("menu")
                        }

                    } catch (e: Exception) {
                        //si sale un error se lo muestro al usuario por un toast
                        estadoBoton = true
                        cargando=false
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                current,
                                "error al iniciar sesion ${e.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }

                }
            }
        ) {
            texto("iniciar sesion")
        }
        //boton que navega a registrarse
        Button(
            enabled = estadoBoton,
            onClick = {
                navControlador.navigate("registrarse")
            }) { texto("registrarse") }
    }
}


@Composable
@Preview
fun previewLogin() {
    Login(rememberNavController())
}