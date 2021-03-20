package br.infnet.al.todolist.ui.compras.detalhes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.infnet.al.todolist.R
import br.infnet.al.todolist.database.AppUtil.itemSelecionado
import br.infnet.al.todolist.database.CompraDaoImp
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetalhesItemCompraFragment : Fragment() {
    private lateinit var viewModel: DetalhesItemCompraViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.detalhes_item_compra_fragment, container, false)

        var detalhesItemCompraViewModelFactory = DetalhesItemCompraViewModelFactory(CompraDaoImp())

        viewModel = ViewModelProvider(this, detalhesItemCompraViewModelFactory).get(DetalhesItemCompraViewModel::class.java)

        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            if (status) {
                findNavController().popBackStack()
            }
        })

        viewModel.receberItem()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.item.observe(viewLifecycleOwner, Observer {
            var edtNomeItem = view.findViewById<EditText>(R.id.edtNomeItemDetalhesItem)
            var quantidade = view.findViewById<TextView>(R.id.txtQtdItemDetalhesItem)
            var checkBoxItemComprado = view.findViewById<CheckBox>(R.id.checkBoxItemCompradoDetalhesItem)


            edtNomeItem.setText(it.nomeItem)
            quantidade.text = it.quantidade.toString()
            checkBoxItemComprado.isChecked = it.itemComprado
        })

        var fabDeletarItemDetalhesItem = view.findViewById<FloatingActionButton>(R.id.fabDeletarItemDetalhesItem)
        fabDeletarItemDetalhesItem.setOnClickListener {
            viewModel.deletarItem(itemSelecionado!!)
        }

        var fabEditarItemDetalhesItem = view.findViewById<FloatingActionButton>(R.id.fabEditarItemDetalhesItem)
        fabEditarItemDetalhesItem.setOnClickListener {
            findNavController().navigate(R.id.cadastroCompraFragment)
        }
    }
}
