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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import iago.slopes.appeducation.databinding.ActivityFormCadastroBinding
import iago.slopes.appeducation.databinding.ActivityMainBinding

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#FFFFFF")

        binding.txtTelaLogin.setOnClickListener {
            navegarTelaLogin()
        }
        binding.btCadastrar.setOnClickListener{ view ->
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
                    cadastrar(view)
                }
            }
        }
    }

    private fun cadastrar(view: View){
        val email = binding.editEmail.text.toString()
        val senha = binding.editSenha.text.toString()

        val progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        binding.btCadastrar.isEnabled = false
        binding.btCadastrar.setTextColor(Color.parseColor("#FFFFFF"))

        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { cadastro ->
            if (cadastro.isSuccessful){
                Handler(Looper.getMainLooper()).postDelayed({
                    val snackbar = Snackbar.make(view, "Cadastro efetuado com sucesso!",Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.BLUE)
                    snackbar.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        navegarTelaLogin()
                    },2000)
                },1000)

            }
        }.addOnFailureListener {exception ->

            val mensagemErro = when(exception){
                is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres!"
                is FirebaseAuthInvalidCredentialsException -> "Digite um e-mail válido!"
                is FirebaseAuthUserCollisionException -> "Essa conta já foi cadastrada!"
                is FirebaseNetworkException -> "Sem conexão com a internet!"
                else -> "Erro ao cadastrar usuário!"
            }
            val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
            progressBar.visibility = View.INVISIBLE
            binding.btCadastrar.isEnabled = true
            binding.btCadastrar.setTextColor(Color.parseColor("#FF000000"))
        }
    }

    private fun navegarTelaLogin(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}