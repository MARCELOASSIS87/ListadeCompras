package br.com.marcelodio.listadecompras.application

import android.app.Application
import br.com.marcelodio.listadecompras.helpers.HelperDB

class ProdutoApplication : Application(){

    var helperDB: HelperDB? = null
        private set

    companion object {
        lateinit var instance: ProdutoApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)
    }
}