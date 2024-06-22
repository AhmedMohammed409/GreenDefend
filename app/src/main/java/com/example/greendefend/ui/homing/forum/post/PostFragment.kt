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
    private lateinit var likeState: String
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

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPostDetailed(args.postId)
        likeState = args.likeStatus

        when (likeState) {
            "Yes" -> {
                binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(
                    requireContext().getDrawable(R.drawable.like),
                    null,
                    null,
                    null
                )
                binding.btnDislike.setCompoundDrawablesWithIntrinsicBounds(
                    requireContext().getDrawable(R.drawable.mdi_dislike),
                    null,
                    null,
                    null
                )

            }

            "No" -> {
                binding.btnDislike.setCompoundDrawablesWithIntrinsicBounds(
                    requireContext().getDrawable(R.drawable.dislike),
                    null,
                    null,
                    null
                )
                binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(
                    requireContext().getDrawable(R.drawable.mdi_like),
                    null,
                    null,
                    null
                )

            }
        }

        adapter = PostAdapter(requireContext())
        binding.rvComment.adapter = adapter
        getCommentsAndObserve()



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
        binding.btnLike.setOnClickListener {

            changeColorReact("Yes")
            reactAndObseve(React(true, Constants.Id, args.postId))
        }
        binding.btnDislike.setOnClickListener {
            changeColorReact("No")
            reactAndObseve(React(false, Constants.Id, args.postId))
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
                    //getCommentsAndObserve()
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

    private fun getPostDetailed(postId: Int) {
        forumViewModel.getPostDetail(postId)
        forumViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    postDetail = response.data as DetailPost
                    binding.post = postDetail
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
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun reactAndObseve(react: React) {
        forumViewModel.addReact(react)
        forumViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                }

                is NetworkResult.Error -> {}
                is NetworkResult.Exception -> {}
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun changeColorReact( click: String) {
        if (likeState == click) {
            binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(
                requireContext().getDrawable(R.drawable.mdi_like),
                null,
                null,
                null
            )
            binding.btnDislike.setCompoundDrawablesWithIntrinsicBounds(
                requireContext().getDrawable(R.drawable.mdi_dislike),
                null,
                null,
                null
            )
            likeState = ""
        } else if (click == "Yes") {
            binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(
                requireContext().getDrawable(R.drawable.like),
                null,
                null,
                null
            )
            binding.btnDislike.setCompoundDrawablesWithIntrinsicBounds(
                requireContext().getDrawable(R.drawable.mdi_dislike),
                null,
                null,
                null
            )
            likeState = "Yes"
        } else if (click == "No") {
            binding.btnDislike.setCompoundDrawablesWithIntrinsicBounds(
                requireContext().getDrawable(R.drawable.dislike),
                null,
                null,
                null

            )
            binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(
                requireContext().getDrawable(R.drawable.mdi_like),
                null,
                null,
                null
            )
            likeState = "No"
        }
    }
}
