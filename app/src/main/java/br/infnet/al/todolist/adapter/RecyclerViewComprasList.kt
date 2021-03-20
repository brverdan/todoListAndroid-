package br.infnet.al.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.infnet.al.todolist.R
import br.infnet.al.todolist.model.Item

class RecyclerViewComprasList(private val items: List<Item>,
                              val actionClick: (Item) -> Unit
) : RecyclerView.Adapter<RecyclerViewComprasList.ComprasViewHolder>() {

    class ComprasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNomeItemCompraRecyclerList: TextView = itemView.findViewById(R.id.txtNomeItemCompraRecyclerList)
        val txtQtdItemCompraRecyclerList: TextView = itemView.findViewById(R.id.txtQtdItemCompraRecyclerList)
        val checkBoxItemCompradoRecylcerList: CheckBox = itemView.findViewById(R.id.checkBoxItemCompradoRecylcerList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComprasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_compras, parent, false)
        return ComprasViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ComprasViewHolder, position: Int) {
        val compra = items[position]
        holder.txtNomeItemCompraRecyclerList.text = compra.nomeItem
        holder.txtQtdItemCompraRecyclerList.text = compra.quantidade.toString()
        holder.checkBoxItemCompradoRecylcerList.isChecked = compra.itemComprado

        holder.itemView.setOnClickListener {
            actionClick(compra)
        }
    }
}