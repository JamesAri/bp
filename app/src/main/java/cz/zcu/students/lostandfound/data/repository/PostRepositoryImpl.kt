package cz.zcu.students.lostandfound.data.repository

import cz.zcu.students.lostandfound.data.mappers.toPost
import cz.zcu.students.lostandfound.data.mappers.toPosts
import cz.zcu.students.lostandfound.data.remote.PostApi
import cz.zcu.students.lostandfound.domain.post.Post
import cz.zcu.students.lostandfound.domain.repository.PostRepository
import cz.zcu.students.lostandfound.domain.util.Resource
import javax.inject.Inject


class PostRepositoryImpl @Inject constructor(
    private val api: PostApi
) : PostRepository {

    override suspend fun getPosts(): Resource<List<Post>> {
        return try {
            Resource.Success(
                data = api.getDtoPosts().toPosts()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getPost(id: Int): Resource<Post> {
        return try {
            Resource.Success(
                data = api.getDtoPost(id).toPost()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}