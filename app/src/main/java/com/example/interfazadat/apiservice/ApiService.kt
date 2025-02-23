package com.example.interfazadat.apiservice
import com.example.interfazadat.Dto.LoginUsuarioDTO
import com.example.interfazadat.Dto.UsuarioDTO
import com.example.interfazadat.Dto.UsuarioRegisterDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("usuarios/registrarse")
    suspend fun registrarUsuario(@Body usuario: UsuarioRegisterDTO): Response<UsuarioDTO?>

    @POST("usuarios/acceder")
    suspend fun loginUsuario(@Body usuario: LoginUsuarioDTO): Response<Map<String, String>>
}