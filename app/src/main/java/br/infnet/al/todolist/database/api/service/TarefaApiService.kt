package br.infnet.al.todolist.database.api.service

import br.infnet.al.todolist.model.ListaTarefasApi
import retrofit2.http.GET

interface TarefaApiService {

    //URL: http://infnet.educacao.ws
    @GET("dadosAtividades.php")
    suspend fun all(): ListaTarefasApi
}