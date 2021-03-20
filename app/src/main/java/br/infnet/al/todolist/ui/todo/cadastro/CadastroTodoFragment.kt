package br.infnet.al.todolist.ui.todo.cadastro

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.infnet.al.todolist.R
import br.infnet.al.todolist.database.AppUtil
import br.infnet.al.todolist.database.TodoDaoImp
import br.infnet.al.todolist.model.Tarefa
import br.infnet.al.todolist.model.Todo
import com.google.android.material.textfield.TextInputEditText

class CadastroTodoFragment : Fragment() {
    private lateinit var viewModel: CadastroTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.cadastro_todo_fragment, container, false)

        val cadastroTodoViewModelFactory = CadastroTodoViewModelFactory(TodoDaoImp())
        viewModel = ViewModelProvider(this, cadastroTodoViewModelFactory).get(CadastroTodoViewModel::class.java)

        viewModel.let {
            it.message.observe(viewLifecycleOwner, Observer { msg ->
                if (!msg.isNullOrBlank()) {
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                }
            })
            it.status.observe(viewLifecycleOwner, Observer {
                if (it) {
                    findNavController().popBackStack()
                }
            })

            var txtLabelCadastrarTodo = view.findViewById<TextView>(R.id.txtLabelCadastrarTodo)
            txtLabelCadastrarTodo.text = "Criar Tarefa"

            return view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        verificarUtils()

        var btnCriarTodo = view.findViewById<Button>(R.id.btnCriarTodo)
        btnCriarTodo.setOnClickListener {
            val descricao = view.findViewById<TextInputEditText>(R.id.edtDescricaoCadastrarTodo).text.toString()
            val titulo = view.findViewById<TextInputEditText>(R.id.edtTituloCadastrarTodo).text.toString()
            val completada = view.findViewById<CheckBox>(R.id.checkBoxCompletadaCadastroTodo).isChecked

            viewModel.SalvarTodo(descricao, titulo, completada)
        }
    }

    private fun verificarUtils() {
        var txtLabelCadastrarTodo = requireView().findViewById<TextView>(R.id.txtLabelCadastrarTodo)

        if (AppUtil.todoSelecionada != null && AppUtil.tarefaSelecionada == null) {
            txtLabelCadastrarTodo.text = "Atualizar tarefa"
            preencherFormulario(AppUtil.todoSelecionada!!)
        }
        else if (AppUtil.todoSelecionada == null && AppUtil.tarefaSelecionada != null) {
            var tarefa = AppUtil.tarefaSelecionada

            var edtDescricaoCadastrarTodo =
                requireView().findViewById<TextInputEditText>(R.id.edtDescricaoCadastrarTodo)

            edtDescricaoCadastrarTodo.setText(tarefa!!.descricao)
        }
    }

    private fun preencherFormulario(todo: Todo) {
        var edtDescricaoCadastrarTodo = requireView().findViewById<TextInputEditText>(R.id.edtDescricaoCadastrarTodo)
        var edtTituloCadastrarTodo = requireView().findViewById<TextInputEditText>(R.id.edtTituloCadastrarTodo)
        var edtCompletadaCadastrarTodo = requireView().findViewById<CheckBox>(R.id.checkBoxCompletadaCadastroTodo)

        edtDescricaoCadastrarTodo.setText(todo.descricao)
        edtTituloCadastrarTodo.setText(todo.titulo)
        edtCompletadaCadastrarTodo.isChecked = todo.completada
    }
}