package com.sergisirvent.myf1app.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sergisirvent.myf1app.R
import com.sergisirvent.myf1app.databinding.FragmentDriversDetailBinding
import com.sergisirvent.myf1app.model.F1Driver
import com.sergisirvent.myf1app.model.ResourceState
import com.sergisirvent.myf1app.presentation.viewmodel.DriversDetailState
import com.sergisirvent.myf1app.presentation.viewmodel.DriversViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class DriversDetailFragment : Fragment() {

    private val binding: FragmentDriversDetailBinding by lazy {
        FragmentDriversDetailBinding.inflate(layoutInflater)
    }

    private val args:DriversDetailFragmentArgs by navArgs()

    private val driversViewModel: DriversViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        driversViewModel.fetchDriver(args.f1DriverId)
    }

    private fun initViewModel() {
        driversViewModel.getDriverDetailLiveData().observe(viewLifecycleOwner) {state ->
            handleDriverDetailState(state)
        }
    }

    private fun handleDriverDetailState(state: DriversDetailState) {

        when (state) {
            is ResourceState.Loading -> {
                binding.pbDriverDetail.visibility = View.VISIBLE
            }

            is ResourceState.Success -> {
                binding.pbDriverDetail.visibility = View.GONE
                initUI(state.result)
            }

            is ResourceState.Error -> {
                binding.pbDriverDetail.visibility = View.GONE
                showErrorDialog(state.error)
            }
        }


    }

    private fun initUI(f1Driver: F1Driver) {

        val numberText = "nº${f1Driver.permanentNumber}"
        binding.tvDetailDriverNumber.text = numberText
        binding.tvDetailDriverGivenName.text = f1Driver.givenName
        binding.tvDetailDriverFamilyName.text = f1Driver.familyName.uppercase()
        binding.tvDriverDetailDateBirth.text = f1Driver.dateOfBirth
        binding.tvDriverDetailNationality.text = f1Driver.nationality

        Glide.with(requireContext())
            .load(R.drawable.helmet_default)
            .into(binding.ivDetailDriverImage)

        binding.btnDriverDetailMoreInfo.setOnClickListener {
            val url = f1Driver.url
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        binding.ivDetailDriverBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    private fun showErrorDialog(error: String) {
        /*MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error)
            .setMessage(error)
            .setPositiveButton(R.string.action_ok, null)
            .setNegativeButton(R.string.action_retry) { dialog, witch ->
                charactersViewModel.fetchCharacters()
            }
            .show()

         */
    }


}