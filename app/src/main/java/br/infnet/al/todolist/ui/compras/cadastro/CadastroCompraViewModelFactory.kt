package br.infnet.al.todolist.ui.compras.cadastro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.infnet.al.todolist.database.CompraDao
import java.lang.IllegalArgumentException

class CadastroCompraViewModelFactory(private val compraDao: CompraDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastroCompraViewModel::class.java)) {
            return CadastroCompraViewModel(compraDao) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida!")
    }
}