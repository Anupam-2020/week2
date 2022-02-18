package com.example.industrialstructurecasestudy.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Board(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val idOrganization : String,
    var name : String,
    var desc: String
)