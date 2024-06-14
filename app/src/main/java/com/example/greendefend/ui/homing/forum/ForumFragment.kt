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
import com.example.greendefend.databinding.FragmentForumBinding
import com.example.greendefend.domin.model.forum.Post
import com.example.greendefend.domin.useCase.viewModels.ForumViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForumFragment : Fragment() {
    private lateinit var binding: FragmentForumBinding
    private val forumViewModel: ForumViewModel by viewModels()
    private val adapter: PostAdapter by lazy {
        PostAdapter(requireContext(),onItemClicked = {
            findNavController().navigate(
                ForumFragmentDirections.actionForumFragmentToPostFragment(
                    it
                )
            ) }, onLikeClicked = {


        },
            onDisLikeClicked = {

            }
            ,onCommentClicked = {
                findNavController().navigate(
                    ForumFragmentDirections.actionForumFragmentToPostFragment(it)
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
        getPostAndObseve()
        binding.recyclerView.adapter = adapter




        binding.btnAsk.setOnClickListener {
            findNavController().navigate(ForumFragmentDirections.actionForumFragmentToAskingFragment())
        }

    }

    private fun getPostAndObseve() {
        forumViewModel.getPosts()
        forumViewModel.response.observe(viewLifecycleOwner){response->
            when(response){
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val result=response.data
                    if (result is List<*>){
                        val posts = result.filterIsInstance<Post>()
                        Log.e("Posts",posts.toString())
                        adapter.submitList(posts)
                    }

                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("MsgErr", response.toString())
                    Toast.makeText(requireContext(), response.errMsg.toString(), Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Exception -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("MsgErr Exeption", response.e.toString())
                    Toast.makeText(
                        requireContext(),
                        response.e.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


    }


}