package br.com.marcelodio.listadecompras.feature.produto


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    private lateinit var iconSalvar: ImageView
    private var produto: Produto? = null
    private lateinit var helper: HelperDB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_list)
        initView()
        setupListView()
        helper = HelperDB(this)
        getProducts()
        buttonSalvar.setOnClickListener { salvarProduto() }
        buttonAtualizar.setOnClickListener { updateProduto() }
        iconSalvar.setOnClickListener { onCLickAdicionar() }
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
            clearEditText()
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
        iconSalvar = findViewById(R.id.icon_adicionar)
    }

    private fun onCLickAdicionar() {
        val intent = Intent(this, SalvarProdutoActivity::class.java)
        startActivity(intent)
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
}

