package com.example.industrialstructurecasestudy.adopter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.industrialstructurecasestudy.R
import com.example.industrialstructurecasestudy.domain.Board
import com.example.industrialstructurecasestudy.dto.BoardsDto
import com.example.industrialstructurecasestudy.ui.board.BoardViewModel

data class RecyclerBoardItemOperation(
    val operation: String,
    val board: BoardsDto,
    val position: Int
)


class BoardAdapter(
    private val context : Context,
    private val boards: List<BoardsDto>,
    private val vm: BoardViewModel
) : RecyclerView.Adapter<BoardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {

        val infVw = LayoutInflater.from(context).inflate(
            R.layout.board_recycle_item,
            parent,
            false
        )

        return BoardViewHolder(infVw)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.name().text = boards[position].name

        holder.delete().setOnClickListener {
            val item = RecyclerBoardItemOperation(
                "delete", boards[position], position
            )
            vm.onItemClick(item)

        }
        holder.edit().setOnClickListener {
            val item = RecyclerBoardItemOperation(
                "edit", boards[position], position
            )
            vm.onItemClick(item)

        }

        holder.name().setOnClickListener {
            val item = RecyclerBoardItemOperation(
                "item",boards[position], position
            )
            vm.onItemClick(item)
        }

    }

    override fun getItemCount() = boards.size
}

class BoardViewHolder(
    private val infVw : View
) : RecyclerView.ViewHolder(infVw) {

    fun name() = infVw.findViewById<Button>(R.id.boardButton)

   fun delete() = infVw.findViewById<ImageView>(R.id.brdDelImg)

    fun edit() = infVw.findViewById<ImageView>(R.id.brdEdt)

}