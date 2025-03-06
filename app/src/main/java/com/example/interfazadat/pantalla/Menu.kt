package com.example.interfazadat.pantalla

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.interfazadat.componentes.AppBarConMenu
import com.example.interfazadat.componentes.texto
import com.example.interfazadat.model.Tarea
import com.example.interfazadat.utils.Usuario
import com.example.interfazadat.viewModel.TareasViewModel


@Composable
fun Menu(navControlador: NavHostController, tareasViewModel: TareasViewModel) {
    val tareas by tareasViewModel.tareas.observeAsState(emptyList())
    val context = LocalContext.current
    LaunchedEffect(true) {
        tareasViewModel.cargarTareas()
    }
    Scaffold(
        topBar = {
            AppBarConMenu("tareas") { selectedItem ->
                when (selectedItem) {
                    "alta" -> navControlador.navigate("darAlta")
                    "crear tareas" -> navControlador.navigate("crear tarea")
                    "usuarios" -> navControlador.navigate("mi zona")
                    "admin" -> navControlador.navigate("listarusarios")
                }
            }
        }
    ) { margen ->
        Column(
            Modifier
                .padding(margen)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LaunchedEffect(true) {
                tareasViewModel.cargarTareas()
            }
            // Mostrar un círculo de carga si estamos cargando las tareas
            if (tareasViewModel.loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                // Si ya hemos cargado las tareas, mostramos la lista
                if(tareas != null){

                        LazyColumn {
                            items(tareas!!) { tarea ->
                                TareaItem(tarea = tarea,{ tareaId ->
                                    tareasViewModel.eliminarTarea(tareaId,context)
                                },{estado->
                                    tareasViewModel.completarTarea(estado,context)
                                },
                                    {idTarea,nombre,descripcion,estado,autor->
                                        tareasViewModel.actualizarTarea(Tarea(idTarea,nombre,descripcion,estado,autor),context)

                                    })
                            }
                        }

                }else{
                    Text("no hay tareas")
                }

            }
            // Mostrar mensaje de error si hay uno
            if (tareasViewModel.errorMessage.isNotEmpty()) {
                Text(
                    text = tareasViewModel.errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
                Button(
                    onClick = { tareasViewModel.cargarTareas() }
                ) {
                    texto("reintentar")
                }
            }
        }
    }
}

@Composable
fun TareaItem(tarea: Tarea, onEliminar: (String) -> Unit,onCompletadaChange: (String) -> Unit,onEditarTarea: (String, String, String, Boolean, String) -> Unit) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var mostrarDialogoEdicion by remember { mutableStateOf(false) }
    var nuevoNombre by remember { mutableStateOf(tarea.nombre) }
    var nuevaDescripcion by remember { mutableStateOf(tarea.descripcion) }
    var nuevoAutor by remember { mutableStateOf(tarea.autor) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "ID: ${tarea._id}")
            Text(text = "Nombre: ${tarea.nombre}")
            Text(text = "Descripción: ${tarea.descripcion}")
            Text(text = "Autor: ${tarea.autor}")
            Checkbox(
                checked = tarea.estado, // Aquí asumimos que 'estado' es un campo Booleano que indica si la tarea está completada
                onCheckedChange = { isChecked ->
                    // Llamamos a onCompletadaChange para actualizar el estado de la tarea en la UI
                    onCompletadaChange(tarea._id ?: "")
                }
            )
            Button(
                onClick = { mostrarDialogoEdicion = true },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = "Editar tarea")
            }
            Spacer(Modifier.padding(5.dp))
            Button(
                onClick = { mostrarDialogo = true },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Eliminar tarea")
            }

        }
    }

    if (mostrarDialogo) {
        ConfirmDialog(
            onConfirm = {
                onEliminar(tarea._id ?: "")
                mostrarDialogo = false
            },
            onDismiss = { mostrarDialogo = false }
        )
    }
    if (mostrarDialogoEdicion) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEdicion = false },
            title = { Text(text = "Editar tarea") },
            text = {
                Column {
                    Text(text = "Nombre:")
                    TextField(
                        value = nuevoNombre,
                        onValueChange = { nuevoNombre = it },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Descripción:")
                    TextField(
                        value = nuevaDescripcion,
                        onValueChange = { nuevaDescripcion = it },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Text(text = "Autor:")
                    TextField(
                        value = nuevoAutor,
                        onValueChange = { nuevoAutor = it },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = Usuario.rol!!
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Llamar al onEditarTarea para actualizar la tarea con los nuevos valores
                        onEditarTarea(tarea._id ?: "", nuevoNombre, nuevaDescripcion,tarea.estado,nuevoAutor)
                        mostrarDialogoEdicion = false
                    }
                ) {
                    Text("Guardar cambios")
                }
            },
            dismissButton = {
                Button(onClick = { mostrarDialogoEdicion = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun ConfirmDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "¿Confirmar eliminación?") },
        text = { Text("¿Seguro que quieres eliminar esta tarea?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}


