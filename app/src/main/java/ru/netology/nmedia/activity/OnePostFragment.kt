package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class OnePostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val viewHolder = PostViewHolder(binding.postLayout, object : PostInteractionListener {

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(
                    R.id.action_onePostFragment_to_newPostFragment,
                    Bundle().apply { textArg = post.content }
                )
            }

            override fun onVideo(post: Post) {
                val intent = Intent(Intent(Intent.ACTION_VIEW, Uri.parse(post.video)))

                val videoIntent =
                    Intent.createChooser(intent, "VIDEO")
                startActivity(videoIntent)
            }

            override fun toPost(post: Post) {
                viewModel.toPostById(post.id)
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

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == arguments?.textArg?.toLong() } ?: run {
                findNavController().popBackStack()
                return@observe
            }
            viewHolder.bind(post)
        }

        return binding.root
    }
}