package com.example.industrialstructurecasestudy.ui.listsAndCards

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.industrialstructurecasestudy.R
import com.example.industrialstructurecasestudy.databinding.FragmentCreateBoardBinding
import com.example.industrialstructurecasestudy.databinding.FragmentCreateListBinding
import com.example.industrialstructurecasestudy.repository.RemoteBoardRepository
import com.example.industrialstructurecasestudy.repository.RemoteOrganizationRepository
import com.example.industrialstructurecasestudy.ui.CreateOrganizationViewModel
import com.example.industrialstructurecasestudy.ui.board.CreateBoardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class CreateListFragment : Fragment() {

    private var _binding : FragmentCreateListBinding? = null

    private val scp : CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val listviewModel: CreateListViewModel by viewModels()

    @Inject
    lateinit var repository: RemoteBoardRepository

    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateListBinding.inflate(inflater, container, false)
        binding.vm = listviewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        display()

        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Creating List")
            .setView(R.layout.progress_dialog_layout)
            .create()
        dialog.show()

        listviewModel.isProcessing.observe(viewLifecycleOwner){
            if(it) {
                dialog.show()
            }else {
                dialog.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun display() {
        scp.launch {
            var dataSource = repository.boards()
            var names : ArrayList<String> = arrayListOf()
            for(i in dataSource){
                names.add(i.name)
            }
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_expandable_list_item_1,
                    names
                )
                binding.spinner2.adapter = adapter
                binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long) {
                        val tempName = names[position]
                        for (boards in dataSource) {
                            if (boards.name == tempName) {
                                Log.i("@id", "${boards.id}")
                                listviewModel._id.value = boards.id
                                break
                            }
                        }

                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }

                }
            }
        }
    }
}