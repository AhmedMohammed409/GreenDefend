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
//            binding.btnComment.background = null
            when (item.likeStatus) {
                "Yes" -> {
                    binding.btnLike.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        context.getDrawable(R.drawable.like),
                        null,
                        null,
                        null
                    )
                    binding.btnDislike.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        context.getDrawable(R.drawable.mdi_dislike),
                        null,
                        null,
                        null
                    )

                }

                "No" -> {
                    binding.btnDislike.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        context.getDrawable(R.drawable.dislike),
                        null,
                        null,
                        null
                    )
                    binding.btnLike.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        context.getDrawable(R.drawable.mdi_like),
                        null,
                        null,
                        null
                    )

                }
            }
            binding.btnLike.setOnClickListener {
                changeColorReact(item.likeStatus!!, "Yes", item, binding)
                onLikeClicked(item.postId!!, item.likeStatus!!)
            }
            binding.btnDislike.setOnClickListener {
                changeColorReact(item.likeStatus!!, "No", item, binding)
                onDisLikeClicked(item.postId!!, item.likeStatus!!)
            }
            binding.btnComment.setOnClickListener {
                onCommentClicked(item)
            }
            binding.root.setOnClickListener {
                onItemClicked(item)
            }

            binding.post = item


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

    @SuppressLint("UseCompatLoadingForDrawables")
    fun changeColorReact(likeStatus: String, click: String, item: Post, binding: RowPostBinding) {
        if (likeStatus == click) {
            binding.btnLike.setCompoundDrawablesRelativeWithIntrinsicBounds(
                context.getDrawable(R.drawable.mdi_like),
                null,
                null,
                null
            )
            binding.btnDislike.setCompoundDrawablesRelativeWithIntrinsicBounds(
                context.getDrawable(R.drawable.mdi_dislike),
                null,
                null,
                null
            )
            item.likeStatus = ""
        } else if (click == "Yes") {
            binding.btnLike.setCompoundDrawablesRelativeWithIntrinsicBounds(
                context.getDrawable(R.drawable.like),
                null,
                null,
                null
            )
            binding.btnDislike.setCompoundDrawablesRelativeWithIntrinsicBounds(
                context.getDrawable(R.drawable.mdi_dislike),
                null,
                null,
                null
            )
            item.likeStatus = "Yes"
        } else if (click == "No") {
            binding.btnDislike.setCompoundDrawablesRelativeWithIntrinsicBounds(
                context.getDrawable(R.drawable.dislike),
                null,
                null,
                null

            )
            binding.btnLike.setCompoundDrawablesRelativeWithIntrinsicBounds(
                context.getDrawable(R.drawable.mdi_like),
                null,
                null,
                null
            )
            item.likeStatus = "No"
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
