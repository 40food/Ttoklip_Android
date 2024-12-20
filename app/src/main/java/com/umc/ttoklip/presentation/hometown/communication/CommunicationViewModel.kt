package com.umc.ttoklip.presentation.hometown.communication

import com.umc.ttoklip.data.model.town.Communities
import kotlinx.coroutines.flow.StateFlow

interface CommunicationViewModel {
    val communities: StateFlow<List<Communities>>
    val criteria: StateFlow<String>
    val streetInfo: StateFlow<List<String>>
    val sort: StateFlow<String>

    fun setCriteria(position: Int)
    fun setSort(position:Int)
    fun getCommunities()

    fun getMemberStreetInfo()
}