package cz.zcu.students.lostandfound.features.lost_items.presentation

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.zcu.students.lostandfound.common.constants.General.Companion.UNKNOWN_ERR
import cz.zcu.students.lostandfound.common.extensions.isNull
import cz.zcu.students.lostandfound.common.features.auth.domain.repository.AuthRepository
import cz.zcu.students.lostandfound.common.features.storage.domain.repository.ImageStorageRepository
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.*
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItemList
import cz.zcu.students.lostandfound.features.lost_items.domain.repository.LostItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LostItemViewModel @Inject constructor(
    private val dbRepo: LostItemRepository,
    private val storageRepo: ImageStorageRepository,
    private val authRepo: AuthRepository,
) : ViewModel() {

    private val _lostItemsState = MutableStateFlow<Response<Flow<LostItemList>>>(Success(null))
    val lostItemsState = _lostItemsState.asStateFlow()

    var createdLostItemState by mutableStateOf<Response<Boolean>>(Success(null))
        private set

    var lostItemState by mutableStateOf<Response<LostItem>>(Success(null))
        private set


    fun loadLostItems() {
        viewModelScope.launch {
            _lostItemsState.update { Loading }
            _lostItemsState.update { dbRepo.getLostItemListFlow() }
        }
    }

    fun getLostItem(id: String) {
        viewModelScope.launch {
            lostItemState = dbRepo.getLostItem(id)
        }
    }

    fun createLostItem(title: String, description: String, imageUri: Uri?) {
        viewModelScope.launch {
            createdLostItemState = Loading
            when (val currentUser = authRepo.getCurrentUser()) {
                is Error -> createdLostItemState = Error(currentUser.error)
                Loading -> {}
                is Success -> {
                    if (currentUser.data == null) {
                        createdLostItemState = Error(Exception("user not logged in"))
                        return@launch
                    }
                    val lostItem = LostItem(
                        title = title,
                        description = description,
                        imageUri = imageUri,
                        postOwnerId = currentUser.data.id,
                    )
                    postLostItem(lostItem)
                }
            }
        }
    }

    private suspend fun postLostItem(lostItem: LostItem) {
        val imageUri = lostItem.imageUri
        createdLostItemState = if (imageUri.isNull()) {
            dbRepo.createLostItem(lostItem = lostItem)
        } else {
            when (val uriResponse =
                storageRepo.addImageToStorage(
                    imageUri = imageUri!!,
                    name = lostItem.id,
                )
            ) {
                is Loading -> Error(Exception(UNKNOWN_ERR))
                is Success -> {
                    // Item got assigned uri from firebase storage, so we can save it in
                    // lightweight database now.
                    val lostItemWithAssignedUri = lostItem.copy(imageUri = uriResponse.data)
                    dbRepo.createLostItem(lostItem = lostItemWithAssignedUri)
                }
                is Error -> Error(uriResponse.error)
            }
        }

    }
}

