package com.example.industrialstructurecasestudy.rest


import com.example.industrialstructurecasestudy.dto.ListDto
import retrofit2.http.*

interface  TrelloListApi {
    @POST("lists")
    suspend fun createList(
        @Query("idBoard") boardId : String,
        @Query("name") name: String,
    ): ListDto

    @DELETE("lists/{id}")
    suspend fun deleteList(
        @Path("id") boardId : String,
    ) : ListDto
}