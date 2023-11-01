package iago.slopes.appeducation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Planta (
    var nome: String,
    var nome_cientifico: String,
    var descricao: String,
    var tipo: String,
    var terreno: String,
    var praga: String,
    var cultivo: String,
    var foto: String
) : Parcelable