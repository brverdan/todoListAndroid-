package br.infnet.al.todolist.ui.todo.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.infnet.al.todolist.R
import br.infnet.al.todolist.adapter.RecyclerViewTodoList
import br.infnet.al.todolist.database.AppUtil.tarefaSelecionada
import br.infnet.al.todolist.database.AppUtil.todoSelecionada
import br.infnet.al.todolist.database.TodoDaoImp
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListTodoFragment : Fragment() {
    private lateinit var viewModel: ListTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var buttonNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        buttonNavigationView.visibility = BottomNavigationView.VISIBLE

        var view = inflater.inflate(R.layout.list_todo_fragment, container, false)

        var recyclerViewTodoList = view.findViewById<RecyclerView>(R.id.recyclerViewTodoList)

        val listTodoViewModelFactory = ListTodoViewModelFactory(TodoDaoImp())

        viewModel = ViewModelProvider(this, listTodoViewModelFactory).get(ListTodoViewModel::class.java)

        viewModel.todo.observe(viewLifecycleOwner, Observer {
            recyclerViewTodoList.adapter = RecyclerViewTodoList(it) {
                todoSelecionada = it
                findNavController().navigate(R.id.detalhesTodoFragment)
            }
            recyclerViewTodoList.layoutManager = LinearLayoutManager(requireContext())
        })

        viewModel.atualizarListaTodos()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var fabAdicionarTodo = view.findViewById<FloatingActionButton>(R.id.fabAdicionarTodo)
        fabAdicionarTodo.setOnClickListener {
            todoSelecionada = null
            tarefaSelecionada = null
            findNavController().navigate(R.id.cadastroTodoFragment)
        }

        var btnTarefasApi = view.findViewById<Button>(R.id.btnTarefasApi)
        btnTarefasApi.setOnClickListener {
            findNavController().navigate(R.id.listApiFragment)
        }
    }

}