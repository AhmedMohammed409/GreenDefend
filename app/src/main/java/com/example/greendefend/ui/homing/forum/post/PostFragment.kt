package com.example.greendefend.ui.homing.forum.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.greendefend.Constants
import com.example.greendefend.databinding.FragmentPostBinding
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.DetailPost
import com.example.greendefend.domin.useCase.viewModels.ForumViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding
    private val forumViewModel: ForumViewModel by viewModels()
    private val args: PostFragmentArgs by navArgs()

    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter=PostAdapter(requireContext())
        binding.rvComment.adapter = adapter
        getCommentsAndObserve()


        val post = args.post
        Glide.with(requireContext()).load(post.userImageURL).into(binding.imgUser)
        Glide.with(requireContext()).load(post.postImageURL).into(binding.imgPost)
        Glide.with(requireContext()).load(Constants.imageUrl).into(binding.imageMe)
        binding.rvComment.adapter = adapter


        binding.btnSend.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            addCommentAndObserve(
                Comment(
                    binding.etComment.text.toString(),
                    Constants.Id,
                    args.post.postId!!
                )
            )
            binding.etComment.text.clear()
        }
    }

    private fun addCommentAndObserve(comment: Comment) {
        forumViewModel.addComment(comment)
        forumViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.VISIBLE
//                    Toast.makeText(requireContext(), "Add Comment is Sucessful", Toast.LENGTH_SHORT)
//                        .show()

                    getCommentsAndObserve()
                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("err comment", response.errMsg + response.code)
//                    Toast.makeText(requireContext(), "Add Comment is Failed", Toast.LENGTH_SHORT)
//                        .show()
                }

                is NetworkResult.Exception -> {}
            }
        }

    }

    private fun getCommentsAndObserve() {
        forumViewModel.getPostDetail(args.post.postId!!)
        forumViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val result=response.data as DetailPost
                    adapter.submitList(result.comments)
                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("err comment", response.errMsg + response.code)
//                    Toast.makeText(requireContext(), "Get Comments is Failed", Toast.LENGTH_SHORT)
//                        .show()
                }

                is NetworkResult.Exception -> {}
            }
        }

    }

}
