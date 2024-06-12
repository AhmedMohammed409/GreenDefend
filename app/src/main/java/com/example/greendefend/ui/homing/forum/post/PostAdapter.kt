package com.example.greendefend.ui.homing.forum.post

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greendefend.databinding.RowCommentBinding
import com.example.greendefend.domin.model.forum.CommentX

class PostAdapter(
    private val context: Context,
//    private var onLikeClicked: (likeState: CommentX) -> Unit,
//    private var onDisLikeClicked: (disLikeState: CommentX) -> Unit,

) : ListAdapter<CommentX, PostAdapter.ViewHolder>(CommentsDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        return ViewHolder(RowCommentBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        holder.bind(
            currentList[position]
//            onLikeClicked,
//            onDisLikeClicked
        )
    }

    inner class ViewHolder(private val binding: RowCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: CommentX,
//            onLikeClicked: (likeState: CommentX) -> Unit,
//            onDisLikeClicked: (disLikeState: CommentX) -> Unit,

            ) {

            binding.comment = item


            Glide.with(context)
                .load(item.userImageUrl)
                .into(binding.imgUser)

//            binding.btnLike.setOnClickListener {
//                onLikeClicked(item)
//            }
//
//            binding.btnDislike.setOnClickListener {
//                onDisLikeClicked(item)
//            }


        }
    }
}


object CommentsDiffUtil : DiffUtil.ItemCallback<CommentX>() {
    override fun areItemsTheSame(oldItem: CommentX, newItem: CommentX): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CommentX, newItem: CommentX): Boolean {
        return oldItem.commentID == newItem.commentID
    }
}
