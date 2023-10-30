package iago.slopes.appeducation.api

import iago.slopes.appeducation.models.Planta
import retrofit2.Call
import retrofit2.http.*

interface ServicoPlanta {
    @GET("plantas")
    fun getAllContatos(): Call<List<Planta>>
}