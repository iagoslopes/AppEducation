package iago.slopes.appeducation.models

import com.google.firebase.firestore.PropertyName

data class Usuario(
    @get:PropertyName("nome") @set:PropertyName("nome")
    var nome: String = "",

    @get:PropertyName("dataNascimento") @set:PropertyName("dataNascimento")
    var dataNascimento: String = "",

    @get:PropertyName("sexo") @set:PropertyName("sexo")
    var sexo: String = ""
) {
    // Construtor padrão sem argumentos
    constructor() : this("", "", "")
}