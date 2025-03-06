package com.example.interfazadat.pantalla

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.interfazadat.utils.Usuario
import com.example.interfazadat.viewModel.UsuariosViewModel

@Composable
fun miZona(navControlador: NavHostController, usuariosViewmodel: UsuariosViewModel) {
    var email by remember { mutableStateOf(Usuario.email ?: "") }
    var contrasena by remember { mutableStateOf(Usuario.contrasena ?: "") }
    var context = LocalContext.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        if (usuariosViewmodel.cargando) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Button(
            onClick = {
                navControlador.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "volver")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Mi Zona")

        Spacer(modifier = Modifier.height(16.dp))
        Text("nombre ${Usuario.nombre}")
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para email
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para contraseña
        TextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(if(Usuario.rol == true)"administrador" else "usuario")

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para guardar los cambios
        Button(
            onClick = {
                usuariosViewmodel.actualizarUsuario(email,contrasena,context)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar Cambios")
        }
        Button(
            onClick = {
                usuariosViewmodel.eliminarCuenta(context,navControlador)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "eliminar cuenta")
        }
    }
}