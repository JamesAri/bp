package cz.zcu.students.lostandfound.lost_items.presentation.lost_items

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.lost_items.domain.repository.LostItemRepository
import cz.zcu.students.lostandfound.util.Resource
import cz.zcu.students.lostandfound.lost_items.presentation.util.ResourceState
import cz.zcu.students.lostandfound.lost_items.presentation.util.collectResource
import cz.zcu.students.lostandfound.lost_items.presentation.util.setData
import cz.zcu.students.lostandfound.lost_items.presentation.util.setLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LostItemViewModel @Inject constructor(
    private val repo: LostItemRepository
) : ViewModel() {

    private val _lostItemsState = MutableStateFlow(ResourceState<Flow<List<LostItem>>>())
    val lostItemsState = _lostItemsState.asStateFlow()

    var lostItemState by mutableStateOf(ResourceState<LostItem>())
        private set

    fun loadLostItems() {
        _lostItemsState.collectResource(viewModelScope, repo::getLostItemListFlow)
    }

    fun getLostItem(
        id: String
    ) {
        // TODO
    }

    fun createLostItem(
        lostItem: LostItem
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.createLostItem(lostItem) {
                Log.d("LostItemViewModel", "createLostItem id: $it")
            }
        }
    }
}
