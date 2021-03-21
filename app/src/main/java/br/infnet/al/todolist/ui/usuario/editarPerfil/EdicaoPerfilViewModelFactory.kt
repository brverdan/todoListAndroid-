package br.infnet.al.todolist.ui.usuario.editarPerfil

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class EdicaoPerfilViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EdicaoPerfilViewModel::class.java)) {
            return EdicaoPerfilViewModel(application) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida!")
    }
}