//package com.example.greendefend.ui.desha
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.example.greendefend.R
//import com.example.greendefend.databinding.FragmentDieasesPlantsBinding
//import androidx.recyclerview.widget.LinearLayoutManager
//
//
//class DiseasesPlantsFragment : Fragment() {
//    private lateinit var binding:FragmentDieasesPlantsBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding=FragmentDieasesPlantsBinding.inflate(inflater,container,false)
//
//        var listQuestion1:MutableList<QuestionAnswer> = mutableListOf()
//        listQuestion1.add(QuestionAnswer(getString(R.string.q1_2),getString(R.string.a1_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q2_2),getString(R.string.a2_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q3_2),getString(R.string.a3_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q4_2),getString(R.string.a4_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q5_2),getString(R.string.a5_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q6_2),getString(R.string.a6_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q7_2),getString(R.string.a7_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q8_2),getString(R.string.a8_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q9_2),getString(R.string.a9_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q10_2),getString(R.string.a10_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q11_2),getString(R.string.a11_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q12_2),getString(R.string.a12_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q13_2),getString(R.string.a13_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q14_2),getString(R.string.a14_2)))
//        listQuestion1.add(QuestionAnswer(getString(R.string.q15_2),getString(R.string.a15_2)))
//
//
//
//
//        var adapter= AdapterQuestionAnswer(listQuestion1)
//        binding.myRecyclerView2.adapter=adapter
//
//        binding.myRecyclerView2.layoutManager=
//            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
//        // Inflate the layout for this fragment
//        return binding.root
//    }
//
//
//}
//
//
//
