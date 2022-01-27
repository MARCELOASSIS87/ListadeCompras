package br.com.marcelodio.listadecompras

import br.com.marcelodio.listadecompras.model.Produto

interface ClickItemProdutoListener {
    fun clickItemProduto(produto: Produto)
}