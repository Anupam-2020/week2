package com.example.industrialstructurecasestudy.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.industrialstructurecasestudy.adopter.RecyclerItemOperation
import com.example.industrialstructurecasestudy.domain.Organization

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(): ViewModel() {
    private val _item : MutableLiveData<RecyclerItemOperation> = MutableLiveData(
        RecyclerItemOperation(
            "",
            Organization(0, "", "", ""),
            0
        )
    )


    val item : LiveData<RecyclerItemOperation> = _item

    private var _clkPos = MutableLiveData<String>()
    var clkPos: LiveData<String> = _clkPos

    fun getItem() = item.value

    fun onItemClick(clickedItem : RecyclerItemOperation) {
        _item.value = clickedItem
        Log.i("@Clicked","Clicked")
    }

    fun getOrgById(list: List<Organization>, id: String): Organization? {
        list.forEach {
            if(it.organizationId == id) {
                return it
            }
        }
        return null
    }

}
