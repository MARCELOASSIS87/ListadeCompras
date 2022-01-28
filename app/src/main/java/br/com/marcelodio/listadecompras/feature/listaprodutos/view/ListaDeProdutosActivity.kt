package br.com.marcelodio.listadecompras.feature.listaprodutos.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.marcelodio.listadecompras.feature.listaprodutos.repository.ListaDeProdutosRepository
import br.com.marcelodio.listadecompras.feature.listaprodutos.adapter.ProdutoAdapter
import br.com.marcelodio.listadecompras.R
import br.com.marcelodio.listadecompras.feature.listaprodutos.viewmodel.ListaDeProdutosViewModel
import br.com.marcelodio.listadecompras.feature.produto.SalvarProdutoActivity
import br.com.marcelodio.listadecompras.helpers.HelperDB
import kotlinx.android.synthetic.main.adicionar_produto.*
import kotlinx.android.synthetic.main.rv_list.*

class ListaDeProdutosActivity : AppCompatActivity() {
    private val adapter: ProdutoAdapter? = null
    var viewModel: ListaDeProdutosViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_list)
        if (viewModel == null) viewModel = ListaDeProdutosViewModel(
            ListaDeProdutosRepository(
                HelperDB(this)
            )
        )
        setupOnClicks()
        setupListView()

    }
    private fun setupOnClicks(){
        icon_adicionar.setOnClickListener { onCLickAdicionar() }
        icon_excluir.setOnClickListener {  }
    }

    private fun setupListView() {
        rv_List.layoutManager = LinearLayoutManager(this)
    }

   override fun onResume() {
        super.onResume()

    }


    private fun onCLickAdicionar() {
        val intent = Intent(this, SalvarProdutoActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.adicionar -> {
                showToast("Executando a adição")
                onCLickAdicionar()
                true
            }
            R.id.remover -> {
                showToast("Executando a remoção")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
//    private fun onClickBuscar(){
//        val busca = etBuscar.text.toString()
//        progress.visibility = View.VISIBLE
//        viewModel?.getListaDeContatos(
//            busca,
//            onSucesso = {listaFiltrada ->
//                runOnUiThread {
//                    adapter = ContatoAdapter(this,listaFiltrada) {onClickItemRecyclerView(it)}
//                    recyclerView.adapter = adapter
//                    progress.visibility = View.GONE
//                    Toast.makeText(this,"Buscando por $busca", Toast.LENGTH_SHORT).show()
//                }
//            }, onError = {ex ->
//                runOnUiThread {
//                    AlertDialog.Builder(this)
//                        .setTitle("Atenção")
//                        .setMessage("Não foi possivel completar sua solicitação")
//                        .setPositiveButton("OK"){alert, i->
//                            alert.dismiss()
//                        }.show()
//                }
//            }
//        )
//
//    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

}