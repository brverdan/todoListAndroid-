package br.infnet.al.todolist.ui.todo.detalhes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.infnet.al.todolist.R
import br.infnet.al.todolist.database.AppUtil.tarefaSelecionada
import br.infnet.al.todolist.database.AppUtil.todoSelecionada
import br.infnet.al.todolist.database.TodoDaoImp
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetalhesTodoFragment : Fragment() {
    private lateinit var viewModel: DetalhesTodoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val detalhesTodoViewModelFactory = DetalhesTodoViewModelFactory(TodoDaoImp())

        viewModel = ViewModelProvider(this, detalhesTodoViewModelFactory).get(DetalhesTodoViewModel::class.java)

        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            if (status) {
                findNavController().popBackStack()
            }
        })

        viewModel.receberTodo()

        return inflater.inflate(R.layout.detalhes_todo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.todo.observe(viewLifecycleOwner, Observer {
            var txtTitulo = view.findViewById<EditText>(R.id.edtLabelTituloDetalhesTodo)
            var txtDescricao = view.findViewById<EditText>(R.id.edtLabelDescricaoDetalhesTodo)
            var checkBoxCompletada = view.findViewById<CheckBox>(R.id.checkBoxCompletadaDetalhesTodo)

            txtTitulo.setText(it.titulo)
            txtDescricao.setText(it.descricao)
            checkBoxCompletada.isChecked = it.completada
        })

        var fabDeleteDetalhesTodo = view.findViewById<FloatingActionButton>(R.id.fabDeleteDetalhesTodo)
        fabDeleteDetalhesTodo.setOnClickListener {
            viewModel.deletarTodo(todoSelecionada!!)
        }

        var fabEditarDetalhesTodo = view.findViewById<FloatingActionButton>(R.id.fabEditarDetalhesTodo)
        fabEditarDetalhesTodo.setOnClickListener {
            tarefaSelecionada = null
            findNavController().navigate(R.id.cadastroTodoFragment)
        }
    }
}