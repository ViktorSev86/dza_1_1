package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel(), PostInteractionListener {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    override fun onLikeClicked(post: Post) =
        repository.likeById(post.id)

    override fun onShareClicked(post: Post) =
        repository.shareById(post.id)

    override fun onGlazClicked(post: Post) =
        repository.glazById(post.id)

    override fun onRemoveClicked(post: Post) =
        repository.delete(post.id)



    //fun share() = repository.share()
    //fun glaz() = repository.glaz()
}