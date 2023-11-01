package iago.slopes.appeducation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Praga (
    var nome: String,
    var nome_cientifico: String,
    var descricao: String,
    var combate: String,
    var foto: String
) : Parcelable