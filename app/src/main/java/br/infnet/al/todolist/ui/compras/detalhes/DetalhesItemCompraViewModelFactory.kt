package br.infnet.al.todolist.ui.compras.detalhes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.infnet.al.todolist.database.CompraDao
import java.lang.IllegalArgumentException

class DetalhesItemCompraViewModelFactory(private val compraDao: CompraDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalhesItemCompraViewModel::class.java)) {
            return DetalhesItemCompraViewModel(compraDao) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida!")
    }
}