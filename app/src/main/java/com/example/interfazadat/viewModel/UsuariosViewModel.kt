package com.example.interfazadat.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.es.aplicacion.dto.UsuarioActualizarDto
import com.example.interfazadat.Dto.UsuarioDTO
import com.example.interfazadat.Dto.UsuarioRegisterDTO
import com.example.interfazadat.apiservice.RetrofitClient
import com.example.interfazadat.utils.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuariosViewModel() : ViewModel() {

    val _usuarios = MutableLiveData<List<UsuarioDTO>>(emptyList())
    val usuarios:LiveData<List<UsuarioDTO>> = _usuarios

    var cargando by mutableStateOf(false)

    fun listarUsuarios(current: Context){
        viewModelScope.launch {
            cargando =true
            try {
                val responseUsuarioRegister =
                    RetrofitClient.instance.listarUsuarios(Usuario.token)
                Log.i("msg_src", responseUsuarioRegister.toString())
                if (!responseUsuarioRegister.isSuccessful) {
                    val errorMessage =
                        responseUsuarioRegister.errorBody()?.string() ?: "Error desconocido"
                    throw Exception(errorMessage)
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        current,
                        "datos cargados",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                _usuarios.value = responseUsuarioRegister.body()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(current, "${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        cargando = false
    }

    fun darDeAlta(usuarioRegisterDTO: UsuarioRegisterDTO, current: Context) {
        viewModelScope.launch {
            try {
                val responseUsuarioRegister =
                    RetrofitClient.instance.registrarUsuario(usuarioRegisterDTO)
                Log.i("msg_src", responseUsuarioRegister.toString())
                if (!responseUsuarioRegister.isSuccessful) {
                    val errorMessage =
                        responseUsuarioRegister.errorBody()?.string() ?: "Error desconocido"
                    throw Exception(errorMessage)
                }
                val usuario = responseUsuarioRegister.body()

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        current,
                        "usuario creado ${usuario?.username ?: ""}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(current, "${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun actualizarUsuario(email: String, contrasena: String?, current: Context) {
        cargando = true
        viewModelScope.launch {
            try {
                val responseUsuarioRegister = RetrofitClient.instance.actualizarUsuario(
                    Usuario.nombre!!, Usuario.token, UsuarioActualizarDto(
                        email,
                        contrasena!!
                    )
                )
                Log.i("msg_src", responseUsuarioRegister.toString())
                if (!responseUsuarioRegister.isSuccessful) {
                    val errorMessage =
                        responseUsuarioRegister.errorBody()?.string() ?: "Error desconocido"
                    throw Exception("Error al registrar usuario: ${responseUsuarioRegister.code()} - $errorMessage")
                }
                val usuario = responseUsuarioRegister.body()?.get("mensaje")

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        current,
                        "usuario actualizado ${usuario ?: ""}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(current, "error al dar de alta $e", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        cargando = false
    }

    fun eliminarCuenta(current: Context, navControlador: NavHostController) {
        cargando = true
        viewModelScope.launch {
            try {
                val responseUsuarioRegister = RetrofitClient.instance.eliminarUsuario(
                    Usuario.nombre!!,
                    Usuario.token
                )
                Log.i("msg_src", responseUsuarioRegister.toString())
                if (!responseUsuarioRegister.isSuccessful) {
                    val errorMessage =
                        responseUsuarioRegister.errorBody()?.string() ?: "Error desconocido"
                    throw Exception("Error al eliminar el usuario: ${responseUsuarioRegister.code()} - $errorMessage")
                }
                val usuario = responseUsuarioRegister.body()?.get("mensaje")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        current,
                        "usuario eliminado ${usuario ?: ""}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                Usuario.token = ""
                navControlador.navigate("login")
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(current, "error al eliminar $e", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        cargando = false
    }

    fun eliminarusuario(current: Context, username:String) {
        cargando = true
        viewModelScope.launch {
            try {
                val responseUsuarioRegister = RetrofitClient.instance.eliminarUsuario(
                    username,
                    Usuario.token
                )
                Log.i("msg_src", responseUsuarioRegister.toString())
                if (!responseUsuarioRegister.isSuccessful) {
                    val errorMessage =
                        responseUsuarioRegister.errorBody()?.string() ?: "Error desconocido"
                    Log.i("msg_src",errorMessage)
                    throw Exception("Error al eliminar el usuario: ${responseUsuarioRegister.code()} - $errorMessage")
                }
                val usuario = responseUsuarioRegister.body()?.get("mensaje")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        current,
                        "usuario eliminado ${usuario ?: ""}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                listarUsuarios(current)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(current, "error al eliminar $e", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        cargando = false
    }
}