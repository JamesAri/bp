package cz.zcu.students.lostandfound.features.lost_items.presentation.add_lost_item

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cz.zcu.students.lostandfound.common.features.map.domain.model.LocationCoordinates
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/** Viewmodel for add lost item form. */
@HiltViewModel
class AddLostItemViewModel @Inject constructor(
) : ViewModel() {
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
}