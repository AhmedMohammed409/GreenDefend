package com.example.greendefend.ui.boarding

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.greendefend.R
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.data.repository.DataStorePrefrenceImpl.Companion.onboardingOpenedState_Key
import com.example.greendefend.databinding.ActivityOnboardingBinding
import com.example.greendefend.domin.model.OnboardingModel
import com.example.greendefend.ui.authentication.AuthenticationActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOnboardingBinding

@Inject lateinit var dataStoreRepositoryImpl: DataStorePrefrenceImpl

private val listInfoFragment by lazy {
    mutableListOf(
        OnboardingModel(
            getString(R.string.title_onboarding1),
            getString(R.string.disception_onboarding1),
            R.drawable.background_onboarding1),
        OnboardingModel(
            getString(R.string.title_onboarding2),
            getString(R.string.disception_onboarding2),
            R.drawable.background_onboarding2),
        OnboardingModel(
            getString(R.string.title_onboarding3),
            getString(R.string.disception_onboarding3),
            R.drawable.background_onboarding3
        )
    )
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        
        //viewpager2 &adapter
        binding.viewPagerFrgment.adapter = ViewPagerAdapter(this, listInfoFragment)
        TabLayoutMediator(binding.tablayout, binding.viewPagerFrgment) { _, _ -> }.attach()


        binding.btnNext.setOnClickListener {
            if (binding.viewPagerFrgment.currentItem > binding.viewPagerFrgment.childCount) {
                lifecycleScope.launch{
                    dataStoreRepositoryImpl.putPreference(onboardingOpenedState_Key,true)
                }
                val intent = Intent(this, AuthenticationActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.viewPagerFrgment.setCurrentItem(
                    binding.viewPagerFrgment.currentItem + 1,
                    true
                )
            }
        }

        //lisener viewpager
        binding.viewPagerFrgment.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (binding.viewPagerFrgment.currentItem==2){
                    binding.btnNext.setText(R.string.finished)
                }
                else{
                    binding.btnNext.setText(R.string.next)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_drawer, menu)
        return true

    }

}




