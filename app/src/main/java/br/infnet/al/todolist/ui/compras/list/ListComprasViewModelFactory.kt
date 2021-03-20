package br.infnet.al.todolist.ui.compras.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.infnet.al.todolist.database.CompraDaoImp
import java.lang.IllegalArgumentException

class ListComprasViewModelFactory(private val compraDaoImp: CompraDaoImp) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListComprasViewModel::class.java)) {
            return ListComprasViewModel(compraDaoImp) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida!")
    }
}