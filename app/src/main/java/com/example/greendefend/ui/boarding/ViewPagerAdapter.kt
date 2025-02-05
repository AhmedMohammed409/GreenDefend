package com.example.greendefend.ui.boarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.greendefend.domin.model.OnboardingModel
import com.example.greendefend.ui.boarding.BoardingFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private var listInfoFragment: List<OnboardingModel>
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return listInfoFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return BoardingFragment.newInstance(listInfoFragment[position])
    }
}
