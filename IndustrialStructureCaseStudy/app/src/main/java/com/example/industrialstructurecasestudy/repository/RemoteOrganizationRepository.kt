package com.example.industrialstructurecasestudy.repository


import com.example.industrialstructurecasestudy.domain.Organization
import com.example.industrialstructurecasestudy.dto.OrganizationDto
import com.example.industrialstructurecasestudy.rest.TrelloOrganizationApi
import com.example.industrialstructurecasestudy.rest.handleRequest
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteOrganizationRepository
@Inject
constructor(
    private val trelloOrganizationApi: TrelloOrganizationApi
) {
    suspend fun createOrganization(organization : Organization): Result<OrganizationDto> = handleRequest {
        trelloOrganizationApi.createOrganization(organization.displayName, organization.desc)
    }

    suspend fun deleteOrganization(organization : Organization) : Result<Any> = handleRequest {
      trelloOrganizationApi.deleteOrganization(organization.organizationId)
    }

    suspend fun organizations() : Result<List<Organization>> = handleRequest {
        trelloOrganizationApi.organizations().map {
            Organization(displayName = it.displayName, organizationId = it.id, id = 0 , desc = it.desc )
        }
    }
    suspend fun getOrganizations() : List<OrganizationDto> = trelloOrganizationApi.organizations()

    suspend fun updateOrganization(id : String, name : String, desc : String) = handleRequest {
        trelloOrganizationApi.update(id , name, desc)
    }


}