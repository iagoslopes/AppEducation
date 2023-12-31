package iago.slopes.appeducation.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val BASE_URL = "https://education-agro.onrender.com/"  // Substitua pela URL base

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val plantaService: ServicoPlanta = 	retrofit.create(ServicoPlanta::class.java)
    val terrenoService: ServicoTerreno = 	retrofit.create(ServicoTerreno::class.java)
    val pragaService: ServicoPraga = 	retrofit.create(ServicoPraga::class.java)
}