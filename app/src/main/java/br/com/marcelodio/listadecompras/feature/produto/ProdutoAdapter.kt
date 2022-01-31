package br.com.marcelodio.listadecompras.feature.produto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.marcelodio.listadecompras.R
import kotlinx.android.synthetic.main.product_item.view.*

class ProdutoAdapter(
    private val context: Context,
    private val list: List<Produto>,
    //private val onClick: ((Int) -> Unit)
) : RecyclerView.Adapter<ProdutoAdapter.ProdutoAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoAdapterViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.product_item,
            parent,
            false
        )//inflo o arquivo xml responsavel por cada card na tela
        return ProdutoAdapterViewHolder(view)
    }
    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ProdutoAdapterViewHolder, position: Int) {
        val produto = list[position]
        with(holder.itemView) {
            tv_Nome.text = produto.nome
            tv_Quantidade.text = produto.quantidade
            //llItem.setOnClickListener { onClick(produto.id) }
        }
    }

    class ProdutoAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
