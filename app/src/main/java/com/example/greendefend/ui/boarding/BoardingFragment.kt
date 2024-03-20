package com.example.greendefend.ui.boarding


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.greendefend.model.OnboardingModel
import com.example.greendefend.databinding.FragmentBoardingBinding



class BoardingFragment : Fragment() {
private lateinit var binding:FragmentBoardingBinding

    private var title: String? = null
    private var description: String? = null
    private var imageResource = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments!=null){
             title = requireArguments().getString(ARG_PARAM1)
             description= requireArguments().getString(ARG_PARAM2)
             imageResource = requireArguments().getInt((ARG_PARAM3))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding= FragmentBoardingBinding.inflate(inflater,container,false)
        binding.txtTitle.text=title
        binding.txtDescription.text = description
        binding.imgBackground.setImageResource(imageResource)
        return binding.root
    }





    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "param3"
        fun newInstance(onboardingModel: OnboardingModel): BoardingFragment {
            val fragment = BoardingFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, onboardingModel.title)
            args.putString(ARG_PARAM2, onboardingModel.disception)
            args.putInt(ARG_PARAM3, onboardingModel.backgroundImage!!.toInt())
            fragment.arguments = args
            return fragment
        }
    }

}