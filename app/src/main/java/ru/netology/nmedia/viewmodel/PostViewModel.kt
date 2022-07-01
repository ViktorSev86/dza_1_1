package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data by repository::data

    private val newPost = Post(
        id = PostRepository.NEW_POST_ID,
        author = "Me",
        content = "content",
        published = "01.07.2022",
        likedByMe = false,
        countLikes = 0u,
        countShare = 0u,
        countGlaz = 0u
    )

    override fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return

        repository.save(newPost.copy(content = content))
    }

    override fun onLikeClicked(post: Post) =
        repository.likeById(post.id)

    override fun onShareClicked(post: Post) =
        repository.shareById(post.id)

    override fun onGlazClicked(post: Post) =
        repository.glazById(post.id)

    override fun onRemoveClicked(post: Post) =
        repository.delete(post.id)

    val edited = MutableLiveData(newPost)

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = newPost
    }

}