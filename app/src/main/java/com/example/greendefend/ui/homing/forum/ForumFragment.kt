package com.example.greendefend.ui.homing.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.greendefend.R
import com.example.greendefend.databinding.FragmentForumBinding
import com.example.greendefend.domin.useCase.ForumViewModel
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
            )
        })
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

        val actiotogle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawer,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawer.addDrawerListener(actiotogle)

        binding.searchBar.setOnClickListener {
            findNavController().navigate(ForumFragmentDirections.actionForumFragmentToSearchFragment())
        }
        binding.btnAsk.setOnClickListener {
            findNavController().navigate(ForumFragmentDirections.actionForumFragmentToAskingFragment())
        }

    }

    private fun getPostAndObseve() {
        forumViewModel.getPosts()
        forumViewModel.posts.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }


}