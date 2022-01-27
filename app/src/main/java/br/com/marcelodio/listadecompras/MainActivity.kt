package br.com.marcelodio.listadecompras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.marcelodio.listadecompras.model.Produto

class MainActivity : AppCompatActivity(), ClickItemProdutoListener {
    private val rvList: RecyclerView by lazy {
        findViewById(R.id.rv_List)
    }
    private val adapter = ProdutoAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_list)
        //initDrawer()
        bindView()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun bindView() {
        rvList.adapter = adapter
        rvList.layoutManager =
            LinearLayoutManager(this) //a forma que ele vai se comportar, como vai se estruturar
        //updateList()
    }
//    private fun getListContact(): List<Contact> {
//        val list = getInstanceSharedPreferences().getString("contacts", "[]")
//        val turnsType = object : TypeToken<List<Contact>>() {}.type
//        return Gson().fromJson(list, turnsType)
//    }
//    private fun updateList() {
//        val list = getListContact()
//        adapter.updateList(list)
//    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }
    private fun onCLickAdicionar(){
        val intent = Intent(this,SalvarProdutoActivity::class.java)
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

    override fun clickItemProduto(produto: Produto) {
        //AQUI É A AÇÃO DO CLICK LONGO PARA SELECIONAR VARIOS-
    }

}