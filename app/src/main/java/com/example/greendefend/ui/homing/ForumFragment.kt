package com.example.greendefend.ui.homing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.fragment.findNavController
import com.example.greendefend.R
import com.example.greendefend.databinding.FragmentForumBinding

class ForumFragment : Fragment() {
private lateinit var binding:FragmentForumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentForumBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    }



}