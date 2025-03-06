package com.example.interfazadat.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.es.aplicacion.dto.CreateTaskDto
import com.example.interfazadat.apiservice.RetrofitClient
import com.example.interfazadat.model.Tarea
import com.example.interfazadat.utils.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext

class TareasViewModel : ViewModel() {
    private val _tareas = MutableLiveData<MutableList<Tarea>?>()

    // LiveData para acceder a la lista de tareas de forma segura (solo lectura)
    val tareas: LiveData<MutableList<Tarea>?> = _tareas
    var loading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf("")
        private set
    var eliminacionExitosa by mutableStateOf(false)
    var tarea by mutableStateOf<Tarea?>(null)
    var mensajeCrearTarea by mutableStateOf("")

    // Función para cargar las tareas
    fun cargarTareas() {
        loading = true
        errorMessage = ""
        viewModelScope.launch {
            try {
                Log.i("eeeeeeeeeeeeeeeeeeeeeeeeee", Usuario.token)
                val response = RetrofitClient.instance
                    .listarTodasLasTareas(Usuario.token).body()
                Log.i("xxxxxxxxxxxxx", response.toString())
                if (response != null) {
                    _tareas.value = response
                } else {
                    errorMessage = "No se encontraron tareas"
                }
            } catch (e: TimeoutCancellationException) {
                errorMessage = "El tiempo de espera se ha agotado. Intenta nuevamente."
            } catch (e: Exception) {
                errorMessage = "Error al cargar las tareas: ${e.localizedMessage}"
            } finally {
                loading = false
            }
        }
    }

    fun guardarTarea(tareaId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.tareaporid(tareaId, Usuario.token)
                if (response.isSuccessful) {
                    tarea = response.body()
                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                Log.e("Error", "Error al eliminar tarea: ${e.message}")
            }
        }
    }

    fun eliminarTarea(id: String, current: Context) {

        Log.i("xxxxxxxx", tarea.toString())
        viewModelScope.launch {
            try {
                guardarTarea(id)
                val response =
                    RetrofitClient.instance.eliminartarea(id, Usuario.token).body()?.get("mensaje")
                if (response != null) {
                    cargarTareas()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(current, "Tarea eliminada ${response}", Toast.LENGTH_SHORT)
                            .show()
                    }
                    Log.i("eliminada la tarea", "la tarea ${tarea?.nombre}")
                } else {
                    eliminacionExitosa = false
                }

            } catch (e: Exception) {
                eliminacionExitosa = false
                withContext(Dispatchers.Main) {
                    Toast.makeText(current, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
                Log.e("Error", "Error al eliminar tarea: ${e.message}")
            }
        }
    }

    fun completarTarea(id: String, current: Context) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.instance.actualizarEstadoTarea(id, Usuario.token).body()
                        ?.get("mensaje")
                if (response != null) {
                    cargarTareas()
                    withContext(Dispatchers.Main) {
                    Toast.makeText(current, "Tarea eliminada ${response}", Toast.LENGTH_SHORT)
                        }
                        .show()
                } else {
                    eliminacionExitosa = false
                }
            } catch (e: Exception) {
                eliminacionExitosa = false
                Log.e("Error", "Error al eliminar tarea: ${e.message}")
            }
        }
    }

    fun crearTarea(nuevaTarea: CreateTaskDto, current: Context, navControlador: NavHostController) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.crearTarea(Usuario.token, nuevaTarea)

                if (response.isSuccessful) {
                    mensajeCrearTarea = response.body()?.nombre.toString()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(current, "tarea creada $mensajeCrearTarea", Toast.LENGTH_SHORT)
                            .show()
                    }
                    navControlador.navigate("menu")
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(current, response.errorBody()?.string()!!, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(current, "$e", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun actualizarTarea(tarea: Tarea, current: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.actualizarTarea(tarea, Usuario.token).body()
                    ?.get("mensaje")
                withContext(Dispatchers.Main) {
                        Toast.makeText(current, "Tarea actualizada $response", Toast.LENGTH_SHORT)
                            .show()
                }
                cargarTareas()
            } catch (e: Exception) {
                cargarTareas()
                withContext(Dispatchers.Main) {
                    Toast.makeText(current, "ERROR tarea NO actualizada $e", Toast.LENGTH_SHORT)
                        .show()
                }
                Log.e("Error", "Error al actualizar tarea: ${e.message}")
            }

        }
    }
}