package br.infnet.al.todolist.ui.usuario.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.infnet.al.todolist.database.UsuarioFirebaseDao
import com.facebook.AccessToken

class LoginViewModel : ViewModel() {
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun logar(email: String, senha: String) {
        UsuarioFirebaseDao.verificarCredenciais(email, senha)
            .addOnSuccessListener {
                _status.value = true
            }
            .addOnFailureListener {
                _message.value = it.message
            }
    }

    fun logarFacebook(token: AccessToken) {
        UsuarioFirebaseDao.logarFacebook(token)
            .addOnSuccessListener {
                _status.value = true
            }
            .addOnFailureListener {
                _message.value = it.message
            }
    }
}