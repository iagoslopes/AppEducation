package iago.slopes.appeducation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iago.slopes.appeducation.adapters.AdapterPragas
import iago.slopes.appeducation.api.RetrofitClient
import iago.slopes.appeducation.models.Praga
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Pragas : AppCompatActivity() {
    lateinit var recyclerPragas: RecyclerView;
    lateinit var searchView: SearchView;
    lateinit var btnMenu: FloatingActionButton;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pragas)
        recyclerPragas = findViewById(R.id.recyclerPragas)
        btnMenu = findViewById(R.id.fabVoltarMenu)

        recyclerPragas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

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
                val adapter = recyclerPragas.adapter as AdapterPragas
                adapter.filter.filter(newText)
                return true
            }
        })
        var retrofitCli: RetrofitClient = RetrofitClient()
        retrofitCli.pragaService.getAllPragas().enqueue(
            object: Callback<List<Praga>> {
                override fun onResponse(
                    call: Call<List<Praga>>,
                    response: Response<List<Praga>>
                ) {
                    if (response.body()!=null){
                        var adapter: AdapterPragas =
                            AdapterPragas(this@Pragas,response.body()!!)

                        adapter.setOnItemClickListener(object : AdapterPragas.OnItemClickListener {
                            override fun onItemClick(praga: Praga) {
                                val intent = Intent(this@Pragas, DetalhesPragaActivity::class.java)
                                intent.putExtra("praga", praga)
                                startActivity(intent)
                            }
                        })

                        recyclerPragas.adapter = adapter
                    }
                }
                override fun onFailure(call: Call<List<Praga>>, t: Throwable) {
                    Log.e("API","onFailure: Falha ao carregar pragas", t)
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