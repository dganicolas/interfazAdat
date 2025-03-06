package com.example.interfazadat.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.interfazadat.utils.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarConMenu(selectedItem:String,onMenuItemClick: (String) -> Unit) {
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { texto("Mi Aplicación") },
        navigationIcon = {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menú")
            }
        },
        actions = {
            Spacer(modifier = Modifier.weight(1f))
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { texto("crear tarea", color = if (selectedItem == "crear tarea") Color.Black else Color.Gray) },
                    onClick = {
                        menuExpanded = false
                        onMenuItemClick("crear tareas")
                    })
                DropdownMenuItem(
                    text = { texto("Mi zona", color = if (selectedItem == "usuarios") Color.Black else Color.Gray) },
                    onClick = {
                    menuExpanded = false
                    onMenuItemClick("usuarios")
                })
                if(Usuario.rol == true){
                    DropdownMenuItem(
                        text = { texto("dar de alta a usuario", color = if (selectedItem == "administracion") Color.Black else Color.Gray) },
                        onClick = {
                            menuExpanded = false
                            onMenuItemClick("alta")
                        })
                    DropdownMenuItem(
                        text = { texto("listar usuarios", color = if (selectedItem == "administracion") Color.Black else Color.Gray) },
                        onClick = {
                            menuExpanded = false
                            onMenuItemClick("admin")
                        })
                }
            }
        }
    )
}