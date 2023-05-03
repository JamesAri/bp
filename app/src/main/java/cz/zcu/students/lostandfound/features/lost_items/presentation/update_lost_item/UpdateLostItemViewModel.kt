package cz.zcu.students.lostandfound.features.lost_items.presentation.update_lost_item

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.zcu.students.lostandfound.common.features.map.domain.model.LocationCoordinates
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.*
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItem
import cz.zcu.students.lostandfound.features.lost_items.domain.repository.LostItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Viewmodel for update lost item form.
 *
 * @property dbRepo repository of [LostItemRepository].
 */
@HiltViewModel
class UpdateLostItemViewModel @Inject constructor(
    private val dbRepo: LostItemRepository,
) : ViewModel() {

    /** Edit mode state. */
    private var isEditing by mutableStateOf(false)

    /** State of te updating item. */
    var lostItemState by mutableStateOf<Response<LostItem>>(Success(null))
        private set

    /** Title of lost item. */
    var title by mutableStateOf("")
        private set

    /** Description of lost item. */
    var description by mutableStateOf("")
        private set

    /** Uri of the lost item image. */
    var uriState by mutableStateOf<Uri?>(null)
        private set

    /** Location of the found lost item. */
    var location by mutableStateOf<LocationCoordinates?>(null)
        private set


    /**
     * Sets stat of lost items [title].
     *
     * @param title to set.
     */
    fun setItemTitle(title: String) {
        this.title = title
    }

    /**
     * Sets stat of lost items [description].
     *
     * @param description to set.
     */
    fun setItemDescription(description: String) {
        this.description = description
    }

    /**
     * Sets stat of lost items [uri].
     *
     * @param uri to set.
     */
    fun setItemUri(uri: Uri?) {
        this.uriState = uri
    }

    /**
     * Sets stat of lost items [location].
     *
     * @param location to set.
     */
    fun setItemLocation(location: LocationCoordinates?) {
        this.location = location
    }

    /**
     * Gets lost item based on the passed [id].
     *
     * @param id of the lost item.
     */
    fun getLostItem(id: String) {
        viewModelScope.launch {
            lostItemState = dbRepo.getLostItem(id)
            when (val lostItemResponse = lostItemState) {
                is Error -> {}
                Loading -> {}
                is Success -> {
                    if (!isEditing) {
                        lostItemResponse.data?.let { lostItem ->
                            Log.d("UpdateLostItemViewModel", "getLostItem: $lostItem")
                            setLostItem(lostItem)
                        }
                        isEditing = true
                    }
                }
            }
        }
    }

    /**
     * Sets the lost item - helper method.
     *
     * @param lostItem to set.
     */
    private fun setLostItem(lostItem: LostItem) {
        setItemTitle(lostItem.title)
        setItemDescription(lostItem.description)
        setItemUri(lostItem.imageUri)
        setItemLocation(lostItem.location)
    }
}