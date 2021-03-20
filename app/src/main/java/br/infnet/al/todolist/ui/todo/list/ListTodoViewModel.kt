package br.infnet.al.todolist.ui.todo.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.infnet.al.todolist.database.TodoDao
import br.infnet.al.todolist.model.Todo
import kotlinx.coroutines.launch

class ListTodoViewModel(private val todoDao: TodoDao) : ViewModel() {

    private val _todos = MutableLiveData<List<Todo>>()
    val todo: LiveData<List<Todo>> = _todos

    fun atualizarListaTodos() {
        viewModelScope.launch {
            todoDao.all()
                .addSnapshotListener { value, error ->
                    if(error != null) {
                        Log.i("FirebaseFirestore", "${error.message}")
                    }
                    else {
                        if(value != null && !value.isEmpty) {
                            _todos.value = value.toObjects(Todo::class.java)
                        }
                    }
                }
        }
    }
}