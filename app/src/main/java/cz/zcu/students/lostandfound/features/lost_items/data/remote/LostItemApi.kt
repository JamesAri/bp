package cz.zcu.students.lostandfound.features.lost_items.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.LOST_ITEM_COLLECTION_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.LOST_ITEM_CREATED_AT_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.LOST_ITEM_IS_DELETED_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.LOST_ITEM_IS_FOUND_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.LOST_ITEM_POST_OWNER_KEY
import cz.zcu.students.lostandfound.features.lost_items.data.remote.dto.LostItemDto
import cz.zcu.students.lostandfound.features.lost_items.data.remote.dto.LostItemListDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * API for Firestore lost item manipulation.
 *
 * @param db reference to Firestore [FirebaseFirestore] database.
 * @constructor constructs lost item API with Firestore [FirebaseFirestore]
 *     database reference.
 */
class LostItemApi @Inject constructor(
    db: FirebaseFirestore,
) {

    /** Lost items db collection reference. */
    private val collectionRef = db.collection(
        LOST_ITEM_COLLECTION_KEY
    )

    /**
     * Fetches all lost items and maps them to DTO object [LostItemListDto].
     *
     * @param query Firestore query for lost items.
     * @return emitting flow of lost item lists (real-time listening).
     */
    private suspend fun lostItemListFlowQueryFetch(
        query: Query
    ): Flow<LostItemListDto> {
        return withContext(Dispatchers.IO) {
            return@withContext query
                .snapshots()
                .map { snapshot ->
                    val lostItems = mutableListOf<LostItemDto>()
                    if (!snapshot.isEmpty) {
                        val documents = snapshot.documents
                        for (document in documents) {
                            document.toObject(
                                LostItemDto::class.java
                            )?.let { lostItems.add(it) }
                        }
                    }
                    LostItemListDto(lostItems)
                }
        }
    }

    /**
     * Calls query for all non-deleted, non-found lost items.
     *
     * @return emitting flow of lost item lists (real-time listening).
     */
    suspend fun getLostItemList(): Flow<LostItemListDto> {
        return lostItemListFlowQueryFetch(
            collectionRef
                .whereEqualTo(LOST_ITEM_IS_DELETED_KEY, false)
                .whereEqualTo(LOST_ITEM_IS_FOUND_KEY, false)
                .orderBy(
                    LOST_ITEM_CREATED_AT_KEY,
                    Query.Direction.DESCENDING
                )
        )
    }

    /**
     * Calls query for non-deleted, non-found lost item with specific owner
     * [id].
     *
     * @param id of lost item.
     * @return emitting flow of lost item lists (real-time listening).
     */
    suspend fun getLostItemListByOwnerId(id: String): Flow<LostItemListDto> {
        return lostItemListFlowQueryFetch(
            collectionRef
                .whereEqualTo(LOST_ITEM_IS_DELETED_KEY, false)
                .whereEqualTo(LOST_ITEM_IS_FOUND_KEY, false)
                .whereEqualTo(LOST_ITEM_POST_OWNER_KEY, id)
                .orderBy(LOST_ITEM_CREATED_AT_KEY, Query.Direction.DESCENDING)
        )
    }

    /**
     * Calls query for lost item with specific [id] that is non-deleted and
     * non-found.
     *
     * @param id of lost item.
     * @return emitting flow of lost item lists (real-time listening).
     */
    suspend fun getLostItem(id: String): LostItemDto? {
        return withContext(Dispatchers.IO) {
            return@withContext collectionRef
                .document(id)
                .get()
                .await()
                .toObject(LostItemDto::class.java)
        }
    }

    /**
     * Updates passed lost item.
     *
     * @param lostItemDto lost item to update.
     */
    suspend fun updateLostItem(lostItemDto: LostItemDto) {
        withContext(Dispatchers.IO) {
            return@withContext collectionRef
                .document(lostItemDto.id)
                .set(lostItemDto)
                .await()
        }
    }

    /**
     * Creates new lost item.
     *
     * @param lostItemDto item to create (or update, because of how the Firebase
     *     db works).
     */
    suspend fun createLostItem(lostItemDto: LostItemDto) {
        updateLostItem(lostItemDto)
    }
}