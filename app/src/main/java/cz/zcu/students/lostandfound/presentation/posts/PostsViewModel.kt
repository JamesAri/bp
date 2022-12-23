package cz.zcu.students.lostandfound.presentation.posts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.zcu.students.lostandfound.domain.repository.PostRepository
import cz.zcu.students.lostandfound.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO: make generic Resource handling

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repo: PostRepository
) : ViewModel() {

    private val _postsState = MutableStateFlow(PostsState())
    val postsState = _postsState.asStateFlow()

    var postState by mutableStateOf(PostState())
        private set

    fun loadPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            _postsState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }
            when (val result = repo.getPosts()) {
                is Resource.Success -> {
                    _postsState.update {
                        it.copy(
                            posts = result.data,
                            isLoading = false,
                            error = null,
                        )
                    }
                }
                is Resource.Error -> {
                    _postsState.update {
                        it.copy(
                            posts = null,
                            isLoading = false,
                            error = result.message,
                        )
                    }
                }
            }
        }
    }

    fun getPost(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        postState = postState.copy(
            isLoading = true,
            error = null,
        )
        when (val result = repo.getPost(id)) {
            is Resource.Success -> {
                postState = postState.copy(
                    post = result.data,
                    isLoading = false,
                    error = null,
                )
            }
            is Resource.Error -> {
                postState = postState.copy(
                    post = null,
                    isLoading = false,
                    error = result.message,
                )
            }
        }
    }
}