package ru.netology.nmedia.adapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

class PostsAdapter(private val interactionListener: PostInteractionListener) : RecyclerView.Adapter<PostViewHolder>() {
    var list = emptyList<Post>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var post: Post

    private val popupMenu by lazy {
        PopupMenu(itemView.context, binding.options).apply {
            inflate(R.menu.options_post)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.remove -> {
                        listener.onRemoveClicked(post)
                        true
                    }
                    else -> false
                }
            }
        }
    }

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
        this.post = post
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

            options.setOnClickListener {popupMenu.show()}

            likes.setOnClickListener {
                listener.onLikeClicked(post)

            }

            share.setOnClickListener {
                listener.onShareClicked(post)
            }

            glaz.setOnClickListener {
                listener.onGlazClicked(post)
            }

        }
    }
}