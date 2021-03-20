package br.infnet.al.todolist.ui.todo.listApi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.infnet.al.todolist.database.api.ApiClient
import br.infnet.al.todolist.model.ListaTarefasApi
import kotlinx.coroutines.launch

class ListApiViewModel : ViewModel() {

    private val _tarefasApi = MutableLiveData<ListaTarefasApi>()
    var tarefaApi: LiveData<ListaTarefasApi> = _tarefasApi


    fun listarTarefasApi() {
        viewModelScope.launch {
            try {
                _tarefasApi.value = ApiClient.getTarefaService().all()
            }
            catch (e: Exception) {
                Log.i("Api", "${e.message}")
            }
        }
    }
}