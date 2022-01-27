package br.com.marcelodio.listadecompras.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperDB(
    context: Context
) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {

    companion object {
        private val NOME_BANCO = "listasdecompras.db"
        private val VERSAO_ATUAL = 1
        val TABLE_NAME = "contato"
        val COLUMNS_ID = "id"
        val COLUMNS_NOME = "nome"
        val COLUMNS_QUANTIDADE = "quantidade"
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMNS_ID INTEGER NOT NULL," +
                "$COLUMNS_NOME TEXT NOT NULL," +
                "$COLUMNS_QUANTIDADE INTEGER NOT NULL," +
                "" +
                "PRIMARY KEY($COLUMNS_ID AUTOINCREMENT)" +
                ")"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion != newVersion) {
            db?.execSQL(DROP_TABLE)
        }
        onCreate(db)
    }
}