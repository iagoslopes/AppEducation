package iago.slopes.appeducation.api

import iago.slopes.appeducation.models.Terreno
import retrofit2.Call
import retrofit2.http.*

interface ServicoTerreno {
    @GET("terrenos")
    fun getAllContatos(): Call<List<Terreno>>
}