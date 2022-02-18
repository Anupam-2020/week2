package com.example.industrialstructurecasestudy.repository

import com.example.industrialstructurecasestudy.dto.BoardsDto
import com.example.industrialstructurecasestudy.dto.ListDto
import com.example.industrialstructurecasestudy.rest.TrelloBoardsApi
import com.example.industrialstructurecasestudy.rest.TrelloListApi
import com.example.industrialstructurecasestudy.rest.handleRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteListRepository
@Inject
constructor(
    private val trelloListApi: TrelloListApi
) {

    suspend fun createList(boardId: String, displayName: String): Result<ListDto> = handleRequest {
        trelloListApi.createList(boardId = boardId, name = displayName)
    }

}