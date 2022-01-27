package br.com.marcelodio.listadecompras

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.marcelodio.listadecompras.model.Produto
import kotlinx.android.synthetic.main.adicionar_produto.*

class SalvarProdutoActivity : AppCompatActivity() {

    private var idProduto: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adicionar_produto)
    }
    private fun onClickSalvarProduto(){
        val nome = editTextNomeProduto.text.toString()
        val quantidade = editTextQuantidade.text.toString()
        val produto = Produto(
            idProduto,
            nome,
            quantidade
        )
        progress.visibility = View.VISIBLE
        Thread {
            Thread.sleep(1500)
            if (idContato == -1) {
                ContatoApplication.instance.helperDB?.salvarContato(contato)
            } else {
                ContatoApplication.instance.helperDB?.updateContato(contato)
            }
            runOnUiThread {
                progress.visibility = View.GONE
                finish()
            }
        }.start()
    }
}