package com.example.industrialstructurecasestudy.rest

import com.example.industrialstructurecasestudy.dto.OrganizationDto
import retrofit2.http.*

interface TrelloOrganizationApi {

    @GET("members/me/organizations")
    suspend fun organizations() :  List<OrganizationDto>

    @POST("organizations")
    suspend fun createOrganization(
        @Query("displayName") displayName : String,
        @Query("desc") description : String,
    ) : OrganizationDto


    @DELETE("organizations/{id}")
    suspend fun deleteOrganization(
        @Path("id") organizationId : String,
    ) : OrganizationDto

    @PUT("organizations/{id}")
    suspend fun update(
        @Path("id") organizationId : String,
        @Query("displayName") name : String,
        @Query("desc") description : String,
    )
}