package iago.slopes.appeducation.api

import iago.slopes.appeducation.models.Planta
import retrofit2.Call
import retrofit2.http.*

interface ServicoPlanta {
    @GET("plantas/{id}")
    fun getPlanta(@Path("id") id: Int): Call<Planta>
    @GET("plantas")
    fun getAllPlantas(): Call<List<Planta>>
}