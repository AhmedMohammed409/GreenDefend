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
    private var onLikeClicked: (postId: Int) -> Unit,
    private var onDisLikeClicked: (postId: Int) -> Unit,
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
            onLikeClicked: (postId: Int) -> Unit,
            onDisLikeClicked: (postId: Int) -> Unit,
            onCommentClicked: (post: Post) -> Unit
        ) {


            binding.post = item
            if (item.postImageURL == null) {
                binding.imgPost.visibility = View.GONE
            }
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

            Glide.with(context)
                .load(item.userImageURL)
                .into(binding.imgUser)

            Glide.with(context)
                .load(item.postImageURL)
                .into(binding.imgPost)

            binding.btnLike.setOnClickListener {
                onLikeClicked(item.postId!!)
            }

            binding.btnDislike.setOnClickListener {
                onDisLikeClicked(item.postId!!)
            }
            binding.btnComment.setOnClickListener {
                onCommentClicked(item)
            }
            binding.root.setOnClickListener {
                onItemClicked(item)
            }


        }
    }
}

object PostsDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return (oldItem.postId == newItem.postId
                && oldItem.likeStatus == newItem.likeStatus
                && oldItem.commentsCount == newItem.commentsCount)
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}
