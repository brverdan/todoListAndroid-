package br.infnet.al.todolist.model

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentId

class Usuario (
    var nome: String? = null,
    var firebaseAuth: FirebaseUser? = null,

    @DocumentId
    var uid: String? = null
)
