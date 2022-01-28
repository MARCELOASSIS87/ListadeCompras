package br.com.marcelodio.listadecompras.feature.produto

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.marcelodio.listadecompras.R
import br.com.marcelodio.listadecompras.application.ProdutoApplication
import br.com.marcelodio.listadecompras.feature.listaprodutos.model.Produto
import kotlinx.android.synthetic.main.adicionar_produto.*
import kotlinx.android.synthetic.main.product_item.*

class SalvarProdutoActivity : AppCompatActivity() {

    private var idProduto: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adicionar_produto)
        setupContato()
        buttonSalvar.setOnClickListener { onClickSalvarProduto() }
    }
    private fun setupContato(){
        idProduto = intent.getIntExtra("index",-1)
//       if (idProduto == -1){
//            btnExcluirContato.visibility = View.GONE
//            return
//        }
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500)
            var lista = ProdutoApplication.instance.helperDB?.buscarProduto("$idProduto",true) ?: return@Runnable
            var produto = lista.getOrNull(0) ?: return@Runnable
            runOnUiThread {
                tv_Nome.setText(produto.nome)
                tv_Number.setText(produto.quantidade)
                progress.visibility = View.GONE
            }
        }).start()
    }


    private fun onClickSalvarProduto() {
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
            if (idProduto == -1) {
                ProdutoApplication.instance.helperDB?.salvarContato(produto)
            } else {
                ProdutoApplication.instance.helperDB?.updateContato(produto)
            }
            runOnUiThread {
                progress.visibility = View.GONE
                finish()
            }
        }.start()
    }
    fun onClickExcluirContato(view: View) {
        if(idProduto > -1){
            progress.visibility = View.VISIBLE
            Thread(Runnable {
                Thread.sleep(1500)
                ProdutoApplication.instance.helperDB?.deletarProduto(idProduto)
                runOnUiThread {
                    progress.visibility = View.GONE
                    finish()
                }
            }).start()
        }
    }


}