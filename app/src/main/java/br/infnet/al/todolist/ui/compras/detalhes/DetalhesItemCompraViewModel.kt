package br.infnet.al.todolist.ui.compras.detalhes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.infnet.al.todolist.database.AppUtil.itemSelecionado
import br.infnet.al.todolist.database.CompraDao
import br.infnet.al.todolist.model.Item

class DetalhesItemCompraViewModel(private val compraDao: CompraDao) : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item> = _item

    fun receberItem() {
        compraDao.read(itemSelecionado!!.documentId!!).addOnSuccessListener {
            _item.value = it.toObject(Item::class.java)
        }
        .addOnFailureListener {
            Log.i("FirebaseFirestore", "${it.message}")
        }
    }

    fun deletarItem(item: Item) {
        _status.value = false
        _message.value = "Por favor, aguarde a persistência!"

        try  {
            compraDao.delete(item)
            _status.value = true
            _message.value = "Persistência realizada!"
        }
        catch (e: Exception) {
            _message.value = "Persistência falhou! " + e.message
        }
    }
}