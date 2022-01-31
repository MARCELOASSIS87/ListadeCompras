package br.com.marcelodio.listadecompras.feature.produto

import android.content.ComponentCallbacks
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.marcelodio.listadecompras.R
import kotlinx.android.synthetic.main.product_item.view.*

class ProdutoAdapter : RecyclerView.Adapter<ProdutoAdapter.ProdutoAdapterViewHolder>() {

    private var list: ArrayList<Produto> = ArrayList()
    private var onClickItem:((Produto) -> Unit)? = null

    fun addItens(itens: ArrayList<Produto>) {
        this.list = itens
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback: (Produto)->Unit){
        this.onClickItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProdutoAdapterViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.product_item,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ProdutoAdapterViewHolder, position: Int) {
        val produto = list[position]
        holder.bindView(produto)
        holder.itemView.setOnClickListener { onClickItem?.invoke(produto) }
    }

    class ProdutoAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //private var id = itemView.findViewById<TextView>(R.id.tv_id)
        private var nome = itemView.findViewById<TextView>(R.id.tv_Nome)
        private var quantidade = itemView.findViewById<TextView>(R.id.tv_Quantidade)
        private var btnDelete = itemView.findViewById<Button>(R.id.buttonDelete)

        fun bindView(produto: Produto) {
            nome.text = produto.nome
            quantidade.text = produto.quantidade
        }

    }
}
