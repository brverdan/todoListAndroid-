package br.infnet.al.todolist.ui.compras.cadastro

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.infnet.al.todolist.R
import br.infnet.al.todolist.database.AppUtil.itemSelecionado
import br.infnet.al.todolist.database.CompraDaoImp
import br.infnet.al.todolist.model.Item
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class CadastroCompraFragment : Fragment() {
    private lateinit var viewModel: CadastroCompraViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.cadastro_compra_fragment, container, false)

        val cadastroCompraViewModelFactory = CadastroCompraViewModelFactory(CompraDaoImp())

        viewModel = ViewModelProvider(this, cadastroCompraViewModelFactory).get(CadastroCompraViewModel::class.java)

        viewModel.let {
            it.message.observe(viewLifecycleOwner, Observer { msg ->
                if (!msg.isNullOrBlank()) {
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                }
            })
            it.status.observe(viewLifecycleOwner, Observer {
                if (it) {
                    findNavController().popBackStack()
                }
            })

        return view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val quantidade = view.findViewById<TextView>(R.id.txtQtdItemCadastrarCompra)

        if(itemSelecionado != null) {
            preencherFormulario(itemSelecionado!!)
        }

        var btnAdicionarQtdItemCadastrarCompra = view.findViewById<Button>(R.id.btnAdicionarQtdItemCadastrarCompra)
        btnAdicionarQtdItemCadastrarCompra.setOnClickListener {
            viewModel.adicionarQtdItem(quantidade.text.toString().toInt())
            viewModel.count.observe(viewLifecycleOwner, Observer {
                quantidade.text = it.toString()
            })
        }

        var btnDiminuirQtdItemCadastrarCompra = view.findViewById<Button>(R.id.btnDiminuirQtdItemCadastrarCompra)
        btnDiminuirQtdItemCadastrarCompra.setOnClickListener {
            viewModel.diminuirQtdItem(quantidade.text.toString().toInt())
            viewModel.count.observe(viewLifecycleOwner, Observer {
                quantidade.text = it.toString()
            })
        }

        var fabSalvarItemCadastrarCompra = view.findViewById<FloatingActionButton>(R.id.fabSalvarItemCadastrarCompra)
        fabSalvarItemCadastrarCompra.setOnClickListener {
            val nomeItem = view.findViewById<TextInputEditText>(R.id.edtNomeItemCadastrarCompra).text.toString()
            val itemComprado = view.findViewById<CheckBox>(R.id.checkBoxItemCompradoCadastrarCompra).isChecked

            viewModel.SalvarItem(nomeItem, quantidade.text.toString().toInt(), itemComprado)
        }
    }

    private fun preencherFormulario(item: Item) {
        var edtNomeItemCadastrarCompra = requireView().findViewById<TextInputEditText>(R.id.edtNomeItemCadastrarCompra)
        var txtQtdItemCadastrarCompra = requireView().findViewById<TextView>(R.id.txtQtdItemCadastrarCompra)
        var checkBoxItemCompradoCadastrarCompra = requireView().findViewById<CheckBox>(R.id.checkBoxItemCompradoCadastrarCompra)

        edtNomeItemCadastrarCompra.setText(item.nomeItem)
        txtQtdItemCadastrarCompra.text = item.quantidade.toString()
        checkBoxItemCompradoCadastrarCompra.isChecked = item.itemComprado
    }
}