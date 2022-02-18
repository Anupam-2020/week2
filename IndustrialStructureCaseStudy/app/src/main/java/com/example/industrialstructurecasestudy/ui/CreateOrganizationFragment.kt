package com.example.industrialstructurecasestudy.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.industrialstructurecasestudy.R
import com.example.industrialstructurecasestudy.databinding.FragmentCreateOrganizationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateOrganizationFragment : Fragment() {
    private var _binding : FragmentCreateOrganizationBinding? = null

    val binding get() = _binding!!

    private val viewModel: CreateOrganizationViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateOrganizationBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Creating Workspace")
            .setView(R.layout.progress_dialog_layout)
            .create()
        dialog.show()

        viewModel.isProcessing.observe(viewLifecycleOwner){
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

}