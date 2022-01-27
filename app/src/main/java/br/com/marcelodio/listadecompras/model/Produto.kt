package br.com.marcelodio.listadecompras.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Produto(
    var nome:String,
    var quantidade: Int
) : Parcelable