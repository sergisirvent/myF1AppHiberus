package com.sergisirvent.myf1app.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sergisirvent.myf1app.R
import com.sergisirvent.myf1app.databinding.FragmentCircuitsBinding
import com.sergisirvent.myf1app.model.ResourceState
import com.sergisirvent.myf1app.presentation.adapter.CircuitsListAdapter
import com.sergisirvent.myf1app.presentation.viewmodel.CircuitsListState
import com.sergisirvent.myf1app.presentation.viewmodel.CircuitsViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CircuitsFragment : Fragment() {

    private val binding: FragmentCircuitsBinding by lazy {
        FragmentCircuitsBinding.inflate(layoutInflater)
    }

    private val circuitsViewModel: CircuitsViewModel by activityViewModel()
    private val circuitsListAdapter = CircuitsListAdapter()

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

        circuitsViewModel.fetchCircuits(getString(R.string.season_actual_year).toInt())

    }

    private fun initViewModel() {
        circuitsViewModel.getCircuitLiveData().observe(viewLifecycleOwner) {state ->
            handleCircuitListState(state)
        }
    }


    private fun handleCircuitListState(state: CircuitsListState) {
        when (state) {
            is ResourceState.Loading -> {
                binding.pbCircuitsList.visibility = View.VISIBLE
            }

            is ResourceState.Success -> {
                binding.pbCircuitsList.visibility = View.GONE
                circuitsListAdapter.submitList(state.result)
            }

            is ResourceState.Error -> {
                binding.pbCircuitsList.visibility = View.GONE
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
                circuitsViewModel.fetchCircuits(getString(R.string.season_actual_year).toInt())
            }
            .show()
    }

    private fun initUI() {
        binding.rvCircuitsList.adapter = circuitsListAdapter
        binding.rvCircuitsList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
        val layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
        binding.rvCircuitsList.addItemDecoration(
            DividerItemDecoration(
                binding.rvCircuitsList.context,
                layoutManager.orientation
            )
        )

        circuitsListAdapter.onClickListener = {circuit ->
            findNavController().navigate(
                GeneralTabFragmentDirections.actionCircuitsListFragmentToCircuitsDetailFragment(
                    circuit.circuitId
                )
            )
        }

    }

}