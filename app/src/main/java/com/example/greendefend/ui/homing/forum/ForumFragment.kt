package com.example.greendefend.ui.homing.forum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.greendefend.Constants
import com.example.greendefend.databinding.FragmentForumBinding
import com.example.greendefend.domin.model.forum.Post
import com.example.greendefend.domin.model.forum.React
import com.example.greendefend.domin.useCase.viewModels.ForumViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForumFragment : Fragment() {
    private lateinit var binding: FragmentForumBinding
    private val forumViewModel: ForumViewModel by viewModels()

    private val adapter: PostAdapter by lazy {
        PostAdapter(requireContext(), onItemClicked = {
            findNavController().navigate(
                ForumFragmentDirections.actionForumFragmentToPostFragment(
                    it.postId!!,
                    it.likeStatus!!
                )
            )
        }, onLikeClicked = {
            reactAndObserve(React(true, Constants.Id, it))
        },
            onDisLikeClicked = {
                reactAndObserve(React(false, Constants.Id, it))
            }, onCommentClicked = {
                findNavController().navigate(
                    ForumFragmentDirections.actionForumFragmentToPostFragment(
                        it.postId!!,
                        it.likeStatus!!
                    )
                )
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        getPostAndObseve()

        binding.btnAsk.setOnClickListener {
            findNavController().navigate(ForumFragmentDirections.actionForumFragmentToAskingFragment())
        }
    }


private fun getPostAndObseve(){
        forumViewModel.getPosts()
    forumViewModel.liveDataPosts.observe(viewLifecycleOwner){
        if (it!=null){
            binding.progressBar.visibility=View.GONE
            adapter.submitList(it)
        }else{
            binding.progressBar.visibility=View.GONE
            Toast.makeText(requireContext(),"failed to get Post",Toast.LENGTH_SHORT).show()
        }
    }
    }

    private fun reactAndObserve(react: React) {
        forumViewModel.addReact(react)
        forumViewModel.updatePosts()
       // getPostAndObseve()
    }


}


//    private fun getPostAndObseve() {
//        forumViewModel.getPosts()
//        forumViewModel.response.observe(viewLifecycleOwner) { response ->
//            when (response) {
//                is NetworkResult.Success -> {
//                    binding.progressBar.visibility = View.GONE
//                    val posts: List<Post>? = (response.data as? List<*>)?.filterIsInstance<Post>()
//                    adapter.submitList(posts)
//                }
//
//                is NetworkResult.Error -> {
//                    binding.progressBar.visibility = View.GONE
//                    Log.e("MsgErr", response.toString())
//
//                }
//
//                is NetworkResult.Exception -> {
//                    binding.progressBar.visibility = View.GONE
//                    Log.e("MsgErr Exeption", response.e.toString())
//                }
//            }
//        }
//
//
//    }