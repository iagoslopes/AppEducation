package iago.slopes.appeducation

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import iago.slopes.appeducation.databinding.ForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ForgotPasswordBinding
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#FFFFFF")

        binding.txtTelaForgot.setOnClickListener {
            voltarTelaLogin()
        }

        binding.btEnviar.setOnClickListener { view ->
            enviarEmail(view)
        }
    }

    private fun enviarEmail(view : View) {
        val email = binding.editEmail.text.toString()

        val progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        binding.btEnviar.isEnabled = false
        binding.txtTelaForgot.isEnabled = false
        binding.btEnviar.setTextColor(Color.parseColor("#FFFFFF"))

        if (email.isNotEmpty()) {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val snackbar = Snackbar.make(view, "Redefinição enviado com sucesso!Verifique seu e-mail",
                                Snackbar.LENGTH_SHORT)
                            snackbar.setBackgroundTint(Color.BLUE)
                            snackbar.show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                voltarTelaLogin()
                            },4000)
                        },2000)
                    } else {
                        val snackbar = Snackbar.make(view, "Erro! Verifique se o e-mail esta correto.", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                        progressBar.visibility = View.INVISIBLE
                        binding.btEnviar.isEnabled = true
                        binding.txtTelaForgot.isEnabled = true
                        binding.btEnviar.setTextColor(Color.parseColor("#FF000000"))
                    }
                }
        } else {
            val snackbar = Snackbar.make(view, "E-mail é obrigatório.", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
            progressBar.visibility = View.INVISIBLE
            binding.btEnviar.isEnabled = true
            binding.btEnviar.setTextColor(Color.parseColor("#FF000000"))
        }
    }

    private fun voltarTelaLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}