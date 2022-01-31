package br.com.marcelodio.listadecompras.feature.produto


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.marcelodio.listadecompras.R
import kotlinx.android.synthetic.main.rv_list.*

class ListaDeProdutosActivity : AppCompatActivity() {

    private var adapter: ProdutoAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var editNome: EditText
    private lateinit var editQuantidade: EditText
    private lateinit var buttonSalvar: Button
    private lateinit var buttonAtualizar: Button
    private var produto: Produto? = null
    private lateinit var helper: HelperDB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_list)
        initView()
        setupOnClicks()
        setupListView()
        helper = HelperDB(this)
        getProducts()
        buttonSalvar.setOnClickListener { salvarProduto() }
        buttonAtualizar.setOnClickListener { updateProduto() }
        adapter?.setOnClickItem {
            Toast.makeText(this, it.nome, Toast.LENGTH_LONG).show()
            editNome.setText(it.nome)
            editQuantidade.setText(it.quantidade)
            produto = it
        }
    }


    private fun updateProduto() {
        val nome = editNome.text.toString()
        val quantidade = editQuantidade.text.toString()

        if (nome == produto?.nome && quantidade == produto?.quantidade) {
            Toast.makeText(this, "Nada alterado", Toast.LENGTH_LONG).show()
            return
        }
        if (produto == null) return

        val produto = Produto(id = produto!!.id, nome = nome, quantidade = quantidade)
        val status = helper.updateProduto(produto)
        if (status > -1){
            clearEditText()
            getProducts()
        }else{
            Toast.makeText(this,"Atualização Falhou",Toast.LENGTH_LONG).show()

        }

    }

    private fun setupOnClicks() {
        icon_adicionar.setOnClickListener { onCLickAdicionar() }
        icon_excluir.setOnClickListener { }
    }

    private fun getProducts() {
        val list = helper.getAllProducts()
        Log.e("pppp", "${list.size}")
        adapter?.addItens(list)
    }

    private fun setupListView() {
        rv_List.layoutManager = LinearLayoutManager(this)

        adapter = ProdutoAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        editNome = findViewById(R.id.editTextNomeProduto)
        editQuantidade = findViewById(R.id.editTextQuantidade)
        buttonSalvar = findViewById(R.id.buttonSalvar)
        buttonAtualizar = findViewById(R.id.buttonAtualizar)
        recyclerView = findViewById(R.id.rv_List)
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

    private fun salvarProduto() {
        val nome = editNome.text.toString()
        val quantidade = editQuantidade.text.toString()

        if (nome.isEmpty() || quantidade.isEmpty()) {
            Toast.makeText(this, "Campo vazio impossivel salvar", Toast.LENGTH_LONG).show()
        } else {
            val produto = Produto(nome = nome, quantidade = quantidade)
            val status = helper.salvarProduto(produto)
            if (status > -1) {
                Toast.makeText(this, "Produto Salvo", Toast.LENGTH_SHORT).show()
                clearEditText()
                getProducts()
            } else {
                Toast.makeText(this, "Não foi possível salvar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
        editNome.setText("")
        editQuantidade.setText("")
        editNome.requestFocus()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}