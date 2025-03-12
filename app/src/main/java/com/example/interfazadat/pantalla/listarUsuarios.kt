package com.example.interfazadat.pantalla

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.interfazadat.Dto.UsuarioDTO
import com.example.interfazadat.viewModel.UsuariosViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun listarUsuarios(
    navControlador: NavHostController,
    usuariosViewmodel: UsuariosViewModel,
    modifier: Modifier
) {
    val usuarios = usuariosViewmodel.usuarios.observeAsState(emptyList())
    val context= LocalContext.current
    LaunchedEffect(Unit) { usuariosViewmodel.listarUsuarios(context) }
    // Si no hay usuarios, mostramos un mensaje de carga o vacío
    Column(modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        if (usuariosViewmodel.cargando) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        Button(
            onClick = {
                navControlador.navigate("menu") // Volver a la pantalla anterior después de crear la tarea
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            Text("volver")
        }
        //si la lista esta vaica muestro un mensaje de no hay usuarios disponibles y si no la lista de usuarios
        if (usuarios.value.isEmpty()) {
            Text(text = "No hay usuarios disponibles.")
        } else {
            Spacer(Modifier.padding(10.dp))
            // Mostramos la lista de usuarios en una LazyColumn
            LazyColumn(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                items(usuarios.value) { usuario ->
                    // Aquí puedes crear un item para cada usuario
                    UsuarioItem(usuario = usuario, onClick = {
                        usuariosViewmodel.eliminarusuario(context,it)
                    })
                }
            }
        }
    }
}

@Composable
fun UsuarioItem(usuario: UsuarioDTO, onClick: (String) -> Unit) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth(0.93f)
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(16.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            //muestro toda la info del usuario
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Icono de usuario",
                    tint = Color.Black
                )
                Text(text = "${usuario.username}",fontWeight = FontWeight.Bold, fontSize = 23.sp)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Icono de usuario",
                    tint = Color.Black
                )
                Text(text = "${usuario.email}",fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Icono de usuario",
                    tint = Color.Black
                )
                Text(text = "rol: ${usuario.rol}",fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

        }
        Button(
            onClick = { mostrarDialogo = true },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Eliminar")
        }
        //muestro un alert que el usuario confirma esa eliminacion
        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false }, // Cerrar el diálogo si se hace clic fuera de él
                title = { Text(text = "Confirmar eliminación") },
                text = { Text(text = "¿Estás seguro de eliminar al usuario ${usuario.username}?") },
                //elimina el usuario
                confirmButton = {
                    TextButton(onClick = {
                        onClick(usuario.username) // Llamar a la función de eliminación
                        mostrarDialogo = false // Cerrar el diálogo
                    }) {
                        Text("Eliminar")
                    }
                },
                //el usuario no hace nada
                dismissButton = {
                    TextButton(onClick = { mostrarDialogo = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}



