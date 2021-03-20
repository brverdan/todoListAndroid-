package br.infnet.al.todolist.database.api

import br.infnet.al.todolist.database.api.service.TarefaApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private var instance: Retrofit? = null

    private fun getRetrofit(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                        .baseUrl("http://infnet.educacao.ws")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
        }
        return instance as Retrofit
    }

    fun getTarefaService(): TarefaApiService {
        return getRetrofit().create(TarefaApiService::class.java)
    }
}