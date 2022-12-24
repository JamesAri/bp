package cz.zcu.students.lostandfound.lost_items.presentation.lost_items

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.lost_items.domain.repository.LostItemRepository
import cz.zcu.students.lostandfound.lost_items.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO: make generic Resource handling

@HiltViewModel
class LostItemViewModel @Inject constructor(
    private val repo: LostItemRepository
) : ViewModel() {

    private val _lostItemsState = MutableStateFlow(LostItemsState())
    val postsState = _lostItemsState.asStateFlow()

    var lostItemState by mutableStateOf(LostItemState())
        private set


    fun loadLostItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _lostItemsState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }
            when (val result = repo.getLostItemList()) {
                is Resource.Success -> {
                    _lostItemsState.update {
                        it.copy(
                            lostItems = result.data,
                            isLoading = false,
                            error = null,
                        )
                    }
                }
                is Resource.Error -> {
                    _lostItemsState.update {
                        it.copy(
                            lostItems = null,
                            isLoading = false,
                            error = result.message,
                        )
                    }
                }
            }
        }
    }

    fun getLostItem(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        lostItemState = lostItemState.copy(
            isLoading = true,
            error = null,
        )
        when (val result = repo.getLostItem(id)) {
            is Resource.Success -> {
                lostItemState = lostItemState.copy(
                    lostItem = result.data,
                    isLoading = false,
                    error = null,
                )
            }
            is Resource.Error -> {
                lostItemState = lostItemState.copy(
                    lostItem = null,
                    isLoading = false,
                    error = result.message,
                )
            }
        }
    }

    fun createLostItem(lostItem: LostItem) = viewModelScope.launch(Dispatchers.IO){
        repo.createLostItem(lostItem) // todo: error handling
    }

}