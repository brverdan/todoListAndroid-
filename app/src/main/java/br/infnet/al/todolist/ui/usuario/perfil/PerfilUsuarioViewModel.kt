package br.infnet.al.todolist.ui.usuario.perfil

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.infnet.al.todolist.database.UsuarioFirebaseDao
import br.infnet.al.todolist.model.Usuario
import com.google.firebase.auth.FacebookAuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import java.io.File
import kotlin.random.Random

class PerfilUsuarioViewModel(application: Application) : AndroidViewModel(application) {

    val app = application

    private val _facebookProvider = MutableLiveData<Boolean>()
    var facebookProvider: LiveData<Boolean> = _facebookProvider

    private val _imagemPerfil = MutableLiveData<Uri>()
    var imagemPerfil: LiveData<Uri> = _imagemPerfil

    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> = _usuario

    fun receberUsuario() {
        UsuarioFirebaseDao
            .consultarUsuario()
            .addOnSuccessListener {
                val usuario = it.toObject(Usuario::class.java)
                usuario!!.firebaseAuth = UsuarioFirebaseDao.firebaseAuth.currentUser
                _usuario.value = usuario!!
                if(UsuarioFirebaseDao.firebaseAuth.currentUser.providerData[1].providerId != FacebookAuthProvider.PROVIDER_ID) {
                    val file = File(app.cacheDir, "${Random.nextInt(0, Int.MAX_VALUE)}.jpeg")
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

    fun verificarProvider() {
        if(UsuarioFirebaseDao.firebaseAuth.currentUser.providerData[1].providerId == FacebookAuthProvider.PROVIDER_ID) {
            _facebookProvider.value = true
        }
    }

    fun logout() {
        UsuarioFirebaseDao.logout()
        _usuario.value = null
    }
}