package br.com.marcelodio.listadecompras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.marcelodio.listadecompras.model.Produto

class ProdutoAdapter(var listener: ClickItemProdutoListener) :
    RecyclerView.Adapter<ProdutoAdapter.ProdutoAdapterViewHolder>() {

    private var list: MutableList<Produto> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.product_item,
            parent,
            false
        )//inflo o arquivo xml responsavel por cada card na tela
        return ProdutoAdapterViewHolder(view, list, listener)
    }
    override fun onBindViewHolder(holder: ProdutoAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }
    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list: List<Produto>) {
        this.list.clear() //limpa a lista aqui
        this.list.addAll(list) //add a lista que chegou nela
        notifyDataSetChanged() // notifica o adapter que a lista que ele utiliza para renderizar mudou
    }


    class ProdutoAdapterViewHolder(
        itemView: View,
        var list: List<Produto>,
        var listener: ClickItemProdutoListener
    ) :
        RecyclerView.ViewHolder(itemView) {
    private val tvName = itemView.findViewById<TextView>(R.id.tv_Nome)
    private val etNumber = itemView.findViewById<EditText>(R.id.eTNumber)


        init {
            itemView.setOnClickListener {
                listener.clickItemProduto(list[adapterPosition])
            }
        }

        fun bind(produto: Produto) {
            tvName.text = produto.nome
            etNumber.inputType = produto.quantidade
        }
    }
}
