package cz.zcu.students.lostandfound.presentation.posts

import cz.zcu.students.lostandfound.domain.post.Post

data class PostsState(
    val posts: List<Post>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

data class PostState(
    val post: Post? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
