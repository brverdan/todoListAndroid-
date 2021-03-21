package br.infnet.al.todolist.database

import br.infnet.al.todolist.model.Todo
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlin.random.Random

class TodoDaoImp: TodoDao {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val collection = FirebaseFirestore.getInstance().collection("todos")

    override fun insert(todo: Todo): Task<Void> {
        todo.documentId = Random.nextLong(0, Long.MAX_VALUE).toString()
        todo.userId = firebaseAuth.currentUser.uid
        return collection.document(todo.documentId!!).set(todo)
    }

    override fun read(documentId: String): Task<DocumentSnapshot> {
        return collection.document(documentId).get()
    }

    override fun find(key: Long): Todo {
        return Todo()
    }

    override fun update(todo: Todo): Task<Void> {
        return collection.document(todo.documentId!!).set(todo)
    }

    override fun all(): Query {
        return collection.whereEqualTo("userId", firebaseAuth.currentUser.uid)
    }

    override fun delete(todo: Todo): Task<Void> {
        return collection.document(todo.documentId!!).delete()
    }


}