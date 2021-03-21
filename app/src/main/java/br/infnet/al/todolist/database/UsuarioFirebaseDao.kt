package br.infnet.al.todolist.database

import android.graphics.Bitmap
import br.infnet.al.todolist.model.Usuario
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.Token
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.io.File

object UsuarioFirebaseDao {
    val firebaseAuth = FirebaseAuth.getInstance()
    val collection = FirebaseFirestore.getInstance().collection("usuarios")
    private val storage = FirebaseStorage.getInstance().reference.child("usuarios")

    fun cadastrarCredenciais(email: String, senha: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, senha)
    }

    fun cadastrarImagemPerfil(imagem: Bitmap, uid: String): UploadTask {
        val outPutStream = ByteArrayOutputStream()
        imagem.compress(Bitmap.CompressFormat.JPEG, 100, outPutStream)
        return storage.child("${uid}.jpeg").putBytes(outPutStream.toByteArray())
    }

    fun cadastrarPerfil(uid:String, nome:String): Task<Void> {
        return collection.document(uid).set(Usuario(nome))
    }

    fun verificarCredenciais(email: String, senha: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, senha)
    }

    fun logarFacebook(token: AccessToken): Task<AuthResult> {
        val credential = FacebookAuthProvider.getCredential(token.token)
        return firebaseAuth.signInWithCredential(credential)
    }

    fun logout() {
        firebaseAuth.signOut()
        LoginManager.getInstance().logOut()
    }

    fun consultarUsuario(): Task<DocumentSnapshot> {
        val firebaseUser = firebaseAuth.currentUser
        var retorno: Task<DocumentSnapshot>? = null
        if(firebaseUser.providerData[1].providerId == "facebook.com") {
            collection.document(firebaseUser.uid).set(Usuario(firebaseAuth.currentUser.displayName))
            return collection.document(firebaseUser.uid).get()
        }
       else {
            return collection.document(firebaseUser.uid).get()
        }
    }

    fun consultarImagem(uid: String, file: File): FileDownloadTask {
        return storage.child("${uid}.jpeg").getFile(file)
    }

    fun atualizarNomeESenha(nome: String, senha: String, uid: String): Task<Void> {
        firebaseAuth.currentUser.updatePassword(senha)
        return collection.document(uid).update("nome", nome)
    }

    fun atualizarNome(nome: String, uid: String): Task<Void> {
        return collection.document(uid).update("nome", nome)
    }
}