package br.infnet.al.todolist.ui.todo.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.infnet.al.todolist.database.TodoDao
import java.lang.IllegalArgumentException

class ListTodoViewModelFactory(private val todoDao: TodoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListTodoViewModel::class.java)) {
            return ListTodoViewModel(todoDao) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida!")
    }

}