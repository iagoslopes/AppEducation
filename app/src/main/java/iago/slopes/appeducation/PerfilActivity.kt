package iago.slopes.appeducation

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import iago.slopes.appeducation.models.Usuario
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
class PerfilActivity : AppCompatActivity() {
    private lateinit var usuarioRef: DocumentReference
    private lateinit var userEmail: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        window.statusBarColor = Color.parseColor("#00A86B")

        val sharedPreferences = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
        userEmail = sharedPreferences.getString("userEmail", "") ?: ""

        // Obtendo referência ao usuário no Firestore usando o email
        usuarioRef = FirebaseFirestore.getInstance().collection("users").document(userEmail)

        // Inicializando elementos UI
        val editNome = findViewById<EditText>(R.id.editNome)
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editDataNascimento = findViewById<EditText>(R.id.editDataNascimento)
        val spinnerSexo = findViewById<Spinner>(R.id.spinnerSexo)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        editDataNascimento.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Não é necessário implementar neste caso
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Não é necessário implementar neste caso
            }

            override fun afterTextChanged(s: Editable?) {
                // Verifica se a formatação é necessária antes de continuar
                if (s.toString() != formatarData(s.toString())) {
                    // Remove o TextWatcher temporariamente para evitar chamadas recursivas
                    editDataNascimento.removeTextChangedListener(this)

                    // Formata o texto adicionando barras conforme necessário
                    val formattedText = formatarData(s.toString())

                    // Atualiza o texto do EditText
                    editDataNascimento.setText(formattedText)

                    // Define o cursor para a posição correta após a formatação
                    editDataNascimento.setSelection(formattedText.length)

                    // Adiciona o TextWatcher de volta
                    editDataNascimento.addTextChangedListener(this)
                }
            }
        })

        // Carregar informações do usuário
        carregarInformacoesUsuario(editNome, editEmail, editDataNascimento, spinnerSexo)

        // Configurando o botão Salvar
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        btnSalvar.setOnClickListener {
            val nome = editNome.text.toString()
            val dataNascimento = editDataNascimento.text.toString()
            val sexo = spinnerSexo.selectedItem.toString()

            // Criando um objeto Usuario com os dados
            val usuario = Usuario(nome, dataNascimento, sexo)

            // Salvando ou atualizando as informações do usuário
            salvarOuAtualizarInformacoesUsuario(usuario)
        }

        // Configurando o botão Voltar
        btnVoltar.setOnClickListener {
            // Implemente a ação desejada ao clicar no botão Voltar
            finish()
        }
    }
    private fun formatarData(input: String): String {
        var formatted = input.replace("[^\\d.]".toRegex(), "") // Remove caracteres não numéricos

        if (formatted.length > 2) {
            formatted = formatted.substring(0, 2) + "/" + formatted.substring(2)
        }
        if (formatted.length > 5) {
            formatted = formatted.substring(0, 5) + "/" + formatted.substring(5)
        }

        return formatted
    }
    fun showDatePickerDialog(view: View) {
        val editText = findViewById<EditText>(R.id.editDataNascimento)

        val currentDate = editText.text.toString().takeIf { it.isNotBlank() }
        val dateArray = currentDate?.split("/")?.map { it.toInt() }

        val calendar = if (dateArray != null && dateArray.size == 3) {
            Calendar.getInstance().apply {
                set(Calendar.YEAR, dateArray[2])
                set(Calendar.MONTH, dateArray[1] - 1) // O mês no Calendar começa do zero
                set(Calendar.DAY_OF_MONTH, dateArray[0])
            }
        } else {
            Calendar.getInstance()
        }

        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
                editText.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }
    private fun carregarInformacoesUsuario(
        editNome: EditText,
        editEmail: EditText,
        editDataNascimento: EditText,
        spinnerSexo: Spinner
    ) {
        // Adiciona um listener para ouvir mudanças nos dados do usuário
        usuarioRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // Lidar com erros de leitura do banco de dados
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                // O documento do usuário existe, carregar os dados nos campos
                val usuario = snapshot.toObject(Usuario::class.java)

                usuario?.let {
                    editNome.setText(it.nome)
                    editEmail.setText(userEmail) // Se desejar exibir o e-mail do usuário
                    editDataNascimento.setText(it.dataNascimento)
                    // Configurar o Spinner com o sexo do usuário
                    configureSexSpinner(spinnerSexo, it.sexo)
                }
            } else {
                // O documento do usuário não existe, deixar os campos vazios ou com valores padrão
                editEmail.setText(userEmail)
            }
        }
    }
    private fun salvarOuAtualizarInformacoesUsuario(usuario: Usuario) {
        // Lógica para salvar ou atualizar as informações do usuário no Firestore
        // Use usuarioRef para acessar e atualizar os dados no Firestore
        usuarioRef.set(usuario)
            .addOnSuccessListener {
                Toast.makeText(this, "Informações do perfil salvas com sucesso!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao salvar informações do perfil.", Toast.LENGTH_SHORT).show()
            }
    }
    private fun configureSexSpinner(spinner: Spinner, sexo: String) {
        val sexoOptions = arrayOf("Masculino", "Feminino")
        val sexoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sexoOptions)
        spinner.adapter = sexoAdapter

        // Selecionar o sexo do usuário no Spinner
        val position = sexoOptions.indexOf(sexo)
        if (position != -1) {
            spinner.setSelection(position)
        }
    }
}