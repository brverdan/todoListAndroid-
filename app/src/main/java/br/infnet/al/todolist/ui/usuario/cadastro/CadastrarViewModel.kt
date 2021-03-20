package br.infnet.al.todolist.ui.usuario.cadastro

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.infnet.al.todolist.database.UsuarioFirebaseDao

class CadastrarViewModel : ViewModel() {
    private var fotoPerfil: Bitmap? = null

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun salvarCadastro(email: String, senha: String, nome: String) {
        UsuarioFirebaseDao.cadastrarCredenciais(email, senha)
            .addOnSuccessListener {
                val uid = it.user.uid
                UsuarioFirebaseDao.cadastrarPerfil(uid, nome)
                    .addOnSuccessListener {
                        if(fotoPerfil != null) {
                            UsuarioFirebaseDao
                                .cadastrarImagemPerfil(fotoPerfil!!, uid)
                                .addOnSuccessListener {
                                    _status.value = true
                                    _message.value = "Usuário cadastrado com sucesso"
                                }
                                .addOnFailureListener {
                                    Log.i("FirebaseStorage", "${it.message}")
                                }
                        }
                        else{
                            _status.value = true
                            _message.value = "Usuário cadastrado com sucesso"
                        }
                    }
                    .addOnFailureListener {
                        _message.value = it.message
                        Log.i("FirebaseStorage", "${it.message}")
                    }
            }
            .addOnFailureListener {
                _message.value = it.message
                Log.i("FirebaseAuth", "${it.message}")
            }
    }

    fun alterarImagemPerfil(img: Bitmap) {
        fotoPerfil = img
    }
}