package com.example.greendefend.ui.homing.home.disease_plants

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.greendefend.R
import com.example.greendefend.databinding.FragmentDiseasePlantsBinding
import com.example.greendefend.domin.model.home.QuestionAnswer
import com.example.greendefend.ui.homing.HomeActivity

class DiseasePlantsFragment : Fragment() {
    private lateinit var binding:FragmentDiseasePlantsBinding
    private val listQuestion1:MutableList<QuestionAnswer> = mutableListOf()
    private val adapter:AdapterQuestionAnswer by lazy {AdapterQuestionAnswer(listQuestion1)  }
    private val args:DiseasePlantsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment

        binding=FragmentDiseasePlantsBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val passedNumber=args.buttonNumber


        when (passedNumber) {
            1 -> {
                binding.titleToolbar.text=getString(R.string.crop_information)
                listQuestion1.add(QuestionAnswer(getString(R.string.q1_1),getString(R.string.a1_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q2_1),getString(R.string.a2_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q3_1),getString(R.string.a3_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q4_1),getString(R.string.a4_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q5_1),getString(R.string.a5_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q6_1),getString(R.string.a6_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q7_1),getString(R.string.a7_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q8_1),getString(R.string.a8_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q9_1),getString(R.string.a9_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q10_1),getString(R.string.a10_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q11_1),getString(R.string.a11_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q12_1),getString(R.string.a12_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q13_1),getString(R.string.a13_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q14_1),getString(R.string.a14_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q15_1),getString(R.string.a15_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q16_1),getString(R.string.a16_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q17_1),getString(R.string.a17_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q18_1),getString(R.string.a18_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q19_1),getString(R.string.a19_1)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q20_1),getString(R.string.a20_1)))
            }
            2 -> {
                binding.titleToolbar.text= getString(R.string.plant_diseases)
                listQuestion1.add(QuestionAnswer(getString(R.string.q1_2),getString(R.string.a1_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q2_2),getString(R.string.a2_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q3_2),getString(R.string.a3_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q4_2),getString(R.string.a4_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q5_2),getString(R.string.a5_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q6_2),getString(R.string.a6_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q7_2),getString(R.string.a7_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q8_2),getString(R.string.a8_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q9_2),getString(R.string.a9_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q10_2),getString(R.string.a10_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q11_2),getString(R.string.a11_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q12_2),getString(R.string.a12_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q13_2),getString(R.string.a13_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q14_2),getString(R.string.a14_2)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q15_2),getString(R.string.a15_2)))
            }
            3 -> {
                binding.titleToolbar.text= getString(R.string.plant_observation)
                listQuestion1.add(QuestionAnswer(getString(R.string.q1_3),getString(R.string.a1_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q2_3),getString(R.string.a2_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q3_3),getString(R.string.a3_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q4_3),getString(R.string.a4_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q5_3),getString(R.string.a5_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q6_3),getString(R.string.a6_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q7_3),getString(R.string.a7_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q8_3),getString(R.string.a8_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q9_3),getString(R.string.a9_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q10_3),getString(R.string.a10_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q11_3),getString(R.string.a11_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q12_3),getString(R.string.a12_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q13_3),getString(R.string.a13_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q14_3),getString(R.string.a14_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q15_3),getString(R.string.a15_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q16_3),getString(R.string.a16_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q17_3),getString(R.string.a17_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q18_3),getString(R.string.a18_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q19_3),getString(R.string.a19_3)))
                listQuestion1.add(QuestionAnswer(getString(R.string.q20_3),getString(R.string.a20_3)))
            }
        }


        binding.myRecyclerView2.adapter=adapter

    }
    override fun onAttach(context: Context) {
        (requireActivity() as HomeActivity).binding.toolbar.visibility=View.GONE
        super.onAttach(context)
    }

    override fun onDestroy() {
        (requireActivity() as HomeActivity).binding.toolbar.visibility=View.VISIBLE
        super.onDestroy()
    }


}