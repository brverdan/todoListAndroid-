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

    val app = application

    private val _imagemPerfil = MutableLiveData<Uri>()
    var imagemPerfil: LiveData<Uri> = _imagemPerfil

    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> = _usuario

    init {
        _status.value = false
        _message.value = null

        UsuarioFirebaseDao
            .consultarUsuario()
            .addOnSuccessListener {
                val usuario = it.toObject(Usuario::class.java)
                usuario!!.firebaseAuth = UsuarioFirebaseDao.firebaseAuth.currentUser
                _usuario.value = usuario!!
                if(UsuarioFirebaseDao.firebaseAuth.currentUser.providerData[1].providerId != FacebookAuthProvider.PROVIDER_ID) {
                    val file = File(app.cacheDir, "userTemp.jpeg")
                    UsuarioFirebaseDao
                        .consultarImagem(usuario.uid!!, file)
                        .addOnSuccessListener {
                            _imagemPerfil.value = file.toUri()
                        }
                        .addOnFailureListener {
                            Log.i("UploadImagem", "${it.message}")
                        }
                }
                else {
                    _imagemPerfil.value = UsuarioFirebaseDao.firebaseAuth.currentUser.providerData[1].photoUrl
                }
            }
    }

    fun editarPerfil(nome: String, senha: String) {
        var userId = UsuarioFirebaseDao.firebaseAuth.currentUser.uid
        if (fotoPerfil != null && !senha.isNullOrBlank()) {
            UsuarioFirebaseDao
                .cadastrarImagemPerfil(fotoPerfil!!, userId)
                    .addOnSuccessListener {
                        atualizarNomeESenha(nome, senha, userId)
                    }
                    .addOnFailureListener {
                        Log.i("FotoEditarPerfil", "${it.message}")
                    }

        }
        else if(!senha.isNullOrBlank()) {
            atualizarNomeESenha(nome, senha, userId)
        }
        else if(fotoPerfil != null) {
            UsuarioFirebaseDao
                .cadastrarImagemPerfil(fotoPerfil!!, userId)
                .addOnSuccessListener {
                    atualizarNome(nome, userId)
                }
                .addOnFailureListener {
                    Log.i("FotoEditarPerfil", "${it.message}")
                }
        }
        else {
            atualizarNome(nome, userId)
        }
    }

    fun atualizarNomeESenha(nome: String, senha: String, userId: String) {
        UsuarioFirebaseDao.atualizarNomeESenha(nome, senha, userId)
            .addOnSuccessListener {
                _status.value = true
            }
            .addOnFailureListener {
                Log.i("EditarPerfil", "${it.message}")
            }
    }

    fun atualizarNome(nome: String, userId: String) {
        UsuarioFirebaseDao.atualizarNome(nome, userId)
            .addOnSuccessListener {
                _status.value = true
            }
            .addOnFailureListener {
                Log.i("EditarPerfil", "${it.message}")
            }
    }

    fun alterarImagemPerfil(img: Bitmap) {
        fotoPerfil = img
    }
}