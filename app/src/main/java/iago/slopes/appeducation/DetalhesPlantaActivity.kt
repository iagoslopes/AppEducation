package iago.slopes.appeducation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iago.slopes.appeducation.models.Planta

class DetalhesPlantaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_planta)

        val fabVoltarLista = findViewById<FloatingActionButton>(R.id.fabVoltarLista)

        fabVoltarLista.setOnClickListener {
            voltarLista()
        }

        val imgPlanta = findViewById<ImageView>(R.id.imgPlantaDetalhes)
        val txtNomePlanta = findViewById<TextView>(R.id.txtNomePlantaDetalhes)
        val txtNomeCientifico = findViewById<TextView>(R.id.txtNomeCientificoDetalhes)
        val txtDescricao = findViewById<TextView>(R.id.txtDescricaoDetalhes)
        val txtTipo = findViewById<TextView>(R.id.txtTipoDetalhes)
        val txtTerreno = findViewById<TextView>(R.id.txtTerrenoDetalhes)
        val txtPraga = findViewById<TextView>(R.id.txtPragaDetalhes)
        val txtCultivo = findViewById<TextView>(R.id.txtCultivoDetalhes)

        val planta: Planta? = intent.getParcelableExtra("planta") as? Planta

        if (planta != null) {
            val url = planta.foto // A URL da imagem da planta
            Glide.with(this)
                .load(url)
                .into(imgPlanta)

            txtNomePlanta.text = planta.nome
            txtNomeCientifico.text = "Nome Científico: ${planta.nome_cientifico}"
            txtDescricao.text = "Descrição: ${planta.descricao}"
            txtTipo.text = "Tipo: ${planta.tipo}"
            txtTerreno.text = "Terreno: ${planta.terreno}"
            txtPraga.text = "Praga: ${planta.praga}"
            txtCultivo.text = "Cultivo: ${planta.cultivo}"
        }
    }
    private fun voltarLista() {
        val intent = Intent(this, Plantas::class.java)
        startActivity(intent)
        finish()
    }
}