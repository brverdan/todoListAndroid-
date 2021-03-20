package br.infnet.al.todolist.database

import br.infnet.al.todolist.model.Item
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot

interface CompraDao {

    fun insert(item: Item) : Task<Void>

    fun read(documentId: String): Task<DocumentSnapshot>

    fun find(key: Long): Item

    fun update(item: Item): Task<Void>

    fun all(): CollectionReference

    fun delete(item: Item): Task<Void>
}