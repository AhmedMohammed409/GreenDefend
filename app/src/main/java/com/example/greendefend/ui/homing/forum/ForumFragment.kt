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
import com.example.greendefend.ui.homing.HomeActivity
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForumFragment : Fragment() {
    private val tag="ForumFragment"
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
        }, onLikeClicked = { id, _ ->

            addReactAndObserve(React(true, Constants.Id, id))
//            Toast.makeText(requireContext(), "local$like",Toast.LENGTH_SHORT).show()
        },
            onDisLikeClicked = { id, _ ->
                addReactAndObserve(React(false, Constants.Id, id))
             //   Toast.makeText(requireContext(), "local$like",Toast.LENGTH_SHORT).show()
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




    private fun addReactAndObserve(react: React) {
        forumViewModel.addReact(react)
        forumViewModel.liveDataReact.observe(viewLifecycleOwner){result->
            when(result){
                is NetworkResult.Success -> {
                    Log.e(tag,result.toString())
                }
                is NetworkResult.Error -> {
                    if (result.code==401){
                        Toast.makeText(requireContext(),result.errMsg,Toast.LENGTH_SHORT).show()
                        ( requireActivity() as HomeActivity).logoutAndObserve()
                    }
                    Toast.makeText(requireContext(),result.errMsg,Toast.LENGTH_SHORT).show()
                    Log.e("react",result.toString()) }


            }
        }

    }

    private fun getPostAndObseve() {
        forumViewModel.getPosts()
        forumViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    binding.progressBar.visibility = View.GONE
                    val posts: List<Post>? = (response.data as? List<*>)?.filterIsInstance<Post>()
                    Log.e("posts", posts.toString())
                    adapter.submitList(posts)
                }
                is NetworkResult.Error -> {
                    if (response.code==401){
                        ( requireActivity() as HomeActivity).logoutAndObserve()
                    }
                    if (response.code==700){
                        Toast.makeText(requireContext(),response.errMsg,Toast.LENGTH_SHORT).show()
                        ( requireActivity() as HomeActivity).logoutAndObserve()
                    }else if(response.code==600){
                        Toast.makeText(requireContext(),response.errMsg,Toast.LENGTH_SHORT).show()
                    }
                    binding.progressBar.visibility = View.GONE
                    Log.e("MsgErr", response.toString())
                }

            }
        }


    }


}


