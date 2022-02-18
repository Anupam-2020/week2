package com.example.industrialstructurecasestudy.ui.board


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
import com.example.industrialstructurecasestudy.repository.RemoteBoardRepository
import com.example.industrialstructurecasestudy.repository.RemoteOrganizationRepository
import com.example.industrialstructurecasestudy.ui.CreateOrganizationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class CreateBoardFragment : Fragment() {

    private var _binding : FragmentCreateBoardBinding? = null

    private val scp : CoroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var repository: RemoteOrganizationRepository


    val binding get() = _binding!!

    private val boardviewModel: CreateBoardViewModel by viewModels()
    private val orgviewModel: CreateOrganizationViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateBoardBinding.inflate(inflater, container, false)
        binding.vm = boardviewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayWorkspaceSpinner()

        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Creating Board")
            .setView(R.layout.progress_dialog_layout)
            .create()
        dialog.show()

        boardviewModel.isProcessing.observe(viewLifecycleOwner){
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

    fun displayWorkspaceSpinner() {
        scp.launch {
            var dataSource = repository.getOrganizations()
            var names : ArrayList<String> = arrayListOf()
            for(i in dataSource){
                names.add(i.displayName)
            }
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_expandable_list_item_1,
                    names
                )
                binding.spinner.adapter = adapter
                binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long) {
                        val tempName = names[position]
                            for (boards in dataSource) {
                                if (boards.displayName == tempName) {
                                    Log.i("@id", "${boards.id}")
                                    boardviewModel._id.value = boards.id
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