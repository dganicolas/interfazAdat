package com.example.interfazadat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.interfazadat.pantalla.Bienvenida
import com.example.interfazadat.pantalla.Login
import com.example.interfazadat.pantalla.Menu
import com.example.interfazadat.pantalla.Registrarse
import com.example.interfazadat.pantalla.crearTarea
import com.example.interfazadat.pantalla.darDeAltaScreen
import com.example.interfazadat.pantalla.listarUsuarios
import com.example.interfazadat.pantalla.miZona
import com.example.interfazadat.viewModel.TareasViewModel
import com.example.interfazadat.viewModel.UsuariosViewModel

@Composable
fun AppNavigation(modifier: Modifier) {
    val navControlador = rememberNavController()
    val  tareasViewModel = TareasViewModel()
    val usuariosViewmodel = UsuariosViewModel()
    NavHost(navController = navControlador, startDestination = "login") {
        composable("menu") {
            Bienvenida(navControlador)
        }
        composable(
            "login"
        ) {
            Login(navControlador)
        }
        composable(
            "registrarse"
        ) {
            Registrarse(navControlador,usuariosViewmodel)
        }
        composable(
            "menu"
        ) {
            Menu(navControlador,tareasViewModel)
        }
        composable("crear tarea"){
            crearTarea(navControlador,tareasViewModel,modifier)
        }
        composable("darAlta") {
            darDeAltaScreen(navControlador,usuariosViewmodel)
        }
        composable("mi zona") {
            miZona(navControlador,usuariosViewmodel,modifier)
        }
        composable("listarusarios") {
            listarUsuarios(navControlador,usuariosViewmodel,modifier)
        }
    }
}