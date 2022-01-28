package br.com.marcelodio.listadecompras.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.marcelodio.listadecompras.feature.listaprodutos.model.Produto

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
    fun salvarContato(produto: Produto) {
        val db = writableDatabase ?: return
        var content = ContentValues()
        content.put(COLUMNS_NOME, produto.nome)
        content.put(COLUMNS_QUANTIDADE, produto.quantidade)
        db.insert(TABLE_NAME, null, content)
        db.close()
    }
    fun updateContato(produto: Produto) {
        val db = writableDatabase ?: return
        val sql =
            "UPDATE $TABLE_NAME SET $COLUMNS_NOME = ?, $COLUMNS_QUANTIDADE = ? WHERE $COLUMNS_ID = ?"
        val arg = arrayOf(produto.nome, produto.quantidade, produto.id)
        db.execSQL(sql, arg)
        db.close()
    }
    fun buscarProduto(busca: String, isBuscaPorID: Boolean = false): List<Produto> {
        val db = readableDatabase ?: return mutableListOf()
        var lista = mutableListOf<Produto>()
        var where: String? = null
        var args: Array<String> = arrayOf()
        if (isBuscaPorID) {
            where = "$COLUMNS_ID = ?"
            args = arrayOf("$busca")
        } else {
            where = "$COLUMNS_NOME LIKE ?"
            args = arrayOf("%$busca%")
        }
        var cursor = db.query(TABLE_NAME, null, where, args, null, null, null)
        if (cursor == null) {
            db.close()
            return mutableListOf()
        }
        while (cursor.moveToNext()) {
            var produto = Produto(
                cursor.getInt(cursor.getColumnIndex(COLUMNS_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_NOME)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_QUANTIDADE))
            )
            lista.add(produto)
        }
        db.close()
        return lista
    }
    fun deletarProduto(id: Int) {
        val db = writableDatabase ?: return
        val sql = "DELETE FROM $TABLE_NAME WHERE $COLUMNS_ID = ?"
        val arg = arrayOf("$id")
        db.execSQL(sql, arg)
        db.close()
    }

    fun updateProduto(produto: Produto) {
        val db = writableDatabase ?: return
        val sql =
            "UPDATE $TABLE_NAME SET $COLUMNS_NOME = ?, $COLUMNS_QUANTIDADE = ? WHERE $COLUMNS_ID = ?"
        val arg = arrayOf(produto.nome, produto.quantidade, produto.id)
        db.execSQL(sql, arg)
        db.close()
    }
}