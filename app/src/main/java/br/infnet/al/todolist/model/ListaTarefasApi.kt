package br.infnet.al.todolist.model

import com.google.gson.annotations.SerializedName

class ListaTarefasApi (
    @SerializedName(value = "tarefa")
    var tarefas: List<Tarefa>
)