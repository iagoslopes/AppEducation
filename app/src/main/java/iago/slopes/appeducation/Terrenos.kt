package iago.slopes.appeducation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iago.slopes.appeducation.adapters.AdapterTerrenos
import iago.slopes.appeducation.api.RetrofitClient
import iago.slopes.appeducation.models.Terreno
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Terrenos : AppCompatActivity() {
    lateinit var recyclerTerrenos: RecyclerView;
    lateinit var searchView: SearchView;
    lateinit var btnMenu: FloatingActionButton;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terrenos)
        recyclerTerrenos = findViewById(R.id.recyclerTerrenos)
        btnMenu = findViewById(R.id.fabVoltarMenu)

        recyclerTerrenos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

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
                val adapter = recyclerTerrenos.adapter as AdapterTerrenos
                adapter.filter.filter(newText)
                return true
            }
        })
        var retrofitCli: RetrofitClient = RetrofitClient()
        retrofitCli.terrenoService.getAllTerrenos().enqueue(
            object: Callback<List<Terreno>> {
                override fun onResponse(
                    call: Call<List<Terreno>>,
                    response: Response<List<Terreno>>
                ) {
                    if (response.body()!=null){
                        var adapter: AdapterTerrenos =
                            AdapterTerrenos(this@Terrenos,response.body()!!)

                        adapter.setOnItemClickListener(object : AdapterTerrenos.OnItemClickListener {
                            override fun onItemClick(terreno: Terreno) {
                                val intent = Intent(this@Terrenos, DetalhesTerrenoActivity::class.java)
                                intent.putExtra("terreno", terreno)
                                startActivity(intent)
                            }
                        })

                        recyclerTerrenos.adapter = adapter
                    }
                }
                override fun onFailure(call: Call<List<Terreno>>, t: Throwable) {
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