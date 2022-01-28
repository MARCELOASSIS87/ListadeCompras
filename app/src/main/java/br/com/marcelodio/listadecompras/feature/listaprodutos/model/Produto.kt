package br.com.marcelodio.listadecompras.feature.listaprodutos.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class Produto(
    var id: Int = -1,
    var nome:String,
    var quantidade: String
)
