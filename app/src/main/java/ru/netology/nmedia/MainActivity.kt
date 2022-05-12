package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1L,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            countLikes = 999u,
            countShare = 1999u,
            countGlaz = 999999u
        )

        with(binding) {
            avatar.setImageResource(R.drawable.ic_channel_foreground)
            authorName.text = post.author
            date.text = post.published
            text.text = post.content
            countLikes.text = post.uIntToString(post.countLikes)
            countShare.text = post.uIntToString(post.countShare)
            countGlaz.text = post.uIntToString(post.countGlaz)

            likes.setOnClickListener {
                post.likedByMe = !post.likedByMe
                var imageResId = R.drawable.ic_baseline_favorite_border_24
                if (post.likedByMe) {
                    imageResId = R.drawable.ic_liked_favorite_24
                    post.countLikes++
                } else {
                    imageResId = R.drawable.ic_baseline_favorite_border_24
                    post.countLikes--
                }
                likes.setImageResource(imageResId)
                countLikes.text = post.uIntToString(post.countLikes)
            }

            share.setOnClickListener {
                post.countShare++
                countShare.text = post.uIntToString(post.countShare)
            }

            glaz.setOnClickListener {
                post.countGlaz++
                countGlaz.text = post.uIntToString(post.countGlaz)
            }
        }

    }
}