package br.com.marcelodio.listadecompras.feature.listaprodutos.viewmodel

import br.com.marcelodio.listadecompras.feature.listaprodutos.repository.ListaDeProdutosRepository
import br.com.marcelodio.listadecompras.feature.listaprodutos.model.Produto
import java.lang.Exception

class ListaDeProdutosViewModel (
    var repository: ListaDeProdutosRepository? = null
    ) {

        fun getListaDeContatos(
            busca: String,
            onSucesso: ((List<Produto>) -> Unit),
            onError: ((Exception) -> Unit)
        ) {
            Thread(Runnable {
                Thread.sleep(1500)
                repository?.requestListaDeProdutos(
                    busca,
                    onSucesso = { lista ->
                        onSucesso(lista)
                    }, onError = { ex ->
                        onError(ex)
                    }
                )
            }).start()

        }
}