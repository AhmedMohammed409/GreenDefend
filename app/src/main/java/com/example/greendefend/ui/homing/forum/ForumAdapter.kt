package com.example.greendefend.ui.homing.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greendefend.databinding.RowPostBinding
import com.example.greendefend.domin.model.forum.Post

class PostAdapter(
    private val context: Context,
    private var onItemClicked: (post: Post) -> Unit,
    private var onLikeClicked: (likeState: String) -> Unit,
    private var onDisLikeClicked: (disLikeState: String) -> Unit,
    private var onCommentClicked: (post: Post) -> Unit,
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
        fun bind(
            item: Post, onItemClicked: (item: Post) -> Unit,
            onLikeClicked: (likeState: String) -> Unit,
            onDisLikeClicked: (disLikeState: String) -> Unit,
            onCommentClicked: (item: Post) -> Unit
        ) {


            binding.post = item

            Glide.with(context)
                .load(item.userImageURL)
                .into(binding.imgUser)

            Glide.with(context)
                .load(item.postImageURL)
                .into(binding.imgPost)

            binding.btnLike.setOnClickListener {
                onLikeClicked(item.likeStatus!!)
            }

            binding.btnDislike.setOnClickListener {
                onDisLikeClicked(item.likeStatus!!)
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
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postId == newItem.postId
    }
}
