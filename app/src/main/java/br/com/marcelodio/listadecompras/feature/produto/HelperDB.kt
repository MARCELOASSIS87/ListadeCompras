package br.com.marcelodio.listadecompras.feature.produto

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperDB(context: Context) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {

    companion object {
        private val NOME_BANCO = "listasdecompras.db"
        private val VERSAO_ATUAL = 1
        val TABLE_NAME = "contato"
        val COLUMNS_ID = "id"
        val COLUMNS_NOME = "nome"
        val COLUMNS_QUANTIDADE = "quantidade"
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        val CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                "$COLUMNS_ID INTEGER NOT NULL," +
                "$COLUMNS_NOME TEXT NOT NULL," +
                "$COLUMNS_QUANTIDADE TEXT NOT NULL," +
                "" +
                "PRIMARY KEY($COLUMNS_ID AUTOINCREMENT)" +
                ")"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL(DROP_TABLE)
        }
        onCreate(db)
    }

    fun salvarProduto(produto: Produto): Long {
        val db = this.writableDatabase
        val content = ContentValues()

        content.put(COLUMNS_NOME, produto.nome)
        content.put(COLUMNS_QUANTIDADE, produto.quantidade)
        val sucesso = db.insert(TABLE_NAME, null, content)
        db.close()
        return sucesso
    }

    fun deleteProdutoById(id: Int): Int {
        val db = this.writableDatabase
        val content = ContentValues()

        content.put(COLUMNS_ID, id)

        val sucesso = db.delete(TABLE_NAME, "id=$id", null)
        db.close()
        return sucesso
    }
//    fun somaValores():String{
//        val db = this.readableDatabase
//        var soma:String = ""
//        val somaQuery = "SELECT SUM($COLUMNS_VALOR) as $soma FROM $TABLE_NAME"
//        soma = db.execSQL(somaQuery).toString()
//        return soma
//    }

    @SuppressLint("Range")
    fun getAllProducts(): ArrayList<Produto> {
        val list: ArrayList<Produto> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val db = this.readableDatabase

        val cursor: Cursor

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var nome: String
        var quantidade: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                nome = cursor.getString(cursor.getColumnIndex("nome"))
                quantidade = cursor.getString(cursor.getColumnIndex("quantidade"))
                val produto = Produto(id = id, nome = nome, quantidade = quantidade)
                list.add(produto)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list

    }

    fun updateProduto(produto: Produto): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMNS_ID, produto.id)
        contentValues.put(COLUMNS_NOME, produto.nome)
        contentValues.put(COLUMNS_QUANTIDADE, produto.quantidade)

        val success = db.update(TABLE_NAME, contentValues, "id=" + produto.id, null)
        db.close()
        return success
    }


}

