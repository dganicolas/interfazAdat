package com.example.interfazadat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.interfazadat.pantalla.Bienvenida
import com.example.interfazadat.pantalla.Login
import com.example.interfazadat.pantalla.Registrarse

@Composable
fun AppNavigation(modifier: Modifier) {
    val navControlador = rememberNavController()
    NavHost(navController = navControlador, startDestination = "registrarse") {
        composable("bienvenida") {
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
            Registrarse(navControlador)
        }
    }
}