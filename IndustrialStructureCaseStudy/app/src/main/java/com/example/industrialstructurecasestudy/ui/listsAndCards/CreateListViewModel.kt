package com.example.industrialstructurecasestudy.ui.listsAndCards

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.industrialstructurecasestudy.domain.Board
import com.example.industrialstructurecasestudy.repository.RemoteBoardRepository
import com.example.industrialstructurecasestudy.repository.RemoteListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateListViewModel
@Inject
constructor(
    private val remoteListRepository: RemoteListRepository
)    : ViewModel() {

    val _id = MutableLiveData<String>()

    private val _isProcessing : MutableLiveData<Boolean> = MutableLiveData(false)
    val isProcessing: LiveData<Boolean> = _isProcessing

    private val _board : MutableLiveData<Board> = MutableLiveData(
        Board(id = 0, idOrganization = "", name = "",desc = "")
    )

    private val board: LiveData<Board> = _board

    fun setId(id:String){
        _id.value = id ?: ""
    }

    fun onListNameChanged(str : String) {
        board.value?.name = str
    }

    fun onCreateListClicked() {
        _isProcessing.value = true
        viewModelScope.launch {
            board.value?.let {
                val result = remoteListRepository.createList(_id.value?:"", displayName = it.name)
                if (result.isSuccess) {
                    Log.i("@Create", "Operation Is Successful")
                    _isProcessing.value = false
                } else {
                    Log.i("@Create", "Operation Failed")
                    _isProcessing.value = false
                }
            }
        }
    }

}