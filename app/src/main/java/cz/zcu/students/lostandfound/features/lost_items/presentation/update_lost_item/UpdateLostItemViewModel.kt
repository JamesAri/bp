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

@HiltViewModel
class UpdateLostItemViewModel @Inject constructor(
    private val dbRepo: LostItemRepository,
) : ViewModel() {

    var isEditing by mutableStateOf(false)

    var lostItemState by mutableStateOf<Response<LostItem>>(Success(null))
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var uriState by mutableStateOf<Uri?>(null)
        private set

    var location by mutableStateOf<LocationCoordinates?>(null)
        private set

    fun setItemTitle(title: String) {
        this.title = title
    }

    fun setItemDescription(description: String) {
        this.description = description
    }

    fun setItemUri(uri: Uri?) {
        this.uriState = uri
    }

    fun setItemLocation(location: LocationCoordinates?) {
        this.location = location
    }

    fun getLostItem(id: String) {
        viewModelScope.launch {
            lostItemState = dbRepo.getLostItem(id)
            when (val lostItemResponse = lostItemState) {
                is Error -> {}
                Loading -> {}
                is Success -> {
                    if (!isEditing) {
                        Log.d("UpdateLostItemViewModel", "getLostItem: CALLLLL")
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

    private fun setLostItem(lostItem: LostItem) {
        setItemTitle(lostItem.title)
        setItemDescription(lostItem.description)
        setItemUri(lostItem.imageUri)
        setItemLocation(lostItem.location)
    }
}