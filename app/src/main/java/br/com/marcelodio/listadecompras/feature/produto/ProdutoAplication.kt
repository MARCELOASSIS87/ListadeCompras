package br.com.marcelodio.listadecompras.feature.produto

import android.app.Application

class ProdutoAplication : Application() {

    var helperDB: HelperDB? = null
        private set

    companion object {
        lateinit var instance: ProdutoAplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)
    }
}