package cz.zcu.students.lostandfound.features.lost_items.presentation.add_lost_item

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cz.zcu.students.lostandfound.common.features.map.domain.model.LocationCoordinates
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddLostItemViewModel @Inject constructor(
) : ViewModel() {
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
}