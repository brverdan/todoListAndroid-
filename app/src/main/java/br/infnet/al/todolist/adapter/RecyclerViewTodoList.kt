package br.infnet.al.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.infnet.al.todolist.R
import br.infnet.al.todolist.model.Todo

class RecyclerViewTodoList (
    private val todos: List<Todo>,
    val actionClick: (Todo) -> Unit

) : RecyclerView.Adapter<RecyclerViewTodoList.TodosViewHolder>() {

    class TodosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTituloTodo: TextView = itemView.findViewById(R.id.txtTituloTodo)
        val checkBoxCompletada: CheckBox = itemView.findViewById(R.id.checkBoxCompletadaTodo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_todos, parent, false)
        return TodosViewHolder(view)
    }

    override fun getItemCount(): Int = todos.size

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        val todo = todos[position]
        if(todo.titulo!!.length > 15) {
            holder.txtTituloTodo.text = todo.titulo!!.slice(IntRange(0,15)) + "..."
        }
        else {
            holder.txtTituloTodo.text = todo.titulo
        }
        holder.checkBoxCompletada.isChecked = todo.completada

        holder.itemView.setOnClickListener {
            actionClick(todo)
        }
    }
}