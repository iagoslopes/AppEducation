package iago.slopes.appeducation

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.android.material.snackbar.Snackbar
import iago.slopes.appeducation.databinding.ActivityFormCadastroBinding
import iago.slopes.appeducation.databinding.ActivityMainBinding

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#FFFFFF")

        binding.txtTelaLogin.setOnClickListener {
            navegarTelaLogin()
        }
        binding.btCadastrar.setOnClickListener{

            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            when{
                email.isEmpty() -> {
                    binding.editEmail.error = "Preencha o E-mail!"
                }
                senha.isEmpty() -> {
                    binding.editSenha.error = "Preencha a Senha!"
                }
                !email.contains("@gmail.com") -> {
                    val snackbar = Snackbar.make(it, "E-mail inválido!", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
                senha.length <= 5 -> {
                    val snackbar = Snackbar.make(it, "A senha precisa ter pelo menos 6 caracteres!",
                        Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
                else -> {
                    cadastrar(it)
                }
            }
        }

    }
    private fun navegarTelaLogin(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun cadastrar(view: View){
        val progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        binding.btCadastrar.isEnabled = false
        binding.btCadastrar.setTextColor(Color.parseColor("#FFFFFF"))

        Handler(Looper.getMainLooper()).postDelayed({
            navegarTelaLogin()
            val snackbar = Snackbar.make(view, "Login efetuado com sucesso!",Snackbar.LENGTH_SHORT)
            snackbar.show()
        },3000)
    }
}