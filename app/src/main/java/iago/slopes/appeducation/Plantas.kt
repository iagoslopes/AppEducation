package iago.slopes.appeducation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iago.slopes.appeducation.adapters.AdapterPlantas
import iago.slopes.appeducation.api.RetrofitClient
import iago.slopes.appeducation.models.Planta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Plantas : AppCompatActivity() {
    lateinit var recyclerPlantas:RecyclerView;
    lateinit var searchView: SearchView;
    lateinit var btnMenu: FloatingActionButton;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plantas)
        recyclerPlantas = findViewById(R.id.recyclerPlantas)
        btnMenu = findViewById(R.id.fabVoltarMenu)

        recyclerPlantas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        searchView = findViewById(R.id.searchView)

        btnMenu.setOnClickListener {
            voltarMenu()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Não é necessário lidar com a submissão no momento
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val adapter = recyclerPlantas.adapter as AdapterPlantas
                adapter.filter.filter(newText)
                return true
            }
        })
        var retrofitCli:RetrofitClient = RetrofitClient()
        retrofitCli.plantaService.getAllContatos().enqueue(
            object:Callback<List<Planta>>{
                override fun onResponse(
                    call: Call<List<Planta>>,
                    response: Response<List<Planta>>
                ) {
                    if (response.body()!=null){
                        var adapter:AdapterPlantas=
                            AdapterPlantas(this@Plantas,response.body()!!)
                        recyclerPlantas.adapter = adapter
                    }
                }
                override fun onFailure(call: Call<List<Planta>>, t: Throwable) {
                    Log.e("API","onFailure: Falha ao carregar plantas", t)
                }
            }
        )
    }
    private fun voltarMenu() {
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }
}