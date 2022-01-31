package br.com.marcelodio.listadecompras.feature.produto

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Produto(
    var id: Int = 0,
    var nome: String = "",
    var quantidade: String = ""
) {
//    companion object{
//        fun getAutoId():Int{
//          val random = Random()
//          return random.nextInt(100)
//        }
//    }
}


