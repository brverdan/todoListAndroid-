package br.infnet.al.todolist.database

import br.infnet.al.todolist.model.Item
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class CompraDaoImp: CompraDao {
    private val collection = FirebaseFirestore.getInstance().collection("compras")

    override fun insert(item: Item): Task<Void> {
        item.documentId = Random.nextLong(0, Long.MAX_VALUE).toString()
        return collection.document(item.documentId!!).set(item)
    }

    override fun read(documentId: String): Task<DocumentSnapshot> {
        return collection.document(documentId).get()
    }

    override fun find(key: Long): Item {
        return Item()
    }

    override fun update(item: Item): Task<Void> {
        return collection.document(item.documentId!!).set(item)
    }

    override fun all(): CollectionReference {
        return collection
    }

    override fun delete(item: Item): Task<Void> {
        return collection.document(item.documentId!!).delete()
    }
}