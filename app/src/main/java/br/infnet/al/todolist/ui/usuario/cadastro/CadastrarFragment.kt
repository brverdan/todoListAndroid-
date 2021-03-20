package br.infnet.al.todolist.ui.usuario.cadastro

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
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.infnet.al.todolist.R
import com.google.android.material.textfield.TextInputEditText

class CadastrarFragment : Fragment() {
    private lateinit var viewModel: CadastrarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CadastrarViewModel::class.java)

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if(it) {
                findNavController().popBackStack()
            }
        })

        viewModel.message.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrBlank()) {
                makeToast(it)
            }
        })

        return inflater.inflate(R.layout.cadastrar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var btnCadastrar = view.findViewById<Button>(R.id.btnCadastrar)
        btnCadastrar.setOnClickListener {
            val senha = view.findViewById<TextInputEditText>(R.id.edtSenhaCadastrar).text.toString()
            val repetirSenha = view.findViewById<TextInputEditText>(R.id.edtResenhaCadastrar).text.toString()
            val email = view.findViewById<TextInputEditText>(R.id.edtEmailCadastrar).text.toString()
            val nome = view.findViewById<TextInputEditText>(R.id.edtNomeCadastrar).text.toString()

            if(!email.isNullOrBlank() && !senha.isNullOrBlank() && !repetirSenha.isNullOrBlank()) {
                if(senha == repetirSenha) {
                    viewModel.salvarCadastro(email, senha, nome)
                }
                else
                {
                    makeToast("Senhas não conferem")
                }
            }
            else {
                makeToast("Todos os campos devem ser preenchidos")
            }
        }

        var imgFotoPerfilCadastrar = view.findViewById<ImageView>(R.id.imgFotoPerfilCadastrar)
        imgFotoPerfilCadastrar.setOnClickListener {
            tirarFoto()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            var imageBitmap = data!!.extras!!.get("data") as Bitmap
            var imgFotoPerfilCadastrar = requireView().findViewById<ImageView>(R.id.imgFotoPerfilCadastrar)
            imgFotoPerfilCadastrar.setImageBitmap(imageBitmap)
            viewModel.alterarImagemPerfil(imageBitmap)
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }
}