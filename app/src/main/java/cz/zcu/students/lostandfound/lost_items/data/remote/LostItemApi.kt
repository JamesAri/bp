package cz.zcu.students.lostandfound.lost_items.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.LOST_ITEM_COLLECTION_KEY
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.LOST_ITEM_DESCRIPTION_KEY
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.LOST_ITEM_LOCATION_KEY
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.LOST_ITEM_TITLE_KEY
import cz.zcu.students.lostandfound.lost_items.data.mappers.toHashMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LostItemApi @Inject constructor(
    private val store: FirebaseFirestore,
) {
    private val collectionRef = store.collection(LOST_ITEM_COLLECTION_KEY)

    fun getLostItemListFlow(): Flow<List<LostItemDto>> {
        return collectionRef
            .snapshots()
            .map { snapshot ->
                val lostItems = mutableListOf<LostItemDto>()
                if (!snapshot.isEmpty) {
                    val documents = snapshot.documents

                    for (document in documents) {
                        lostItems.add(
                            LostItemDto(
                                id = document.id,
                                title = document.getString(LOST_ITEM_TITLE_KEY),
                                description = document.getString(LOST_ITEM_DESCRIPTION_KEY),
                                location = document.getString(LOST_ITEM_LOCATION_KEY),
                            )
                        )
                    }
                }
                lostItems.toList()
            }
    }

    fun getLostItem(id: String): LostItemDto {
        TODO("Not yet implemented")
    }

    fun createLostItem(lostItem: LostItemDto) {
        collectionRef
            .add(lostItem.toHashMap())
            .addOnSuccessListener { documentReference ->
                Log.d("LostItemApiImpl", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("LostItemApiImpl", "Error adding document", e)
            }
    }
}