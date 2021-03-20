package br.infnet.al.todolist.ui.compras.cadastro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.infnet.al.todolist.database.AppUtil.itemSelecionado
import br.infnet.al.todolist.database.CompraDao
import br.infnet.al.todolist.model.Item

class CadastroCompraViewModel(private val compraDao: CompraDao) : ViewModel() {

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    init {
        _status.value = false
        _message.value = null
        _count.value = 0
    }

    fun SalvarItem(nomeItem: String, quantidade: Int, itemComprado: Boolean) {
        _status.value = false
        _message.value = "Por favor, aguarde a persistência!"
        val compra = Item(nomeItem, quantidade, itemComprado)
        if (itemSelecionado != null) {
            var compraArmazenada = itemSelecionado
            compraArmazenada!!.nomeItem = compra.nomeItem
            compraArmazenada!!.quantidade = compra.quantidade
            compraArmazenada!!.itemComprado = compra.itemComprado

            compraDao.update(itemSelecionado!!).addOnSuccessListener {
                _status.value = true
                _message.value = "Persistência realizada!"
            }
                .addOnFailureListener {
                    _message.value = "Persistência falhou! " + it.message
                }
        } else {
            compraDao.insert(compra).addOnSuccessListener {
                _status.value = true
                _message.value = "Persistência realizada!"
            }
                .addOnFailureListener {
                    _message.value = "Persistência falhou! " + it.message
                }
        }
    }

    fun adicionarQtdItem(qtdItem: Int) {
        if(qtdItem != 0) {
            _count.value = qtdItem + 1
        }
        else {
            _count.value = 0
            _count.value = _count.value!! + 1
        }
    }

    fun diminuirQtdItem(qtdItem: Int) {
        if(qtdItem != 0) {
            _count.value = qtdItem - 1
        }
        else {
            _count.value = 0
            _count.value = _count.value!! - 1
        }
    }
}