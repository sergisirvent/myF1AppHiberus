package com.sergisirvent.myf1app.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.sergisirvent.myf1app.R
import com.sergisirvent.myf1app.databinding.FragmentGeneralTabBinding

class GeneralTabFragment : Fragment() {

    private var _binding: FragmentGeneralTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeneralTabBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        val tabLayout = binding.tlGeneralTabsFragment
        val viewPager2 = binding.vp2GeneralTabFragment

        viewPager2.adapter = GeneralTabViewPagerAdapter(this)

        TabLayoutMediator(tabLayout,viewPager2) {tab,position ->
            tab.text = when (position) {
                0 -> getString(R.string.drivers_tab_layout_name)
                1 -> getString(R.string.circuit_tab_layout_name)
                else -> getString(R.string.invalid_error_tab_layout)
            }
        }.attach()
    }

    private inner class GeneralTabViewPagerAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount() = getString(R.string.number_of_tabs_created_general_tab_layout).toInt()
        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> DriversFragment()
                1 -> CircuitsFragment()
                else -> throw IllegalArgumentException(getString(R.string.invalid_error_tab_layout))
            }
        }
    }


}