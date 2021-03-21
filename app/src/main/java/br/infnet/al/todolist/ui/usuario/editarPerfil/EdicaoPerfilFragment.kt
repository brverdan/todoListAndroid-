package br.infnet.al.todolist.ui.usuario.editarPerfil

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.infnet.al.todolist.R
import br.infnet.al.todolist.model.Usuario
import br.infnet.al.todolist.ui.usuario.perfil.PerfilUsuarioViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class EdicaoPerfilFragment : Fragment() {
    private lateinit var viewModel: EdicaoPerfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edicao_perfil_fragment, container, false)

        val edicaoPerfilViewModelFactory = EdicaoPerfilViewModelFactory(requireActivity().application)

        viewModel = ViewModelProvider(this, edicaoPerfilViewModelFactory).get(EdicaoPerfilViewModel::class.java)

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if (it) {
               findNavController().popBackStack()
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var imgFotoPerfilEditar = requireView().findViewById<ImageView>(R.id.imgFotoPerfilEditar)
        var edtInputNomeEditarPerfil = requireView().findViewById<TextInputEditText>(R.id.edtInputNomeEditarPerfil)
        var edtInputSenhaEditarPerfil = requireView().findViewById<TextInputEditText>(R.id.edtInputSenhaEditarPerfil)

        var btnEditarPerfilUsuario = view.findViewById<Button>(R.id.btnEditarPerfilUsuario)
        btnEditarPerfilUsuario.setOnClickListener {
            var nome = edtInputNomeEditarPerfil.text.toString()
            var senha = edtInputSenhaEditarPerfil.text.toString()
            if(!nome.isNullOrBlank() || !senha.isNullOrBlank() || imgFotoPerfilEditar != null) {
                viewModel.editarPerfil(nome, senha)
            }
            else
            {
                makeToast("Algum campo precisa ser preenchido.")
            }
        }

        imgFotoPerfilEditar.setOnClickListener {
            tirarFoto()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            var imageBitmap = data!!.extras!!.get("data") as Bitmap
            var imgFotoPerfilEditar = requireView().findViewById<ImageView>(R.id.imgFotoPerfilEditar)
            imgFotoPerfilEditar.setImageBitmap(imageBitmap)
            viewModel.alterarImagemPerfil(imageBitmap)
        }
    }

    private fun tirarFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //startActivityForResult(intent, 200)
        //if(intent.resolveActivity(requireActivity().packageManager) != null){
        startActivityForResult(intent, 200)
        //}
        //else {
        //   makeToast("Nenhum recurso disponível.")
        //}
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }
}