package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter (
            onLikeClicked = {post ->  viewModel.likeById(post.id)},
            onShareClicked = {post ->  viewModel.shareById(post.id)},
            onGlazClicked = {post ->  viewModel.glazById(post.id)}
        )
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.list = posts
        }

        /*
        fun UInt.uIntToString():String {  // Переделать расширением типа
            val res = when {
                this < 1000u -> this.toString()
                this < 10000u -> "${this/1000u}.${this/100u%10u}K"
                this < 1000000u -> "${this/1000u}K"
                this < 10000000u -> "${this/1000000u}.${this/100000u%10u}M"
                this < 100000000u -> "${this/1000000u}M"
                else -> "много"
            }
            return res
        }

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                avatar.setImageResource(R.drawable.ic_channel_foreground)
                authorName.text = post.author
                date.text = post.published
                text.text = post.content
                countLikes.text = post.countLikes.uIntToString()
                countShare.text = post.countShare.uIntToString()
                countGlaz.text = post.countGlaz.uIntToString()
                likes.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                )

                likes.setOnClickListener {
                    viewModel.like()

                }
                share.setOnClickListener {
                    viewModel.share()
                }

                glaz.setOnClickListener {
                    viewModel.glaz()
                }
            }
        }
        */
    }
}