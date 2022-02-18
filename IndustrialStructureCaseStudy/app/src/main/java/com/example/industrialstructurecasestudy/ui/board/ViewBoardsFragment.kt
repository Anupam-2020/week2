package com.example.industrialstructurecasestudy.ui.board

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.industrialstructurecasestudy.R
import com.example.industrialstructurecasestudy.adopter.BoardAdapter
import com.example.industrialstructurecasestudy.adopter.HomeAdapter
import com.example.industrialstructurecasestudy.databinding.FragmentViewBoardsBinding
import com.example.industrialstructurecasestudy.dialogueBox.YesNoDialogue
import com.example.industrialstructurecasestudy.dto.BoardsDto
import com.example.industrialstructurecasestudy.repository.RemoteBoardRepository
import com.example.industrialstructurecasestudy.toast.ToastUtil
import com.example.industrialstructurecasestudy.ui.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class ViewBoardsFragment : Fragment(R.layout.fragment_create_board) {
    private val scp : CoroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var boardrepository: RemoteBoardRepository

    private var _binding : FragmentViewBoardsBinding? = null
    val binding get() = _binding!!

    private val vm : BoardViewModel by viewModels()

    private val crviewModel : CreateBoardViewModel by viewModels()

    private val args : ViewBoardsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentViewBoardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.frameLayout3.setOnRefreshListener {
//            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
            fetchBoardsFromServer()
            binding.frameLayout3.isRefreshing = false
        }
        fetchBoardsFromServer()


        vm.item.observe(viewLifecycleOwner) {
            val id = it.board.idOrganization
            val boardId = it.board.id
            when(it.operation) {
                "edit" -> {
                    Log.i("@Check", "Edit Clicked")
                    performUpdateOperation(id, boardId)
                }
                "delete" -> {
                    Log.i("@Check", "Delete Clicked")
                    performDeleteOperation(it.board.name,it.board.id)
                }
                "item" -> {
                    Log.i("@BoardItemClicked", "Item Clicked")
                    findNavController().navigate(R.id.viewListsAndCardsFragment)
                }

            }
        }

    }


    private fun fetchBoardsFromServer() {
        val id = args.id
            scp.launch {
                val result = boardrepository.boards()
                Log.i("@Board Check1", "${result}")
                Log.i("@id Check", "${id}")
                val data : ArrayList<BoardsDto> = arrayListOf()
                for(i in result){
                    if(i.idOrganization == id){
                        data.add(i)
                    }
                }
                Log.i("@Board Check2", "${data}")
                withContext(Dispatchers.Main){
                    val adapter = BoardAdapter(
                        requireContext(),
                        data,
                        vm
                    )
                    binding.recBoard.adapter = adapter
                    val layoutManager = GridLayoutManager(requireContext(),2)
                    binding.recBoard.layoutManager = layoutManager

                    binding.recBoard.addItemDecoration(
                        DividerItemDecoration(requireContext(), layoutManager.orientation)
                    )
                }
            }

    }


    private fun performDeleteOperation(displayName: String, id: String) {

        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> crviewModel.deleteBoard(id)
                DialogInterface.BUTTON_NEGATIVE ->  Log.i("@Check", "${id}")
            }
        }
        YesNoDialogue.createSimpleYesNoDialog(
            title = "Delete",
            msg = "Sure want to Delete ${displayName}",
            context = requireContext(),
            listener = dialogClickListener
        ).show()
    }

//    private fun onUpdateWorkspace(id : String, nm : String, desc : String) {
//        Log.i("@Check", "id - $id, nm - $nm, desc - $desc")
//        scp.launch {
//            val result = boardrepository.updateOrganization(id, nm, desc)
//            result.onSuccess {
//                ToastUtil.coroutineToast(requireContext(), "Updated Successfully")
//            }
//            result.onFailure {
//                ToastUtil.coroutineToast(requireContext(), "Problem In Updating")
//            }
//        }
//    }

    private fun onUpdateBoard(id: String, nm: String, desc: String) {
        Log.i("@Check","id-$id , nm-$nm , desc-$desc ")
        scp.launch {
            val result = boardrepository.updateBoard(id,nm,desc)
            result.onSuccess {
                ToastUtil.coroutineToast(requireContext(), "Updated Successfully")
            }
            result.onFailure {
                ToastUtil.coroutineToast(requireContext(), "Problem In Updating")
            }
        }
    }

    private fun performUpdateOperation(id : String, boardId: String) {
        scp.launch {
            val result = boardrepository.boards()
            val oldBoard = vm.getBoardById(result, id)
            withContext(Dispatchers.Main) {
                YesNoDialogue.createInputYesNoDialog(
                    requireContext(),
                    "Do you want to update Board ?",
                    oldBoard?.name?: "Default",
                    oldBoard?.desc?: "Default"
                ) { nm, desc ->
                    onUpdateBoard(boardId, nm, desc)
                }.show()
            }

        }

    }
}
