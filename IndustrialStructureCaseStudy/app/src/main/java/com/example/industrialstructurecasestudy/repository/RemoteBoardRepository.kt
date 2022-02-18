package com.example.industrialstructurecasestudy.repository

import com.example.industrialstructurecasestudy.domain.Board
import com.example.industrialstructurecasestudy.domain.Organization
import com.example.industrialstructurecasestudy.dto.BoardsDto
import com.example.industrialstructurecasestudy.dto.OrganizationDto
import com.example.industrialstructurecasestudy.rest.TrelloBoardsApi
import com.example.industrialstructurecasestudy.rest.TrelloOrganizationApi
import com.example.industrialstructurecasestudy.rest.handleRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteBoardRepository
@Inject
constructor(
    private val trelloBoardApi: TrelloBoardsApi
){

    suspend fun createBoard(organizationId : String, displayName : String): Result<BoardsDto> = handleRequest {
        trelloBoardApi.createBoard(organizationId = organizationId, displayName = displayName)
    }

    suspend fun boards() : List<BoardsDto> =
        trelloBoardApi.boards()



    suspend fun deleteBoard(id : String) : Result<Any> = handleRequest {
        trelloBoardApi.deleteBoard(id)
    }
//    suspend fun deleteBoard(id : String) =
//        trelloBoardApi.deleteBoard(id)

    suspend fun updateBoard(id: String, name: String, desc: String): Result<Any> = handleRequest {
        trelloBoardApi.updateBoard(id, name, desc)
    }


}