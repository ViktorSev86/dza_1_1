package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
//import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val post = Post(
            id = 1L,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            countLikes = 999u,
            countShare = 1999u,
            countGlaz = 999999u
        )*/

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

                /*    post.likedByMe = !post.likedByMe
                    var imageResId = R.drawable.ic_baseline_favorite_border_24
                    if (post.likedByMe) {
                        imageResId = R.drawable.ic_liked_favorite_24
                        post.countLikes++
                    } else {
                        imageResId = R.drawable.ic_baseline_favorite_border_24
                        post.countLikes--
                    }
                    likes.setImageResource(imageResId)
                    countLikes.text = post.countLikes.uIntToString()

                 */
                }
                share.setOnClickListener {
                    post.countShare++
                    countShare.text = post.countShare.uIntToString()
                }

                glaz.setOnClickListener {
                    post.countGlaz++
                    countGlaz.text = post.countGlaz.uIntToString()
                }
            }
        }
    }
}