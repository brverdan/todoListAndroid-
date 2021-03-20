package br.infnet.al.todolist.ui.compras.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.infnet.al.todolist.database.CompraDaoImp
import br.infnet.al.todolist.model.Item
import kotlinx.coroutines.launch

class ListComprasViewModel(private val compraDaoImp: CompraDaoImp) : ViewModel() {
    private val _compras = MutableLiveData<List<Item>>()
    val item: LiveData<List<Item>> = _compras

    fun atualizarListaCompras() {
        viewModelScope.launch {
            compraDaoImp.all()
                .addSnapshotListener { value, error ->
                    if(error != null) {
                        Log.i("FirebaseFirestore", "${error.message}")
                    }
                    else {
                        if(value != null && !value.isEmpty) {
                            _compras.value = value.toObjects(Item::class.java)
                        }
                    }
                }
        }
    }
}