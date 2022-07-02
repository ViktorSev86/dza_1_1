package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class PostRepositoryInMemoryImpl : PostRepository {

    private var nextId = GENERATED_POST_AMOUNT.toLong()

    private val posts
    get() = checkNotNull(data.value) {
        "Data value should not be null"
    }

    override val data = MutableLiveData(
        List(GENERATED_POST_AMOUNT) {index ->
            Post(
                id = index + 1L,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Some random content $index",
                published = "30.06.2022",
                likedByMe = false,
                countLikes = 0u,
                countShare = 0u,
                countGlaz = 0u
            )
        })

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        data.value = posts.map {
            if (it.id != id) it
            else it.copy(likedByMe = !it.likedByMe,
                countLikes = if (!it.likedByMe) it.countLikes + 1u else it.countLikes - 1u)
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        data.value = posts.map {
            if (it.id != id) it
            else it.copy(countShare = it.countShare + 1u)
        }
        data.value = posts
    }

    override fun glazById(id: Long) {
        data.value = posts.map {
            if (it.id != id) it
            else it.copy(countGlaz = it.countGlaz + 1u)
        }
        data.value = posts
    }

    override fun delete(postId: Long) {
        data.value = posts.filter {
            it.id != postId
        }
        data.value = posts
    }

    override fun save(post: Post, content: String) {
        if (post.id == 0L) {
            data.value = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now",
                    content = content
                )
            ) + posts
        } else {
            data.value = posts.map { if (it.id != post.id) it else it.copy(content = content) }
        }
    }


    private companion object {
        const val GENERATED_POST_AMOUNT = 100
    }
}