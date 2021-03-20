package br.infnet.al.todolist.database

import br.infnet.al.todolist.model.Todo
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query

interface TodoDao {

    fun insert(todo: Todo) : Task<Void>

    fun read(documentId: String): Task<DocumentSnapshot>

    fun find(key: Long): Todo

    fun update(todo: Todo): Task<Void>

    fun all(): Query

    fun delete(todo: Todo): Task<Void>
}