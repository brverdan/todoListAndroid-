package br.infnet.al.todolist.ui.todo.cadastro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.infnet.al.todolist.database.AppUtil.todoSelecionada
import br.infnet.al.todolist.database.TodoDao
import br.infnet.al.todolist.model.Todo

class CadastroTodoViewModel(private val todoDao: TodoDao) : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    init {
        _status.value = false
        _message.value = null
    }

    fun SalvarTodo(descricao: String, titulo: String, completada: Boolean) {
        _status.value = false
        _message.value = "Por favor, aguarde a persistência!"
        val todo = Todo(descricao, titulo, completada)
        if (todoSelecionada != null) {
            var todoArmazenada = todoSelecionada
            todoArmazenada!!.descricao = todo.descricao
            todoArmazenada!!.titulo = todo.titulo
            todoArmazenada!!.completada = todo.completada

            todoDao.update(todoSelecionada!!).addOnSuccessListener {
                _status.value = true
                _message.value = "Persistência realizada!"
            }
            .addOnFailureListener {
                _message.value = "Persistência falhou! " + it.message
            }
        } else {
            todoDao.insert(todo).addOnSuccessListener {
                _status.value = true
                _message.value = "Persistência realizada!"
            }
                .addOnFailureListener {
                    _message.value = "Persistência falhou! " + it.message
                }
        }
    }
}