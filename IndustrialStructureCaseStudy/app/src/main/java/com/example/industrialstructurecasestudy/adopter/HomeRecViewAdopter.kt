package com.example.industrialstructurecasestudy.adopter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.industrialstructurecasestudy.R
import com.example.industrialstructurecasestudy.domain.Organization
import com.example.industrialstructurecasestudy.ui.HomeFragment
import com.example.industrialstructurecasestudy.ui.HomeViewModel

data class RecyclerItemOperation(
    val operation: String,
    val organization: Organization,
    val position: Int
)

class HomeViewHolder(
    private val infVw : View
) : RecyclerView.ViewHolder(infVw) {

    fun itemPos() = infVw

    fun name() = infVw.findViewById<TextView>(R.id.txtOrgNm)

    fun image() = infVw.findViewById<ImageView>(R.id.imgOrg)

    fun delete() = infVw.findViewById<ImageView>(R.id.imgDel)

    fun edit() = infVw.findViewById<ImageView>(R.id.imgEdt)

    fun desc() = infVw.findViewById<TextView>(R.id.txtOrgDesc)
}

class HomeAdapter(
    private val context : Context,
    private val organizations : List<Organization>,
    private val vm: HomeViewModel
) : RecyclerView.Adapter<HomeViewHolder>() {

    private val _clkPos = MutableLiveData<Int>()
    val clkPos: LiveData<Int> = _clkPos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {

        val infVw = LayoutInflater.from(context).inflate(
            R.layout.home_recyler_item,
            parent,
            false
        )

        return HomeViewHolder(infVw)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.name().text = organizations[position].displayName
        holder.edit().setImageResource(R.drawable.ic_edit)
        holder.delete().setImageResource(R.drawable.ic_delete)
        holder.image().setImageResource(R.drawable.ic_menu_gallery)
        holder.desc().text = organizations[position].desc

        holder.delete().setOnClickListener {
            val item = RecyclerItemOperation(
                "delete", organizations[position], position
            )
            vm.onItemClick(item)

        }

        holder.edit().setOnClickListener {
            val item = RecyclerItemOperation(
                "edit", organizations[position], position
            )
            vm.onItemClick(item)
        }

        holder.itemPos().setOnClickListener {
            //_clkPos.value = position
            val item = RecyclerItemOperation(
                "item", organizations[position], position
            )
            vm.onItemClick(item)
        }

    }

    override fun getItemCount() = organizations.size
}