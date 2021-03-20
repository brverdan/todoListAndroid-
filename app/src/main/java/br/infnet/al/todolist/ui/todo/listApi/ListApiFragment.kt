package br.infnet.al.todolist.ui.todo.listApi

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.infnet.al.todolist.R
import br.infnet.al.todolist.database.AppUtil
import br.infnet.al.todolist.database.AppUtil.todoSelecionada
import br.infnet.al.todolist.model.ListaTarefasApi
import br.infnet.al.todolist.model.Tarefa
import org.w3c.dom.Text
import java.lang.Appendable
import android.widget.Adapter as Adapter1

class ListApiFragment : Fragment() {
    private lateinit var viewModel: ListApiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.list_api_fragment, container, false)

        viewModel = ViewModelProvider(this).get(ListApiViewModel::class.java)

        val listTarefaApi = view.findViewById<ListView>(R.id.listTarefaApi)
        val txtListaTarefaApiVazia = view.findViewById<TextView>(R.id.txtListaTarefaApiVazia)
        viewModel.tarefaApi.observe(viewLifecycleOwner, Observer {
            if (!it.tarefas.isNullOrEmpty()) {
                listTarefaApi.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1 ,
                    it.tarefas)
                listTarefaApi.setOnItemClickListener { parent, view, position, id ->
                    AppUtil.tarefaSelecionada = it.tarefas[position]
                    todoSelecionada = null
                    findNavController().navigate(R.id.cadastroTodoFragment)
                }
            }
            else {
                txtListaTarefaApiVazia.visibility = TextView.VISIBLE
            }
        })

        viewModel.listarTarefasApi()

        return view
    }
}