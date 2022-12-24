package cz.zcu.students.lostandfound.lost_items.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.LOST_ITEM_COLLECTION
import javax.inject.Inject

class LostItemApiImpl @Inject constructor(
    private val store: FirebaseFirestore,
): LostItemApi {

    override suspend fun getLostItemList(): LostItemListDto {
        val acc = mutableListOf<LostItemDto>()
        store.collection(LOST_ITEM_COLLECTION)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("LostItemApiImpl", "${document.id} => ${document.data}")
                    acc.add(LostItemDto(document.data))
                }
            }
            .addOnFailureListener { exception ->
                Log.w("LostItemApiImpl", "Error getting documents.", exception)
            }
        return LostItemListDto(acc)
    }

    override suspend fun getLostItem(id: Int): LostItemDto {
        TODO("Not yet implemented")
    }

    override suspend fun createLostItem(lostItem: LostItemDto) {
//        // Add a new document with a generated ID
        store.collection(LOST_ITEM_COLLECTION)
            .add(lostItem.data)
            .addOnSuccessListener { documentReference ->
                Log.d("LostItemApiImpl", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("LostItemApiImpl", "Error adding document", e)
            }
    }
}