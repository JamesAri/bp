package cz.zcu.students.lostandfound.features.lost_items.domain.repository

import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItem
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItemList
import kotlinx.coroutines.flow.Flow

/**
 * Lost items repository.
 *
 * This repository provides multiple operations for manipulation with lost
 * items.
 *
 * @see Response
 */
interface LostItemRepository {
    /**
     * Gets all lost items.
     *
     * @return emitting lost item lists [LostItemList].
     */
    suspend fun getLostItemListFlow(): Response<Flow<LostItemList>>

    /**
     * Gets lost item based on the passed lost item [id].
     *
     * @param id of lost item.
     * @return Lost item [LostItem].
     */
    suspend fun getLostItem(id: String): Response<LostItem>

    /**
     * Creates lost item [lostItem].
     *
     * @param lostItem to create.
     * @return `true` if success, `false` otherwise.
     */
    suspend fun createLostItem(lostItem: LostItem): Response<Boolean>

    /**
     * Updates lost item [lostItem].
     *
     * @param lostItem lost item to update.
     * @return `true` if success, `false` otherwise.
     */
    suspend fun updateLostItem(lostItem: LostItem): Response<Boolean>

    /**
     * Gets current users posts.
     *
     * @return posts which the currently logged in user is owner of.
     */
    suspend fun getLostItemListFlowFromCurrentUser(): Response<Flow<LostItemList>>
}