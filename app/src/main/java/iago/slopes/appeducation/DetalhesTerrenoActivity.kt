package iago.slopes.appeducation

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iago.slopes.appeducation.models.Terreno

class DetalhesTerrenoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_terreno)

        window.statusBarColor = Color.parseColor("#00A86B")

        val fabVoltarLista = findViewById<FloatingActionButton>(R.id.fabVoltarLista)

        fabVoltarLista.setOnClickListener {
            voltarLista()
        }

        val imgTerreno = findViewById<ImageView>(R.id.imgTerrenoDetalhes)
        val txtNomeTerreno = findViewById<TextView>(R.id.txtNomeTerrenoDetalhes)
        val txtNomeCientifico = findViewById<TextView>(R.id.txtNomeCientificoDetalhes)
        val txtDescricao = findViewById<TextView>(R.id.txtDescricaoDetalhes)
        val txtCaracteristica = findViewById<TextView>(R.id.txtCaracteristicaDetalhes)

        val terreno: Terreno? = intent.getParcelableExtra("terreno") as? Terreno

        if (terreno != null) {
            val url = terreno.foto // A URL da imagem da planta
            Glide.with(this)
                .load(url)
                .into(imgTerreno)

            txtNomeTerreno.text = terreno.nome
            txtNomeCientifico.text = "${terreno.nome_cientifico}"
            txtDescricao.text = "${terreno.descricao}"
            txtCaracteristica.text = "${terreno.caracteristica}"
        }
    }
    private fun voltarLista() {
        val intent = Intent(this, Terrenos::class.java)
        startActivity(intent)
        finish()
    }
}