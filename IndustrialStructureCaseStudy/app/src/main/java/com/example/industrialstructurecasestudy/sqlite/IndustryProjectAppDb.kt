package com.example.industrialstructurecasestudy.sqlite

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.industrialstructurecasestudy.dao.OrganizationDao
import com.example.industrialstructurecasestudy.domain.Organization

@Database(entities = [Organization::class], version = 1)
abstract class IndustryProjectAppDb : RoomDatabase() {
    abstract fun organizationDao() : OrganizationDao
}