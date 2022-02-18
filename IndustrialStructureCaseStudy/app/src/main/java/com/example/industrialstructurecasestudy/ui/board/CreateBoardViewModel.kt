package com.example.industrialstructurecasestudy.ui.board

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.industrialstructurecasestudy.domain.Board
import com.example.industrialstructurecasestudy.domain.Organization
import com.example.industrialstructurecasestudy.repository.RemoteBoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBoardViewModel
@Inject
constructor(
    private val remoteBoardRepository: RemoteBoardRepository
)    : ViewModel() {

    val _id = MutableLiveData<String>()

    private val _isProcessing : MutableLiveData<Boolean> = MutableLiveData(false)
    val isProcessing: LiveData<Boolean> = _isProcessing

    private val _board : MutableLiveData<Board> = MutableLiveData(
        Board(id = 0, idOrganization = "", name = "", desc = "")
    )

    private val board: LiveData<Board> = _board

    fun setId(id:String){
        _id.value = id ?: ""
    }

    fun onBoardNameChanged(str : String) {
        board.value?.name = str
    }

    fun onCreateBoardClicked() {
        _isProcessing.value = true
        viewModelScope.launch {
            board.value?.let {
                val result = remoteBoardRepository.createBoard(_id.value?:"", displayName = it.name)
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

    fun deleteBoard(id: String){
        _isProcessing.value = true
        viewModelScope.launch {
            val result = remoteBoardRepository.deleteBoard(id)
//            if (result.isSuccess) {
//                Log.i("@ani", "Operation Is Successful")
//                _isProcessing.value = false
//            } else {
//                Log.i("@ani", "Operation Failed")
//                _isProcessing.value = false
//            }
        }
    }
}