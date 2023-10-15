package com.sergisirvent.myf1app.presentation.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import java.lang.reflect.Array.getInt
import java.time.LocalDate
import java.time.format.DateTimeFormatter


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

        val preferences = requireContext().getSharedPreferences(
            ContextCompat.getString(
                requireContext(),
                R.string.DRIVER_PREFERENCES_NAME,
            ), Context.MODE_PRIVATE)

        val colorKey = "${R.string.DRIVER_PREFERENCES_KEY_PREFIX_COLOR}" + f1Driver.driverId
        val driverColorFromPreferences = preferences.getString(colorKey,"#ffffff")

        val fontKey = "${R.string.DRIVER_PREFERENCES_KEY_PREFIX_FONT}" + f1Driver.driverId
        val driverFontFromPreferences = preferences.getString(fontKey,null)

        binding.tvDetailDriverBigNumber.text = f1Driver.permanentNumber
        binding.tvDetailDriverBigNumber.setTextColor(Color.parseColor(driverColorFromPreferences))
        if (driverFontFromPreferences != null) {
            binding.tvDetailDriverBigNumber.typeface = resources.getFont(driverFontFromPreferences.toInt())
            binding.tvDetailDriverFamilyName.typeface = resources.getFont(driverFontFromPreferences.toInt())
        }

        binding.tvDetailDriverGivenName.text = f1Driver.givenName
        binding.tvDetailDriverFamilyName.text = f1Driver.familyName.uppercase()
        binding.tvDetailDriverFamilyName.setTextColor(Color.parseColor(driverColorFromPreferences))


        binding.cvHeaderDetail.strokeColor = Color.parseColor(driverColorFromPreferences)

        val date = LocalDate.parse(f1Driver.dateOfBirth)
        val formatter = DateTimeFormatter.ofPattern("dd - MMMM - yyyy")
        binding.tvDriverDetailDateBirth.text = date.format(formatter)

        binding.tvDriverDetailNationality.text = f1Driver.nationality



        binding.btnDriverDetailMoreInfo.setOnClickListener {
            
            try{
                val url = f1Driver.url
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }catch (e : Exception){
                Toast.makeText(requireContext(),e.localizedMessage,Toast.LENGTH_LONG).show()
            }

        }

        binding.ivDetailDriverBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }




    }

    private fun showErrorDialog(error: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error_detail)
            .setMessage(error)
            .setPositiveButton(R.string.action_ok, null)
            .show()
    }


}