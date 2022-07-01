package ru.netology.nmedia.adapter

import ru.netology.nmedia.dto.Post

interface PostInteractionListener {
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onGlazClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun edit(post: Post)
    fun save(content: String)
}