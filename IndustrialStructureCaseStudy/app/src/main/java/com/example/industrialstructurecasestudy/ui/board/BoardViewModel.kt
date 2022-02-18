package com.example.industrialstructurecasestudy.ui.board

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.industrialstructurecasestudy.adopter.RecyclerBoardItemOperation
import com.example.industrialstructurecasestudy.adopter.RecyclerItemOperation
import com.example.industrialstructurecasestudy.domain.Board
import com.example.industrialstructurecasestudy.domain.Organization
import com.example.industrialstructurecasestudy.dto.BoardsDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoardViewModel
@Inject
constructor(): ViewModel() {
    private val _item : MutableLiveData<RecyclerBoardItemOperation> = MutableLiveData(
        RecyclerBoardItemOperation(
            "",
            BoardsDto(id = "",idOrganization = "",name = "",desc = ""),
            0
        )
    )

    val item : LiveData<RecyclerBoardItemOperation> = _item

    private var _clkPos = MutableLiveData<String>()
    var clkPos: LiveData<String> = _clkPos

    fun getItem() = item.value

    fun onItemClick(clickedItem : RecyclerBoardItemOperation) {
        _item.value = clickedItem
        Log.i("@Clicked","Clicked")
    }

    fun getBoardById(list: List<BoardsDto>, id: String): BoardsDto? {
        Log.i("@Anupam","$list $id")
        Log.i("@Anupam", id)
        list.forEach {
            if(it.idOrganization == id) {
                return it
            }
        }
        return null
    }



}