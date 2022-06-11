package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val countLikes: UInt,
    val countShare: UInt,
    val countGlaz: UInt
)
