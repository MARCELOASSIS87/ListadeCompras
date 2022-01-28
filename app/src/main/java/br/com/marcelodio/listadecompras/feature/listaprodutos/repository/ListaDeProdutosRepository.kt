package br.com.marcelodio.listadecompras.feature.listaprodutos.repository

import br.com.marcelodio.listadecompras.bases.BaseRepository
import br.com.marcelodio.listadecompras.helpers.HelperDB
import br.com.marcelodio.listadecompras.helpers.HelperDB.Companion.COLUMNS_ID
import br.com.marcelodio.listadecompras.helpers.HelperDB.Companion.COLUMNS_NOME
import br.com.marcelodio.listadecompras.helpers.HelperDB.Companion.COLUMNS_QUANTIDADE
import br.com.marcelodio.listadecompras.helpers.HelperDB.Companion.TABLE_NAME
import br.com.marcelodio.listadecompras.feature.listaprodutos.model.Produto
import java.lang.Exception
import java.sql.SQLDataException

class ListaDeProdutosRepository
    (
    helperDBar: HelperDB? = null
) : BaseRepository(helperDBar) {

    fun requestListaDeProdutos(
        busca: String,
        onSucesso: ((List<Produto>) -> Unit),
        onError: ((Exception) -> Unit)
    ) {
        try {
            val db = readableDataBase
            var lista = mutableListOf<Produto>()
            var where: String? = null
            var args: Array<String> = arrayOf()
//       if(isBuscaPorID){
//            where = "$COLUMNS_ID = ?"
//            args = arrayOf("$busca")
//        }else{
//                where = "$COLUMNS_NOME LIKE ?"
//                args = arrayOf("%$busca%")

            var cursor = db?.query(TABLE_NAME, null, where, args, null, null, null)
            if (cursor == null) {
                db?.close()
                onError(SQLDataException("não foi possível fazer a query"))
                return
            }
            while (cursor.moveToNext()) {
                var produto = Produto(
                    cursor.getInt(cursor.getColumnIndex(COLUMNS_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_NOME)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_QUANTIDADE))
                )
                lista.add(produto)
            }
            db?.close()
            onSucesso(lista)
        } catch (ex: Exception) {
            onError(ex)
        }
    }
}