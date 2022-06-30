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



/*    private var post = Post(
        id = 1L,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        likedByMe = false,
        countLikes = 999u,
        countShare = 99u,
        countGlaz = 999999u
    )

 */

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

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) {
            insert(post)
        } else {
            update(post)
        }

    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }

    }

    private fun insert(post: Post) {
        data.value = posts + post

    }

    private companion object {
        const val GENERATED_POST_AMOUNT = 1000
    }

    /*   override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        if (post.likedByMe) {
            post.countLikes++
        } else {
            post.countLikes--
        }
        data.value = post
    }

    override fun share() {
        post.countShare++
        data.value = post
    }

    override fun glaz() {
        post.countGlaz++
        data.value = post
    }

  */
}