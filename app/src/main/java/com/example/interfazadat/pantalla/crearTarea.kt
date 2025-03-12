package com.example.interfazadat.pantalla

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.es.aplicacion.dto.CreateTaskDto
import com.example.interfazadat.utils.Usuario
import com.example.interfazadat.viewModel.TareasViewModel
@Composable
fun crearTarea(
    navControlador: NavHostController,
    tareasViewModel: TareasViewModel,
    modifier: Modifier
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf(Usuario.nombre.toString()) }
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Spacer(Modifier.padding(15.dp))
        //campo de texto para el nombre de la tarea
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de la tarea") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Campo  de texto para la descripción de la tarea
        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción de la tarea") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        //campo de texto para el autor de la tarea
        //PD: si el usuario es rol user este campo no debe editarlo el propio usuario
        Usuario.rol?.let {
            TextField(
                value = autor,
                onValueChange = { autor = it },
                label = { Text("autor de la tarea") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                enabled = Usuario.rol!!
            )
        }

        // Botón para crear la tarea
        //PD: si el campo de texto tanto nombre, autory descripcion estan vaico este campo debe de estar inactivo
        //si le pulsa el usuario crea una tarea
        Button(
            onClick = {
                if (nombre.isNotEmpty() && descripcion.isNotEmpty()) {
                    val nuevaTarea = CreateTaskDto(
                        nombre = nombre,
                        descripcion = descripcion,
                        autor = autor // Este valor podría venir del usuario autenticado
                    )
                    tareasViewModel.crearTarea(nuevaTarea,context,navControlador)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            enabled = nombre.isNotEmpty() && descripcion.isNotEmpty() && autor.isNotEmpty()
        ) {
            Text("Crear tarea")
        }
        //boton para volver al menu principal
        Button(
            onClick = {
                navControlador.navigate("menu") // Volver a la pantalla anterior después de crear la tarea
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            Text("volver")
        }
    }
}