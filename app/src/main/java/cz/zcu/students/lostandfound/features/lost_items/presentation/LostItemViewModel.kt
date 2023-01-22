package cz.zcu.students.lostandfound.features.lost_items.presentation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
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

    var createdLostItemState by mutableStateOf<Response<Boolean>>(Success(null))
        private set

    var lostItemState by mutableStateOf<Response<LostItem>>(Success(null))
        private set

    val filters = mutableStateListOf<String>()

    fun loadLostItems() {
        viewModelScope.launch {
            _lostItemListState.update { Loading }
            when (val apiFlowResponse = dbRepo.getLostItemListFlow()) {
                is Error -> _lostItemListState.update { Error(apiFlowResponse.error) }
                is Success -> {
                    if (apiFlowResponse.data != null) {
                        apiFlowResponse.data
                            .collect { lostItemList ->
                                val filteredList = filterLostItemList(lostItemList)
                                _lostItemListState.update { Success(filteredList) }
                            }
                    } else {
                        _lostItemListState.update { Success(null) }
                    }
                }
                Loading -> {} // do nothing
            }
        }
    }

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


    private fun anyStringsContainsTargets(terms: List<String>, targets: List<String>): Boolean {
        return targets.any { target ->
            terms.any { term ->
                term in target
            }
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

