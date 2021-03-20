package br.infnet.al.todolist.ui.todo.cadastro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.infnet.al.todolist.database.TodoDao
import java.lang.IllegalArgumentException

class CadastroTodoViewModelFactory(private val todoDao: TodoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastroTodoViewModel::class.java)) {
            return CadastroTodoViewModel(todoDao) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida!")
    }
}