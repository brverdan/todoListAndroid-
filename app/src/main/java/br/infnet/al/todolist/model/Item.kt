package br.infnet.al.todolist.model

import com.google.firebase.firestore.DocumentId

class Item (
    var nomeItem: String? = null,
    var quantidade: Int? = null,
    var itemComprado: Boolean = false,

    @DocumentId
    var documentId: String? = null
)