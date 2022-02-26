package br.com.marcelodio.listadecompras.feature.produto

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.marcelodio.listadecompras.R
import kotlinx.android.synthetic.main.adicionar_produto.*


class SalvarProdutoActivity : AppCompatActivity() {

    private lateinit var editNome: EditText
    private lateinit var editQuantidade: EditText
    private lateinit var buttonSalvar: Button

    private lateinit var helper: HelperDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adicionar_produto)
        helper = HelperDB(this)
        initView()
        buttonSalvar.setOnClickListener { salvarProduto() }
        setupToolBar(toolBar,"Lista de Compras", true)

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


    private fun initView() {
        editNome = findViewById(R.id.editTextNomeProduto)
        editQuantidade = findViewById(R.id.editTextQuantidade)
        buttonSalvar = findViewById(R.id.buttonSalvar)
    }

    private fun setupToolBar(toolBar: Toolbar, title: String, navgationBack: Boolean) {
        toolBar.title = title
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(navgationBack)
    }
}




