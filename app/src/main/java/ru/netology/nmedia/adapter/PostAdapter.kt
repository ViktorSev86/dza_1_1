package ru.netology.nmedia.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias OnLikeListener = (post: Post) -> Unit

class PostsAdapter(private val onLikeListener: OnLikeListener) : RecyclerView.Adapter<PostViewHolder>() {
    var list = emptyList<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener
) : RecyclerView.ViewHolder(binding.root) {

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

    fun bind(post: Post) {
        binding.apply {
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
                onLikeListener(post)

            }

            share.setOnClickListener {
                onLikeListener(post)
            }

            glaz.setOnClickListener {
                onLikeListener(post)
            }

        }
    }
}