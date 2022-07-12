package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.view.View
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.card_post.view.*
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        run {
            val preferences = getPreferences(Context.MODE_PRIVATE)
            preferences.edit {
                putString("key", "value")
            }
        }

        run {
            val preferences = getPreferences(Context.MODE_PRIVATE)
            val value = preferences.getString("key", "no value") ?: return@run
            Snackbar.make(binding.root, value, Snackbar.LENGTH_INDEFINITE).show()
        }

        val viewModel: PostViewModel by viewModels()

        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.save(result)
        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch("")
        }

        val adapter = PostsAdapter(object : PostInteractionListener {

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                newPostLauncher.launch(post.content)
            }

            override fun onVideo(post: Post) {
                val intent = Intent(Intent(Intent.ACTION_VIEW, Uri.parse(post.video)))

                val videoIntent =
                    Intent.createChooser(intent, "VIDEO")
                startActivity(videoIntent)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
        })

        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }


    }
}
