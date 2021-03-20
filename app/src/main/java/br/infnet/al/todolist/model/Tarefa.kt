package br.infnet.al.todolist.model

class Tarefa (
    var id: String? = null,
    var descricao: String? = null
) {
    override fun toString(): String {
        return "$descricao"
    }
}