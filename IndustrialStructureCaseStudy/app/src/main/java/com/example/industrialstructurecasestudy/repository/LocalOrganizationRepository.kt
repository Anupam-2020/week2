package com.example.industrialstructurecasestudy.repository

import com.example.industrialstructurecasestudy.dao.OrganizationDao
import com.example.industrialstructurecasestudy.domain.Organization
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LocalOrganizationRepository
@Inject
constructor(
    private val daoOrganization : OrganizationDao
) {
    fun createOrganization(organization: Organization) = daoOrganization.createOrganization(organization)
}