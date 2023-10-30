package iago.slopes.appeducation

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import iago.slopes.appeducation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#FFFFFF")

        binding.txtTelaCadastro.setOnClickListener {
            navegarTelaCadastro()
        }
        binding.txtForgotPass.setOnClickListener {
            mandarEmailPass()
        }
        binding.btEntrar.setOnClickListener{view ->

            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            when{
                email.isEmpty() -> {
                    binding.editEmail.error = "Preencha o E-mail!"
                }
                senha.isEmpty() -> {
                    binding.editSenha.error = "Preencha a Senha!"
                }
                else -> {
                    login(view)
                }
            }
        }
    }

    private fun mandarEmailPass() {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun login(view: View){
        val email = binding.editEmail.text.toString()
        val senha = binding.editSenha.text.toString()

        val progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        binding.btEntrar.isEnabled = false
        binding.txtForgotPass.isEnabled = false
        binding.txtTelaCadastro.isEnabled = false
        binding.btEntrar.setTextColor(Color.parseColor("#FFFFFF"))

        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { login ->
            if (login.isSuccessful){
                val user = auth.currentUser
                if (user != null && user.isEmailVerified) {
                    // E-mail verificado, permita o login
                    Handler(Looper.getMainLooper()).postDelayed({
                        val snackbar = Snackbar.make(view, "Login efetuado com sucesso!", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.BLUE)
                        snackbar.show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            navegarTelaPrincipal()
                        }, 2000)
                    }, 1000)
                } else {
                    // E-mail não verificado, exiba uma mensagem
                    val snackbar = Snackbar.make(view, "Verifique seu e-mail para ativar sua conta!", Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                    progressBar.visibility = View.INVISIBLE
                    binding.btEntrar.isEnabled = true
                    binding.txtForgotPass.isEnabled = true
                    binding.txtTelaCadastro.isEnabled = true
                    binding.btEntrar.setTextColor(Color.parseColor("#FF000000"))
                }
            }
        }.addOnFailureListener {exception ->

            val mensagemErro = when(exception){
                is FirebaseAuthInvalidCredentialsException -> "Digite um e-mail válido!"
                is FirebaseAuthInvalidUserException -> "Essa conta não existe!"
                is FirebaseNetworkException -> "Sem conexão com a internet!"
                else -> "Erro ao entrar com o usuário ou senha!"
            }
            val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
            progressBar.visibility = View.INVISIBLE
            binding.btEntrar.isEnabled = true
            binding.txtForgotPass.isEnabled = true
            binding.txtTelaCadastro.isEnabled = true
            binding.btEntrar.setTextColor(Color.parseColor("#FF000000"))
        }
    }

    private  fun navegarTelaPrincipal(){
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }

    private fun navegarTelaCadastro(){
        val intent = Intent(this, FormCadastro::class.java)
        startActivity(intent)
        finish()
    }

}