package com.example.greendefend.ui.homing
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.greendefend.Constants
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.databinding.FragmentProfileBinding
import com.example.greendefend.domin.model.account.UserData
import com.example.greendefend.domin.useCase.viewModels.AuthViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val tag="ProfileFragment"
    private lateinit var binding: FragmentProfileBinding
    private val authViewModel: AuthViewModel by viewModels()
    private  var result: UserData =UserData(Constants.Bio!!,Constants.Country,Constants.Email,Constants.fileName,
        Constants.imageUrl!!,Constants.Id)

    @Inject
    lateinit var dataStorePrefrenceImpl: DataStorePrefrenceImpl
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
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserData()
        Log.i(tag, result.toString())
        binding.btnShare.setOnClickListener {
    val intent=Intent()
    intent.action=Intent.ACTION_SEND
    intent.putExtra(Intent.EXTRA_TEXT,"${binding.txtTitleThreat.text}\n${binding.txtDescription.text}")
    intent.type="text/plain"
    startActivity(Intent.createChooser(intent,"Choose your APP"))
}
        binding.btnChangeprofile.setOnClickListener {
            (requireActivity() as HomeActivity).binding.toolbar.visibility=View.GONE

            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToChangeProfileFragment(
                    result
                )
            )
        }
    }

    private fun getUserData() {
        authViewModel.getUserData(Constants.Id)
        authViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    result = response.data as UserData
                    binding.txtName.text = result.fullName

                    (requireActivity() as HomeActivity).updateHeadearDrawer()
                    Log.i(tag,result.toString())

                        Glide.with(requireContext())
                            .load(result.imageUrl)
                            .into(binding.imgPerson)

                    saveLocal(result)
                    runBlocking {  saveAtPrefrences(result) }
                }

                is NetworkResult.Error -> {
                    if (response.code==401){
                        ( requireActivity() as HomeActivity).logoutAndObserve()
                    }else{
                        Toast.makeText(requireContext(),response.errMsg,Toast.LENGTH_SHORT).show()
                        Log.i(tag,response.toString())
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.txtName.text = Constants.Name
                    binding.txtBio.text = Constants.Bio
                    Glide.with(requireContext())
                        .load(Constants.imageUrl)
                        .into(binding.imgPerson)


                }

            }
        }
    }


    private suspend fun saveAtPrefrences(userData: UserData) {
        dataStorePrefrenceImpl.putPreference(
            DataStorePrefrenceImpl.Country_KEY,
            userData.country
        )
        dataStorePrefrenceImpl.putPreference(
            DataStorePrefrenceImpl.ImageUrl_KEY,
            userData.imageUrl.toString()
        )

        dataStorePrefrenceImpl.putPreference(
            DataStorePrefrenceImpl.Bio_KEY,
            userData.bio
        )

        dataStorePrefrenceImpl.putPreference(
            DataStorePrefrenceImpl.userId_KEY,
            userData.userId
        )
        dataStorePrefrenceImpl.putPreference(
            DataStorePrefrenceImpl.Name_KEY,
            userData.fullName
        )
        dataStorePrefrenceImpl.putPreference(
            DataStorePrefrenceImpl.Email_KEY,
            userData.email
        )


    }

    private fun saveLocal(userData: UserData){
        Constants.Name=userData.fullName
        Constants.Bio=userData.bio
        Constants.Email=userData.email
       Constants.imageUrl= userData. imageUrl
    }


}