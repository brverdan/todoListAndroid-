package br.infnet.al.todolist.ui.todo.detalhes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.infnet.al.todolist.database.AppUtil.todoSelecionada
import br.infnet.al.todolist.database.TodoDao
import br.infnet.al.todolist.model.Todo
import kotlinx.coroutines.launch

class DetalhesTodoViewModel(private val todoDao: TodoDao) : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _todo = MutableLiveData<Todo>()
    val todo: LiveData<Todo> = _todo

    fun receberTodo() {
        todoDao.read(todoSelecionada!!.documentId!!).addOnSuccessListener {
            _todo.value = it.toObject(Todo::class.java)
        }
        .addOnFailureListener {
            Log.i("FirebaseFirestore", "${it.message}")
        }
    }

    fun deletarTodo(todo: Todo) {
        _status.value = false
        _message.value = "Por favor, aguarde a persistência!"

        try  {
            todoDao.delete(todo)
            _status.value = true
            _message.value = "Persistência realizada!"
        }
        catch (e: Exception) {
            _message.value = "Persistência falhou! " + e.message
        }
    }
}