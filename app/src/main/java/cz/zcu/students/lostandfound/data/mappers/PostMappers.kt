package cz.zcu.students.lostandfound.data.mappers

import cz.zcu.students.lostandfound.data.remote.PostDto
import cz.zcu.students.lostandfound.data.remote.PostsDto
import cz.zcu.students.lostandfound.domain.post.Post

// TODO: api specific

fun PostsDto.toPosts(): List<Post> {
    return titles.zip(descriptions) { t, d ->
        Post(t, d)
    }
}

fun PostDto.toPost(): Post {
     return Post(title = title, description = description)
}