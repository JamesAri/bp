package cz.zcu.students.lostandfound.data.remote

// TODO: api specific

interface PostApi {
    suspend fun getDtoPosts(): PostsDto
    suspend fun getDtoPost(id: Int): PostDto
}