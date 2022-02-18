package com.example.industrialstructurecasestudy.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Organization(
    @PrimaryKey(autoGenerate = true)
    var id : Long,
    var organizationId: String,
    var displayName : String,
    var desc : String
)