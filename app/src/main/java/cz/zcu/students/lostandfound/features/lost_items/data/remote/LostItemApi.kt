package cz.zcu.students.lostandfound.features.lost_items.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import cz.zcu.students.lostandfound.common.Constants.Companion.LOST_ITEM_COLLECTION_KEY
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
    private val collectionRef = db.collection(LOST_ITEM_COLLECTION_KEY)

    suspend fun getLostItemList(): Flow<LostItemListDto> {
        return withContext(Dispatchers.IO) {
            return@withContext collectionRef
                .snapshots()
                .map { snapshot ->
                    val lostItems = mutableListOf<LostItemDto>()
                    if (!snapshot.isEmpty) {
                        val documents = snapshot.documents
                        for (document in documents) {
                            document.toObject(LostItemDto::class.java)?.let {
                                lostItems.add(it)
                            }
                        }
                    }
                    LostItemListDto(lostItems)
                }
        }
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

    suspend fun createLostItem(lostItem: LostItemDto) {
        withContext(Dispatchers.IO) {
            return@withContext collectionRef
                .document(lostItem.id)
                .set(lostItem)
                .await()
        }
    }
}