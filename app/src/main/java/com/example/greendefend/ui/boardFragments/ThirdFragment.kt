package com.example.greendefend.ui.boardFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.greendefend.R
import com.example.greendefend.ui.authenticationFragments.AuthenticationActivity
import com.example.greendefend.databinding.FragmentThirdBinding


class ThirdFragment : Fragment() {
private lateinit var binding: FragmentThirdBinding
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
        binding= FragmentThirdBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            requireActivity().findViewById<ViewPager2>(R.id.viewPagerFrgment).currentItem=+1
          startActivity(Intent(requireActivity(), AuthenticationActivity::class.java))
        }

    }


}