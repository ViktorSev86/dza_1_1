package ru.netology.nmedia.adapter

import ru.netology.nmedia.dto.Post

interface PostInteractionListener {
    fun onEdit(post: Post)
    fun onLike(post: Post)
    fun onRemove(post: Post)
    fun onShare(post: Post)
}