package com.example.industrialstructurecasestudy.domain

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class List(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val idBoard : String,
    var name : String
)