package br.com.marcelodio.listadecompras.feature.produto


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.marcelodio.listadecompras.R
import kotlinx.android.synthetic.main.rv_list.*

open class ListaDeProdutosActivity : AppCompatActivity() {

    private var adapter: ProdutoAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var editNome: EditText
    private lateinit var editQuantidade: EditText
    private lateinit var iconeSalvar: ImageView
    private var produto: Produto? = null
    private lateinit var helper: HelperDB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_list)
        initView()
        setupListView()
        helper = HelperDB(this)
        getProducts()
        iconeSalvar.setOnClickListener { onCLickAdicionar() }
        adapter?.setOnClickItem {
            Toast.makeText(this, it.nome, Toast.LENGTH_LONG).show()
            editNome.setText(it.nome)
            editQuantidade.setText(it.quantidade)
            produto = it
        }
        adapter?.setOnClickDeleteItem {
            deleteItem(it.id)
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

        val produto = Produto(
            id = produto!!.id,
            nome = nome,
            quantidade = quantidade,
        )
        val status = helper.updateProduto(produto)
        if (status > -1) {
            //clearEditText()
            getProducts()
        } else {
            Toast.makeText(this, "Atualização Falhou", Toast.LENGTH_LONG).show()

        }

    }

    private fun deleteItem(id: Int) {
        if (id == null) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Você quer mesmo deletar o este produto?")
        builder.setCancelable(true)
        builder.setPositiveButton("Sim") { dialog, _ ->
            helper.deleteProdutoById(id)
            getProducts()
            dialog.dismiss()
        }
        builder.setNegativeButton("Não") { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    fun getProducts() {
        val list = helper.getAllProducts()
        Log.e("pppp", "${list.size}")
        adapter?.addItens(list)

    }

    private fun setupListView() {
        rv_List.layoutManager = LinearLayoutManager(this)
        adapter = ProdutoAdapter()
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getProducts()
    }
    private fun initView() {
        recyclerView = findViewById(R.id.rv_List)
        iconeSalvar = findViewById(R.id.icon_adicionar)
    }

    private fun onCLickAdicionar() {
        val intent = Intent(this, SalvarProdutoActivity::class.java)
        startActivity(intent)
    }


}

