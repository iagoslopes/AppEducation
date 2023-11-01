package iago.slopes.appeducation.api

import iago.slopes.appeducation.models.Praga
import retrofit2.Call
import retrofit2.http.*

interface ServicoPraga {
    @GET("pragas")
    fun getAllPragas(): Call<List<Praga>>
}