package com.example.industrialstructurecasestudy.ui

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.industrialstructurecasestudy.HomeScreenActivity
import com.example.industrialstructurecasestudy.adopter.HomeAdapter
import com.example.industrialstructurecasestudy.adopter.RecyclerItemOperation
import com.example.industrialstructurecasestudy.databinding.FragmentHomeBinding
import com.example.industrialstructurecasestudy.dialogueBox.YesNoDialogue
import com.example.industrialstructurecasestudy.domain.Organization
import com.example.industrialstructurecasestudy.repository.RemoteOrganizationRepository
import com.example.industrialstructurecasestudy.toast.ToastUtil

import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import androidx.lifecycle.Observer
import kotlinx.coroutines.*


@AndroidEntryPoint
class HomeFragment: Fragment() {

    private val scp : CoroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var repository: RemoteOrganizationRepository

    private var _binding : FragmentHomeBinding? = null

    private val vm : HomeViewModel by viewModels()

    private val viewModel: CreateOrganizationViewModel by viewModels()

    private lateinit var observer : Observer<RecyclerItemOperation>

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as HomeScreenActivity).supportActionBar?.apply {
            title = "Workspaces"
            this.setDisplayHomeAsUpEnabled(true)
        }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.frameLayout2.setOnRefreshListener {
//            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
            initRecycler()
            binding.frameLayout2.isRefreshing = false
        }
        initRecycler()


        observer = Observer<RecyclerItemOperation> {
            val id = it.organization.organizationId ?: ""
            when(it.operation) {
                "edit" -> {
                    Log.i("@Check", "Edit Clicked")
                    performUpdateOperation(id)
                }
                "delete" -> {
                    Log.i("@Check", "Delete Clicked")
                    showDeleteDialog(it.organization.displayName,it.organization)
                }
                "item" -> {
                    Log.i("@Check", "ItemClicked")
                    val item = it.organization.organizationId
                    val itemName = it.organization.displayName
                    Log.i("@Check itm ", "$item  $itemName")
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToViewBoardsFragment(item))
                }
            }
        }
        vm.item.observe(viewLifecycleOwner,observer)


    }

//    override fun onDestroyView() {
//        scp.cancel()
//        vm.item.removeObserver(observer)
//        super.onDestroyView()
//
//    }

    private fun initRecycler() {

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recHome.layoutManager = layoutManager

        binding.recHome.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )

        fetchOrganizationsFromServer()


    }

    private fun fetchOrganizationsFromServer() {
        scp.launch {
            val result = repository.organizations()
            if(result.isSuccess) {
                val organizations = result.getOrThrow()

                withContext(Dispatchers.Main) {
                    val adapter = HomeAdapter(
                        requireContext(),
                        organizations,
                        vm
                    )
                    binding.recHome.adapter = adapter

//                    adapter.clkPos.observe(viewLifecycleOwner){
//                        val itm = vm.getItem()
//                        val id = itm?.organization?.organizationId ?: ""
//                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToViewBoardsFragment(id))
//                    }
                }


            }
        }
    }

    private fun showDeleteDialog(displayName: String, organization: Organization) {

        val dialogClickListener = DialogInterface.OnClickListener{_,which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE ->  viewModel.deleteOrganization(organization)
                DialogInterface.BUTTON_NEGATIVE ->  Log.i("@Check", "${organization.organizationId} ${organization.displayName}")
            }
        }
        YesNoDialogue.createSimpleYesNoDialog(
            title = "Delete",
            msg = "Sure want to Delete $displayName",
            context = requireContext(),
            listener = dialogClickListener
        ).show()
    }

    private fun onUpdateWorkspace(id : String, nm : String, desc : String) {
        Log.i("@ani", "id - $id, nm - $nm, desc - $desc")
        scp.launch {
            val result = repository.updateOrganization(id, nm, desc)
            result.onSuccess {
                ToastUtil.coroutineToast(requireContext(), "Updated Successfully")
            }
            result.onFailure {
                ToastUtil.coroutineToast(requireContext(), "Problem In Updating")
            }
        }
    }



    private fun performUpdateOperation(id : String) {

        scp.launch {
            val result = repository.organizations().getOrThrow()
            val oldOrg = vm.getOrgById(result, id)
            withContext(Dispatchers.Main) {
                YesNoDialogue.createInputYesNoDialog(
                    requireContext(),
                    "Do you want to update Workspace ?",
                    oldOrg?.displayName!!,
                    oldOrg.desc
                ) { nm, desc ->
                    onUpdateWorkspace(id, nm, desc)
                }.show()
            }

        }


    }

}