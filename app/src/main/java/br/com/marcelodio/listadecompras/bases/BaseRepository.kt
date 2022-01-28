package br.com.marcelodio.listadecompras.bases

import android.database.sqlite.SQLiteDatabase
import br.com.marcelodio.listadecompras.helpers.HelperDB

open class BaseRepository(
    val helperDB: HelperDB? = null
) {
    val readableDataBase: SQLiteDatabase?
        get() {
            return helperDB?.readableDatabase
        }
    val writableDataBase: SQLiteDatabase?
        get() {
            return helperDB?.writableDatabase
        }
}