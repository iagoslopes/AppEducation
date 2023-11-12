package iago.slopes.appeducation

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
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

        window.statusBarColor = Color.parseColor("#00A86B")

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

        mostrarMensagemCarregando()

        retrofitCli.plantaService.getAllPlantas().enqueue(
            object:Callback<List<Planta>>{
                override fun onResponse(
                    call: Call<List<Planta>>,
                    response: Response<List<Planta>>

                ) {
                    esconderMensagemCarregando()
                    if (response.body()!=null){
                        var adapter:AdapterPlantas=
                            AdapterPlantas(this@Plantas,response.body()!!)

                        adapter.setOnItemClickListener(object : AdapterPlantas.OnItemClickListener {
                            override fun onItemClick(planta: Planta) {
                                val intent = Intent(this@Plantas, DetalhesPlantaActivity::class.java)
                                intent.putExtra("planta", planta)
                                startActivity(intent)
                            }
                        })

                        recyclerPlantas.adapter = adapter
                    }
                }
                override fun onFailure(call: Call<List<Planta>>, t: Throwable) {
                    esconderMensagemCarregando()
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
    private var loadingSnackbar: Snackbar? = null
    private fun mostrarMensagemCarregando() {
        loadingSnackbar = Snackbar.make(recyclerPlantas, "Carregando Plantas...", Snackbar.LENGTH_INDEFINITE)
        loadingSnackbar!!.setBackgroundTint(Color.GREEN)
        loadingSnackbar!!.show()
    }
    private fun esconderMensagemCarregando() {
        // Fecha a mensagem de carregamento se estiver aberta
        loadingSnackbar?.dismiss()
    }
}