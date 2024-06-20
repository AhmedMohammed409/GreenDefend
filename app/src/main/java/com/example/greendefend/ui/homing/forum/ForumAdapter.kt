package com.example.greendefend.ui.homing.forum

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greendefend.R
import com.example.greendefend.databinding.RowPostBinding
import com.example.greendefend.domin.model.forum.Post

class PostAdapter(
    private val context: Context,
    private var onItemClicked: (post: Post) -> Unit,
    private var onLikeClicked: (postId: Int, likeState: String) -> Unit,
    private var onDisLikeClicked: (postId: Int, likeState: String) -> Unit,
    private var onCommentClicked: (postId: Post) -> Unit,
) : ListAdapter<Post, PostAdapter.ViewHolder>(PostsDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        return ViewHolder(RowPostBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        holder.bind(
            currentList[position],
            onItemClicked,
            onLikeClicked,
            onDisLikeClicked,
            onCommentClicked
        )
    }

    inner class ViewHolder(private val binding: RowPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(
            item: Post, onItemClicked: (post: Post) -> Unit,
            onLikeClicked: (postId: Int, likeState: String) -> Unit,
            onDisLikeClicked: (postId: Int, likeState: String) -> Unit,
            onCommentClicked: (post: Post) -> Unit
        ) {

            binding.btnLike.setOnClickListener {
                item.likeStatus = if (item.likeStatus == "Yes") {
                    ""
                } else {
                    "Yes"
                }

                onLikeClicked(item.postId!!, item.likeStatus!!)
            }
            binding.btnDislike.setOnClickListener {
                item.likeStatus = if (item.likeStatus == "No") {
                    ""
                } else {
                    "No"
                }

                onDisLikeClicked(item.postId!!, item.likeStatus!!)
            }
            binding.btnComment.setOnClickListener {
                onCommentClicked(item)
            }
            binding.root.setOnClickListener {
                onItemClicked(item)
            }

            binding.post = item
            when (item.likeStatus) {
                "Yes" -> {
                    binding.btnLike.background = context.getDrawable(R.color.state)
                    binding.btnDislike.background = null
                }

                "No" -> {
                    binding.btnDislike.background = context.getDrawable(R.color.state)
                    binding.btnLike.background = null
                }

                else -> {
                    binding.btnLike.background = null
                    binding.btnDislike.background = null
                }

            }

            if (item.postImageURL == null) {
                binding.imgPost.visibility = View.GONE
            }
            Glide.with(context)
                .load(item.userImageURL)
                .into(binding.imgUser)

            Glide.with(context)
                .load(item.postImageURL)
                .into(binding.imgPost)




        }
    }
}

object PostsDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}
