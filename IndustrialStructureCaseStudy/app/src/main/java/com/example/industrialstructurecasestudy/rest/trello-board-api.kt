package com.example.industrialstructurecasestudy.rest

import com.example.industrialstructurecasestudy.dto.BoardsDto
import retrofit2.http.*

interface  TrelloBoardsApi {

    @GET("members/me/boards")
    suspend fun boards() :  List<BoardsDto>

    @POST("boards")
    suspend fun createBoard(
        @Query("idOrganization") organizationId : String,
        @Query("name") displayName: String,
    ): BoardsDto

    @DELETE("boards/{id}")
    suspend fun deleteBoard(
        @Path("id") boardId : String,
    ) : BoardsDto

    @PUT("boards/{id}")
    suspend fun updateBoard(
        @Path("id") boardId : String,
        @Query("name") name : String,
        @Query("desc") description: String

    )


}