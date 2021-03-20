package br.infnet.al.todolist.ui.todo.detalhes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.infnet.al.todolist.database.TodoDao
import java.lang.IllegalArgumentException

class DetalhesTodoViewModelFactory(private val todoDao: TodoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalhesTodoViewModel::class.java)) {
            return DetalhesTodoViewModel(todoDao) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida!")
    }
}