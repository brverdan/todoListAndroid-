package br.infnet.al.todolist.ui.usuario.perfil

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.infnet.al.todolist.R
import br.infnet.al.todolist.database.UsuarioFirebaseDao
import br.infnet.al.todolist.model.Usuario

class PerfilUsuarioFragment : Fragment() {
    private lateinit var viewModel: PerfilUsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.perfil_usuario_fragment, container, false)

        val PerfilUsuarioViewModelFactory = PerfilUsuarioViewModelFactory(requireActivity().application)

        viewModel = ViewModelProvider(this, PerfilUsuarioViewModelFactory).get(PerfilUsuarioViewModel::class.java)

        viewModel.usuario.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                preencherInformacoes(it)
            }
            else {
                if(UsuarioFirebaseDao.firebaseAuth.currentUser == null){
                    findNavController().navigate(R.id.loginFragment)
                    limparInformacoes()
                }
            }
        })

        viewModel.imagemPerfil.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                requireView().findViewById<ImageView>(R.id.imgFotoPerfilUsuario).setImageURI(it)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var btnLogout = view.findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun limparInformacoes() {
        var nome = requireView().findViewById<EditText>(R.id.edtNomePerfilUsuario)
        var email = requireView().findViewById<EditText>(R.id.edtEmailPerfilUsuario)

        nome.text = null
        email.text = null
    }

    private fun preencherInformacoes(usuario: Usuario) {
        var nome = requireView().findViewById<EditText>(R.id.edtNomePerfilUsuario)
        var email = requireView().findViewById<EditText>(R.id.edtEmailPerfilUsuario)

        nome.setText(usuario.nome)
        email.setText(usuario.firebaseAuth!!.email)
    }
}