package br.infnet.al.todolist.ui.compras.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.infnet.al.todolist.R
import br.infnet.al.todolist.adapter.RecyclerViewComprasList
import br.infnet.al.todolist.database.AppUtil.itemSelecionado
import br.infnet.al.todolist.database.CompraDaoImp
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListComprasFragment : Fragment() {
    private lateinit var viewModel: ListComprasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.list_compras_fragment, container, false)

        var recyclerViewComprasList = view.findViewById<RecyclerView>(R.id.recyclerViewCompraList)

        val listComprasViewModelFactory = ListComprasViewModelFactory(CompraDaoImp())

        viewModel = ViewModelProvider(this, listComprasViewModelFactory).get(ListComprasViewModel::class.java)

        viewModel.item.observe(viewLifecycleOwner, Observer {
            recyclerViewComprasList.adapter = RecyclerViewComprasList(it) {
                itemSelecionado = it
                findNavController().navigate(R.id.detalhesItemCompraFragment)
            }
            recyclerViewComprasList.layoutManager = LinearLayoutManager(requireContext())
        })

        viewModel.atualizarListaCompras()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var fabCadastrarCompra = view.findViewById<FloatingActionButton>(R.id.fabCadastrarCompra)
        fabCadastrarCompra.setOnClickListener {
            itemSelecionado = null
            findNavController().navigate(R.id.cadastroCompraFragment)
        }
    }
}