package iago.slopes.appeducation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
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
        binding.btSignOut.setOnClickListener {
            signOut()
        }
        binding.btPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }

    }
    private fun signOut() {
        val sharedPreferences = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.putString("userEmail", "")
        editor.apply()

        FirebaseAuth.getInstance().signOut()

        navegarTelaLogin()
    }
    private fun navegarTelaLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
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