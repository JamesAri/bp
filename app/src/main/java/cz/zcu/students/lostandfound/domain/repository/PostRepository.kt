package cz.zcu.students.lostandfound.domain.repository

import cz.zcu.students.lostandfound.domain.post.Post
import cz.zcu.students.lostandfound.domain.util.Resource

interface PostRepository {
    suspend fun getPosts(): Resource<List<Post>>
    suspend fun getPost(id: Int): Resource<Post>
}