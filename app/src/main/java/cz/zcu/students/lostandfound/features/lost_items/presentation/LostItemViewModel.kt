package cz.zcu.students.lostandfound.features.lost_items.presentation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.zcu.students.lostandfound.common.extensions.isNull
import cz.zcu.students.lostandfound.common.features.auth.domain.repository.AuthRepository
import cz.zcu.students.lostandfound.common.features.storage.domain.repository.ImageStorageRepository
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.*
import cz.zcu.students.lostandfound.common.util.anyStringsContainsTargets
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItemList
import cz.zcu.students.lostandfound.features.lost_items.domain.repository.LostItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class LostItemViewModel @Inject constructor(
    private val dbRepo: LostItemRepository,
    private val storageRepo: ImageStorageRepository,
    private val authRepo: AuthRepository,
) : ViewModel() {

    private val _lostItemListState = MutableStateFlow<Response<LostItemList>>(Success(null))
    val lostItemListState = _lostItemListState.asStateFlow()

    var crudLostItemState by mutableStateOf<Response<Boolean>>(Success(null))
        private set

    var lostItemState by mutableStateOf<Response<LostItem>>(Success(null))
        private set

    val filters = mutableStateListOf<String>()

    /**
     * Future application should use a dedicated third-party search service.
     * These services provide advanced indexing and search capabilities far beyond
     * what any simple database query can offer and what in this case is an overkill.
     *
     * As temporary solution (and as we except small user base) we will compute these
     * queries locally.
     *
     * Empty [filters] results in loading the whole dataset.
     */
    private suspend fun filterLostItemList(
        lostItemList: LostItemList,
    ): LostItemList {

        if (filters.isEmpty()) return lostItemList
        return withContext(Dispatchers.Default) {
            val filteredList = lostItemList.lostItems.filter { lostItem ->
                val targets = listOf(lostItem.title, lostItem.description)
                anyStringsContainsTargets(terms = filters, targets = targets)
            }
            return@withContext LostItemList(filteredList)
        }
    }

    private fun fetchLostItems(repoCall: suspend () -> Response<Flow<LostItemList>>) {
        viewModelScope.launch {
            _lostItemListState.update { Loading }
            when (val apiFlowResponse = repoCall()) {
                is Error -> _lostItemListState.update { Error(apiFlowResponse.error) }
                is Success -> {
                    if (apiFlowResponse.data != null) {
                        try {
                            apiFlowResponse.data
                                .collect { lostItemList ->
                                    val filteredList = filterLostItemList(lostItemList)
                                    _lostItemListState.update { Success(filteredList) }
                                }
                        } catch (e: Exception) {
                            _lostItemListState.update { Success(null) }
                            Log.e("LostItemViewModel", "fetchLostItems: ${e.message}")
                        }
                    } else {
                        _lostItemListState.update { Success(null) }
                    }
                }
                Loading -> {}
            }
        }
    }

    fun loadLostItems() {
        fetchLostItems { dbRepo.getLostItemListFlow() }
    }

    fun loadMyItems() {
        fetchLostItems { dbRepo.getLostItemListFlowFromCurrentUser() }
    }

    fun getLostItem(id: String) {
        viewModelScope.launch {
            lostItemState = dbRepo.getLostItem(id)
        }
    }

    fun deleteLostItem(lostItem: LostItem) {
        viewModelScope.launch {
            updateLostItem(
                lostItem.copy(
                    isDeleted = true,
                )
            )
        }
    }

    fun createLostItem(title: String, description: String, localImageUri: Uri?) {
        viewModelScope.launch {
            crudLostItemState = Loading
            when (val currentUser = authRepo.getCurrentUser()) {
                is Error -> crudLostItemState = Error(currentUser.error)
                Loading -> {}
                is Success -> {
                    if (currentUser.data == null) {
                        crudLostItemState = Error(Exception("user not logged in"))
                    } else {
                        val lostItem = LostItem(
                            title = title,
                            description = description,
                            imageUri = localImageUri,
                            postOwnerId = currentUser.data.id,
                        )
                        writeLostItemWithLocalImage(lostItem)
                    }
                }
            }
        }
    }

    private fun writeLostItemWithLocalImage(lostItem: LostItem) {
        viewModelScope.launch {
            val imageUri = lostItem.imageUri
            crudLostItemState = Loading
            if (imageUri.isNull()) {
                crudLostItemState = dbRepo.createLostItem(lostItem = lostItem)
            } else {
                when (val uriResponse = storageRepo.addImageToStorage(
                    imageUri = imageUri!!,
                    name = lostItem.id,
                )) {
                    is Loading -> {}
                    is Success -> {
                        // Item got assigned uri from firebase storage, so we can save it in
                        // lightweight database now.
                        val lostItemWithAssignedUri = lostItem.copy(imageUri = uriResponse.data)
                        crudLostItemState =
                            dbRepo.createLostItem(lostItem = lostItemWithAssignedUri)
                    }
                    is Error -> crudLostItemState = Error(uriResponse.error)
                }
            }
        }
    }

    private fun writeLostItemWithRemoteImage(lostItem: LostItem) {
        viewModelScope.launch {
            crudLostItemState = Loading
            when (val currentUser = authRepo.getCurrentUser()) {
                is Error -> crudLostItemState = Error(currentUser.error)
                Loading -> {}
                is Success -> {
                    crudLostItemState = if (currentUser.data == null) {
                        Error(Exception("user not logged in"))
                    } else {
                        dbRepo.updateLostItem(lostItem)
                    }
                }
            }
        }
    }


    fun updateLostItem(lostItem: LostItem, hasRemoteUri: Boolean = true) {
        if (hasRemoteUri) {
            writeLostItemWithRemoteImage(lostItem = lostItem)
        } else {
            writeLostItemWithLocalImage(lostItem = lostItem)
        }
    }
}

