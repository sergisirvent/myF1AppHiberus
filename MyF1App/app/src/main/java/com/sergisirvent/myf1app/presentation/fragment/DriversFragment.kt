package com.sergisirvent.myf1app.presentation.fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sergisirvent.myf1app.R
import com.sergisirvent.myf1app.databinding.FragmentDriversBinding
import com.sergisirvent.myf1app.model.F1Driver
import com.sergisirvent.myf1app.model.ResourceState
import com.sergisirvent.myf1app.presentation.adapter.DriversListAdapter
import com.sergisirvent.myf1app.presentation.viewmodel.DriversListState
import com.sergisirvent.myf1app.presentation.viewmodel.DriversViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class DriversFragment : Fragment() {

    private val binding: FragmentDriversBinding by lazy {
        FragmentDriversBinding.inflate(layoutInflater)
    }
    private val driversViewModel: DriversViewModel by activityViewModel()
    private val driversListAdapter = DriversListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initUI()

        driversViewModel.fetchDrivers(getString(R.string.season_actual_year).toInt())

    }

    private fun initViewModel() {
        driversViewModel.getDriverLiveData().observe(viewLifecycleOwner) {state ->
            handleDriverListState(state)
        }

    }


    private fun handleDriverListState(state: DriversListState) {
        when (state) {
            is ResourceState.Loading -> {
                binding.pbDriversList.visibility = View.VISIBLE
            }

            is ResourceState.Success -> {
                binding.pbDriversList.visibility = View.GONE
                driversListAdapter.submitList(state.result, requireContext())
            }

            is ResourceState.Error -> {
                binding.pbDriversList.visibility = View.GONE
                showErrorDialog(state.error)
            }
        }
    }



    private fun showErrorDialog(error: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error_list)
            .setMessage(error)
            .setPositiveButton(R.string.action_ok, null)
            .setNegativeButton(R.string.action_retry) { dialog, witch ->
                driversViewModel.fetchDrivers(getString(R.string.season_actual_year).toInt())
            }
            .show()
    }

    private fun initUI() {
        binding.rvDriversList.adapter = driversListAdapter
        binding.rvDriversList.layoutManager = LinearLayoutManager(requireContext())
        val layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
        binding.rvDriversList.addItemDecoration(
            DividerItemDecoration(
                binding.rvDriversList.context,
                layoutManager.orientation
            )
        )

        driversListAdapter.onClickListener = { driver ->
            //TODO cambiar para que lea parametros tambien de color y de font

            findNavController().navigate(
                GeneralTabFragmentDirections.actionDriversListFragmentToDriversDetailFragment(
                    driver.driverId
                )
            )

        }

    }




}