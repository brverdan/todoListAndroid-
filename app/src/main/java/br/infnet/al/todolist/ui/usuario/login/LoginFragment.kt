package br.infnet.al.todolist.ui.usuario.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.infnet.al.todolist.R
import br.infnet.al.todolist.database.UsuarioFirebaseDao
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText


class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private var callbackManager = CallbackManager.Factory.create();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        if(isLoggedIn) {
            UsuarioFirebaseDao.logout()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var buttonNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        buttonNavigationView.visibility = BottomNavigationView.GONE

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if(it) {
                findNavController().navigate(R.id.listTodoFragment)
            }
        })

        viewModel.message.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrBlank()) {
                makeToast(it)
            }
        })

        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCadastrarLogin = view.findViewById<Button>(R.id.btnLoginCadastrar)
        btnCadastrarLogin.setOnClickListener {
            findNavController().navigate(R.id.cadastrarFragment)
        }

        val edtEmailLogin = view.findViewById<TextInputEditText>(R.id.edtEmailLogin)
        val edtSenhaLogin = view.findViewById<TextInputEditText>(R.id.edtSenhaLogin)

        val btnLogar = view.findViewById<Button>(R.id.btnLogar)
        btnLogar.setOnClickListener {

            var email = edtEmailLogin.text.toString()
            var senha = edtSenhaLogin.text.toString()

            if(!email.isNullOrBlank() && !senha.isNullOrBlank()) {
                viewModel.logar(email, senha)
            }
            else {
                makeToast("Email e Senha obrigatórios.")
            }
        }

        var loginButton = view.findViewById(R.id.login_button) as LoginButton
        loginButton.setReadPermissions("email")
        loginButton.fragment = this

        // Callback registration
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                if (loginResult != null) {
                    viewModel.logarFacebook(loginResult.accessToken)

                    loginButton.visibility = LoginButton.GONE
                    btnCadastrarLogin.isEnabled = false
                    btnLogar.isEnabled = false
                    edtEmailLogin.isEnabled = false
                    edtSenhaLogin.isEnabled = false

                    makeToast("Entrando.....")
                }
            }

            override fun onCancel() {
                makeToast("Login cancelado  ")
            }

            override fun onError(exception: FacebookException) {
                makeToast("${exception.message}")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }
}