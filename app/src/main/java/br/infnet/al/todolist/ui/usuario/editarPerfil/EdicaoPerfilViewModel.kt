package br.infnet.al.todolist.ui.usuario.editarPerfil

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.infnet.al.todolist.database.UsuarioFirebaseDao
import br.infnet.al.todolist.model.Usuario
import com.google.firebase.auth.FacebookAuthProvider
import java.io.File

class EdicaoPerfilViewModel(application: Application) : AndroidViewModel(application) {

    private var fotoPerfil: Bitmap? = null

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    init {
        _status.value = false
        _message.value = null
    }

    fun alterarImagemPerfil(img: Bitmap) {
        fotoPerfil = img
    }

    fun editarPerfil(nome: String, senha: String) {
        var userId = UsuarioFirebaseDao.firebaseAuth.currentUser.uid
        if (fotoPerfil != null && !senha.isNullOrBlank() && !nome.isNullOrBlank()) { //3 campos
            UsuarioFirebaseDao
                .cadastrarImagemPerfil(fotoPerfil!!, userId)
                    .addOnSuccessListener {
                        atualizarNomeESenha(nome, senha, userId)
                    }
                    .addOnFailureListener {
                        Log.i("FotoEditarPerfil", "${it.message}")
                    }

        }
        else if (fotoPerfil != null && !senha.isNullOrBlank() ) { // apenas o campo de foto e senha
            UsuarioFirebaseDao
                .cadastrarImagemPerfil(fotoPerfil!!, userId)
                .addOnSuccessListener {
                    atualizarSenha(senha)
                }
                .addOnFailureListener {
                    Log.i("FotoEditarPerfil", "${it.message}")
                }
        }
        else if (fotoPerfil != null && !nome.isNullOrBlank()) { // apenas o campo de foto e nome
            UsuarioFirebaseDao
                .cadastrarImagemPerfil(fotoPerfil!!, userId)
                .addOnSuccessListener {
                    atualizarNome(nome, userId)
                }
                .addOnFailureListener {
                    Log.i("FotoEditarPerfilSenha", "${it.message}")
                }
        }
        else if (!senha.isNullOrBlank() && !nome.isNullOrBlank()) { // apenas o campo de nome e senha
            atualizarNomeESenha(nome, senha, userId)
        }
        else if (!nome.isNullOrBlank()) { // apenas o campo de nome
            atualizarNome(nome, userId)
        }
        else if (!senha.isNullOrBlank()) { // apenas o campo de senha
            atualizarSenha(senha)
        }
        else { // apenas a foto
            atualizarFotoPerfil(userId)
        }
    }

    private fun atualizarSenha(senha: String) {
        UsuarioFirebaseDao.atualizarSenha(senha)
            .addOnSuccessListener {
                _status.value = true
            }
            .addOnFailureListener {
                Log.i("EditarPerfilSenha", "${it.message}")
            }
    }

    private fun atualizarFotoPerfil(userId: String) {
        UsuarioFirebaseDao
            .cadastrarImagemPerfil(fotoPerfil!!, userId)
            .addOnSuccessListener {
                _status.value = true
            }
            .addOnFailureListener {
                Log.i("FotoEditarPerfilSenha", "${it.message}")
            }
    }

    private fun atualizarNomeESenha(nome: String, senha: String, userId: String) {
        UsuarioFirebaseDao.atualizarNomeESenha(nome, senha, userId)
            .addOnSuccessListener {
                _status.value = true
            }
            .addOnFailureListener {
                Log.i("EditarPerfilNomeESenha", "${it.message}")
            }
    }

    private fun atualizarNome(nome: String, userId: String) {
        UsuarioFirebaseDao.atualizarNome(nome, userId)
            .addOnSuccessListener {
                _status.value = true
            }
            .addOnFailureListener {
                Log.i("EditarPerfilNome", "${it.message}")
            }
    }
}