package iago.slopes.appeducation

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.android.material.snackbar.Snackbar
import iago.slopes.appeducation.databinding.ActivityTelaPrincipalBinding

class TelaPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#FFFFFF")

        binding.btPlantas.setOnClickListener { view ->
            navegarPlantas(view)
        }
        binding.btTerrenos.setOnClickListener { view ->
            navegarTerrenos(view)
        }
        binding.btPragas.setOnClickListener { view ->
            navegarPragas(view)
        }

    }

    private fun navegarPragas(view: View) {
        binding.btPlantas.isEnabled = false
        binding.btTerrenos.isEnabled = false
        binding.btPragas.isEnabled = false
        binding.btPragas.setTextColor(Color.parseColor("#FFFFFF"))

        Handler(Looper.getMainLooper()).postDelayed({
            val snackbar = Snackbar.make(view, "Carregando...", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.BLUE)
            snackbar.show()
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, Pragas::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }, 1000)
    }

    private fun navegarTerrenos(view: View) {
        binding.btPlantas.isEnabled = false
        binding.btTerrenos.isEnabled = false
        binding.btPragas.isEnabled = false
        binding.btTerrenos.setTextColor(Color.parseColor("#FFFFFF"))

        Handler(Looper.getMainLooper()).postDelayed({
            val snackbar = Snackbar.make(view, "Carregando...", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.BLUE)
            snackbar.show()
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, Terrenos::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }, 1000)
    }

    private fun navegarPlantas(view: View) {
        binding.btPlantas.isEnabled = false
        binding.btTerrenos.isEnabled = false
        binding.btPragas.isEnabled = false
        binding.btPlantas.setTextColor(Color.parseColor("#FFFFFF"))

        Handler(Looper.getMainLooper()).postDelayed({
            val snackbar = Snackbar.make(view, "Carregando...", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.BLUE)
            snackbar.show()
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, Plantas::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }, 1000)
    }
}