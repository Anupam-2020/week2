package com.example.industrialstructurecasestudy.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.industrialstructurecasestudy.domain.Organization

@Dao
interface OrganizationDao {
    @Insert
    fun createOrganization(organization: Organization)

}