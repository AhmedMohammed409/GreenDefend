package com.example.greendefend.ui.homing.forum.post

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.greendefend.Constants
import com.example.greendefend.R
import com.example.greendefend.databinding.FragmentPostBinding
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.DetailPost
import com.example.greendefend.domin.model.forum.React
import com.example.greendefend.domin.useCase.viewModels.ForumViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding
    private lateinit var postDetail: DetailPost
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
        getPostDetailed(args.postId)


        adapter = PostAdapter(requireContext())
        binding.rvComment.adapter = adapter
        getCommentsAndObserve()




        binding.btnLike.setOnClickListener {
            reactAndObseve(React(true, Constants.Id, args.postId))

        }
        binding.btnDislike.setOnClickListener {

        }
        binding.btnSend.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            addCommentAndObserve(
                Comment(
                    binding.etComment.text.toString(),
                    Constants.Id,
                    args.postId
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
        forumViewModel.getPostDetail(args.postId)
        forumViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val result = response.data as DetailPost
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
    private fun getPostDetailed(postId:Int){
        forumViewModel.getPostDetail(postId)
        forumViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                   postDetail = response.data as DetailPost
                    binding.post=postDetail
                    Glide.with(requireContext()).load(postDetail.postImageURL).into(binding.imgPost)
                    Glide.with(requireContext()).load(postDetail.userImageURL).into(binding.imgUser)
                    Glide.with(requireContext()).load(Constants.imageUrl).into(binding.imageMe)

                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("err comment", response.errMsg + response.code)
//                    Toast.makeText(requireContext(), "Get Comments is Failed", Toast.LENGTH_SHORT)
//                        .show()
                }

                is NetworkResult.Exception -> {}
            }
    }}

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun reactAndObseve(react: React) {
        forumViewModel.addReact(react)
        forumViewModel.response.observe(viewLifecycleOwner){response->
            when(response){
                is NetworkResult.Success->{
                    when (args.likeStatus) {
                        "Yes" -> {
                            binding.btnLike.background = requireContext().getDrawable(R.color.state)
                            binding.btnDislike.background = null
                        }

                        "No" -> {
                            binding.btnDislike.background = requireContext().getDrawable(R.color.state)
                            binding.btnLike.background = null
                        }

                        else -> {
                            binding.btnLike.background = null
                            binding.btnDislike.background = null
                        }
                    }
                }
                is  NetworkResult.Error->{}
                is NetworkResult.Exception->{}
            }
        }
    }
}
