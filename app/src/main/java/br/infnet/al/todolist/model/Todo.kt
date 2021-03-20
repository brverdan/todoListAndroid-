package br.infnet.al.todolist.model

import com.google.firebase.firestore.DocumentId

class Todo (
    var descricao: String? = null,
    var titulo: String? = null,
    var completada: Boolean = false,
    var userId: String? = null,

    @DocumentId
    var documentId: String? = null
) {

    override fun toString(): String {
        return "$titulo"
    }
}