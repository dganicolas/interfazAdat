package com.example.interfazadat.apiservice

import com.es.aplicacion.dto.CreateTaskDto
import com.es.aplicacion.dto.UsuarioActualizarDto
import com.example.interfazadat.Dto.LoginUsuarioDTO
import com.example.interfazadat.Dto.UsuarioDTO
import com.example.interfazadat.Dto.UsuarioRegisterDTO
import com.example.interfazadat.model.Tarea
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("usuarios/registrarse")
    suspend fun registrarUsuario(@Body usuario: UsuarioRegisterDTO): Response<UsuarioDTO?>

    @POST("usuarios/acceder")
    suspend fun loginUsuario(@Body usuario: LoginUsuarioDTO):
            Response<Map<String, String>>

    @DELETE("usuarios/eliminarUsuario/{username}")
    suspend fun eliminarUsuario(
        @Path("username") usuarioABorrrar: String,
        @Header("Authorization") token: String,
    ):
            Response<Map<String,String>>

    @PUT("usuarios/actualizarUsuario/{username}")
    suspend fun actualizarUsuario(
        @Path("username") usuario: String,
        @Header("Authorization") authentication: String,
        @Body nuevoUsuario: UsuarioActualizarDto,
    ):
            Response<Map<String,String>>

    @GET("usuarios/listarusuarios")
    suspend fun listarUsuarios(@Header("Authorization") token: String):
            Response<List<UsuarioDTO>>

    @GET("usuarios/esadmin")
    suspend fun esAdmin(@Header("Authorization") token: String):
            Response<Boolean>

    @POST("tareas/crear")
    suspend fun crearTarea(
        @Header("Authorization") authentication: String,
        @Body tarea: CreateTaskDto,
    ):Response<CreateTaskDto>

    @PUT("tareas/actualizarEstadoTarea/{id}")
    suspend fun actualizarEstadoTarea(
        @Path("id") id: String,
        @Header("Authorization") authentication: String,
    ):
            Response<Map<String,String>>

    @GET("tareas/listarTodasLasTareas")
    suspend fun listarTodasLasTareas(@Header("Authorization") authentication: String):
            Response<MutableList<Tarea>>

    @DELETE("tareas/eliminar/{id}")
    suspend fun eliminartarea(
        @Path("id") id: String,
        @Header("Authorization") authentication: String,
    ):
            Response<Map<String,String>>

    @PUT("tareas/actualizartarea")
    suspend fun actualizarTarea(
        @Body tarea: Tarea,
        @Header("Authorization") authentication: String,
    ):
            Response<Map<String,String>>
    @GET("tareas/tareaporid/{id}")
    suspend fun tareaporid(@Path("id") id:String,@Header("Authorization") authentication: String):Response<Tarea>

    @GET("usuarios/obteneremail")
    suspend fun obtenerEmail(@Header("Authorization") authentication: String): Response<Map<String, String>>
}