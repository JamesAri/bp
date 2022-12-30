package cz.zcu.students.lostandfound.lost_items.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import cz.zcu.students.lostandfound.common.Constants.Companion.LOST_ITEM_COLLECTION_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LostItemApi @Inject constructor(
    store: FirebaseFirestore,
) {
    private val collectionRef = store.collection(LOST_ITEM_COLLECTION_KEY)

    suspend fun getLostItemListFlow(): Flow<LostItemListDto> {
        return withContext(Dispatchers.IO) {
            return@withContext collectionRef
                .snapshots()
                .map { snapshot ->
                    val lostItems = mutableListOf<LostItemDto>()
                    if (!snapshot.isEmpty) {
                        val documents = snapshot.documents
                        for (document in documents) {
                            document.toObject(LostItemDto::class.java)?.let { lostItems.add(it) }
                        }
                    }
                    LostItemListDto(lostItems)
                }
        }
    }

    fun getLostItem(id: String): LostItemDto {
        TODO("Not yet implemented")
    }

    suspend fun createLostItem(lostItem: LostItemDto, callback: (String) -> Unit) {
        withContext(Dispatchers.IO) {
            collectionRef
                .add(lostItem)
                .addOnSuccessListener { documentReference ->
                    val newId: String = documentReference.id
                    Log.d("LostItemApi", "DocumentSnapshot added with ID: $newId")
                    callback(newId)
                }
                .addOnFailureListener { error ->
                    Log.w("LostItemApi", "Error adding document", error)
                }
        }
    }
}