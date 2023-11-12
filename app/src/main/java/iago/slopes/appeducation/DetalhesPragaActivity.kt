package iago.slopes.appeducation

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iago.slopes.appeducation.models.Praga

class DetalhesPragaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_praga)

        window.statusBarColor = Color.parseColor("#00A86B")

        val fabVoltarLista = findViewById<FloatingActionButton>(R.id.fabVoltarLista)

        fabVoltarLista.setOnClickListener {
            voltarLista()
        }

        val imgPraga = findViewById<ImageView>(R.id.imgPragaDetalhes)
        val txtNomePraga = findViewById<TextView>(R.id.txtNomePragaDetalhes)
        val txtNomeCientifico = findViewById<TextView>(R.id.txtNomeCientificoDetalhes)
        val txtDescricao = findViewById<TextView>(R.id.txtDescricaoDetalhes)
        val txtCombate = findViewById<TextView>(R.id.txtCombateDetalhes)

        val praga: Praga? = intent.getParcelableExtra("praga") as? Praga

        if (praga != null) {
            val url = praga.foto // A URL da imagem da planta
            Glide.with(this)
                .load(url)
                .into(imgPraga)

            txtNomePraga.text = praga.nome
            txtNomeCientifico.text = "${praga.nome_cientifico}"
            txtDescricao.text = "${praga.descricao}"
            txtCombate.text = "${praga.combate}"
        }
    }
    private fun voltarLista() {
        val intent = Intent(this, Pragas::class.java)
        startActivity(intent)
        finish()
    }
}