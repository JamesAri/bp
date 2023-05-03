package cz.zcu.students.lostandfound.features.lost_items.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.zcu.students.lostandfound.common.features.auth.domain.repository.AuthRepository
import cz.zcu.students.lostandfound.common.features.map.domain.model.LocationCoordinates
import cz.zcu.students.lostandfound.common.features.storage.domain.repository.ImageStorageRepository
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.*
import cz.zcu.students.lostandfound.common.util.datetime.LocaleTimeString
import cz.zcu.students.lostandfound.common.util.fullTextSearch
import cz.zcu.students.lostandfound.features.lost_items.data.util.LocaleTimeStringImpl
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItem
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItemList
import cz.zcu.students.lostandfound.features.lost_items.domain.repository.LostItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Viewmodel for lost items.
 *
 * @property lostItemsRepo lost items repository [LostItemRepository].
 * @property storageRepo storage repository [ImageStorageRepository].
 * @property authRepo authentication repository [AuthRepository].
 */
@HiltViewModel
class LostItemViewModel @Inject constructor(
    private val lostItemsRepo: LostItemRepository,
    private val storageRepo: ImageStorageRepository,
    private val authRepo: AuthRepository,
) : ViewModel() {

    /** SSoC lost items list state object. */
    private val _lostItemListState =
        MutableStateFlow<Response<LostItemList>>(Success(null))

    /** Lost items list state. */
    val lostItemListState = _lostItemListState.asStateFlow()

    /** State with reflects CRUD lost items operations. */
    var crudLostItemState by mutableStateOf<Response<Boolean>>(Success(null))
        private set

    /** State for one WIP lost item object. */
    var lostItemState by mutableStateOf<Response<LostItem>>(Success(null))
        private set

    /** Applied full-text search filters. */
    val filters = mutableStateListOf<String>()

    /**
     * Fulltext search.
     *
     * Future application should use a dedicated third-party search service.
     * These services provide advanced indexing and search capabilities far
     * beyond what any simple database query can offer and what in this case is
     * an overkill.
     *
     * As temporary solution (as we except small user base) we will compute
     * these queries locally.
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
                fullTextSearch(terms = filters, targets = targets)
            }
            return@withContext LostItemList(filteredList)
        }
    }

    /**
     * Fetches sorted (by date) lost items and filters them by [filters].
     *
     * @param repoCall repository call with [LostItemList].
     */
    private fun fetchLostItems(
        repoCall: suspend () -> Response<Flow<LostItemList>>
    ) {
        viewModelScope.launch {
            _lostItemListState.update { Loading }
            when (val apiFlowResponse = repoCall()) {
                is Error -> _lostItemListState.update {
                    Error(apiFlowResponse.error)
                }
                is Success -> {
                    if (apiFlowResponse.data != null) {
                        try {
                            apiFlowResponse.data
                                .collect { lostItemList ->
                                    val filteredList = filterLostItemList(lostItemList)
                                    _lostItemListState.update {
                                        Success(filteredList)
                                    }
                                }
                        } catch (e: Exception) {
                            _lostItemListState.update {
                                Success(null)
                            }
                            Log.e(
                                "LostItemViewModel",
                                "fetchLostItems: ${e.message}"
                            )
                        }
                    } else {
                        _lostItemListState.update { Success(null) }
                    }
                }
                Loading -> {}
            }
        }
    }

    /**
     * Fetches all lost items that are sorted by date and filtered by
     * [filters].
     */
    fun loadLostItems() {
        fetchLostItems { lostItemsRepo.getLostItemListFlow() }
    }

    /** Fetches current user's posts with lost items. */
    fun loadMyItems() {
        fetchLostItems { lostItemsRepo.getLostItemListFlowFromCurrentUser() }
    }

    /**
     * Gets lost item based on the passed [id].
     *
     * @param id of lost item.
     */
    fun getLostItem(id: String) {
        viewModelScope.launch {
            lostItemState = lostItemsRepo.getLostItem(id)
        }
    }

    /**
     * Deletes lost item [lostItem].
     *
     * @param lostItem to delete.
     */
    fun deleteLostItem(lostItem: LostItem) {
        viewModelScope.launch {
            updateLostItem(
                lostItem.copy(
                    isDeleted = true,
                )
            )
        }
    }

    /**
     * Creates new lost item.
     *
     * @param title of the new lost item post.
     * @param description of the new lost item post.
     * @param localImageUri of the lost item.
     * @param location of the item that was found.
     */
    fun createLostItem(
        title: String,
        description: String,
        localImageUri: Uri?,
        location: LocationCoordinates?,
    ) {
        viewModelScope.launch {
            crudLostItemState = Loading
            when (val currentUser = authRepo.getCurrentUser()) {
                is Error -> crudLostItemState = Error(currentUser.error)
                Loading -> { /* do nothing */
                }
                is Success -> {
                    if (currentUser.data == null) {
                        crudLostItemState = Error(Exception("user not logged in"))
                    } else {
                        val lostItem = LostItem(
                            title = title,
                            description = description,
                            imageUri = localImageUri,
                            postOwnerId = currentUser.data.id,
                            location = location,
                        )
                        writeLostItemWithLocalImage(lostItem)
                    }
                }
            }
        }
    }

    /**
     * Creates/updates lost item [lostItem] with local image uri.
     *
     * @param lostItem to write.
     */
    private fun writeLostItemWithLocalImage(lostItem: LostItem) {
        viewModelScope.launch {
            val imageUri = lostItem.imageUri
            crudLostItemState = Loading
            if (imageUri == null) {
                crudLostItemState = lostItemsRepo.createLostItem(lostItem = lostItem)
            } else {
                when (val remoteImageUriResponse = storageRepo.addImageToStorage(
                    imageUri = imageUri,
                    name = lostItem.id,
                )) {
                    is Loading -> { /* do nothing */
                    }
                    is Success -> {
                        // Item got assigned uri from firebase storage, so we can save it in
                        // lightweight database now.
                        val lostItemWithAssignedUri =
                            lostItem.copy(imageUri = remoteImageUriResponse.data)
                        crudLostItemState =
                            lostItemsRepo.createLostItem(lostItem = lostItemWithAssignedUri)
                    }
                    is Error -> crudLostItemState = Error(remoteImageUriResponse.error)
                }
            }
        }
    }

    /**
     * Creates/updates lost item [lostItem] with remote image uri.
     *
     * @param lostItem to write.
     */
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
                        lostItemsRepo.updateLostItem(lostItem)
                    }
                }
            }
        }
    }

    /**
     * Updates lost items [lostItem].
     *
     * @param lostItem to update.
     * @param hasRemoteUri `true` if the image has remote uri, `false` if the
     *     image has local uri.
     */
    fun updateLostItem(lostItem: LostItem, hasRemoteUri: Boolean = true) {
        if (hasRemoteUri) {
            writeLostItemWithRemoteImage(lostItem = lostItem)
        } else {
            writeLostItemWithLocalImage(lostItem = lostItem)
        }
    }

    /**
     * Gets [LocaleTimeString] with correct locale datetime utils.
     *
     * @param context interface to global information about an application
     *     environment.
     * @return instance of datetime utils [LocaleTimeString].
     */
    fun getLocaleTimeString(context: Context): LocaleTimeString {
        return LocaleTimeStringImpl(context)
    }
}

