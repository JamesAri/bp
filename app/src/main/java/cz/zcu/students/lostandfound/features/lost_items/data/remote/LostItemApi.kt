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

class LostItemApi @Inject constructor(
    db: FirebaseFirestore,
) {
    private val collectionRef = db.collection(
        LOST_ITEM_COLLECTION_KEY
    )

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

    suspend fun getLostItemList(): Flow<LostItemListDto> {
        return lostItemListFlowQueryFetch(
            collectionRef
                .whereEqualTo(LOST_ITEM_IS_DELETED_KEY, false)
                .whereEqualTo(LOST_ITEM_IS_FOUND_KEY, false)
                .orderBy(LOST_ITEM_CREATED_AT_KEY,
                    Query.Direction.DESCENDING
                )
        )
    }

    suspend fun getLostItemListByOwnerId(id: String): Flow<LostItemListDto> {
        return lostItemListFlowQueryFetch(
            collectionRef
                .whereEqualTo(LOST_ITEM_IS_DELETED_KEY, false)
                .whereEqualTo(LOST_ITEM_IS_FOUND_KEY, false)
                .whereEqualTo(LOST_ITEM_POST_OWNER_KEY, id)
                .orderBy(LOST_ITEM_CREATED_AT_KEY, Query.Direction.DESCENDING)
        )
    }

    suspend fun getLostItem(id: String): LostItemDto? {
        return withContext(Dispatchers.IO) {
            return@withContext collectionRef
                .document(id)
                .get()
                .await()
                .toObject(LostItemDto::class.java)
        }
    }

    suspend fun updateLostItem(lostItemDto: LostItemDto) {
        withContext(Dispatchers.IO) {
            return@withContext collectionRef
                .document(lostItemDto.id)
                .set(lostItemDto)
                .await()
        }
    }

    suspend fun createLostItem(lostItemDto: LostItemDto) {
        updateLostItem(lostItemDto)
    }
}