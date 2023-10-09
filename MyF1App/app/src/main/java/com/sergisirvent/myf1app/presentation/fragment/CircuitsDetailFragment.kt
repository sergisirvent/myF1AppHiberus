package com.sergisirvent.myf1app.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sergisirvent.myf1app.R
import com.sergisirvent.myf1app.databinding.FragmentCircuitsDetailBinding
import com.sergisirvent.myf1app.databinding.FragmentDriversBinding
import com.sergisirvent.myf1app.databinding.FragmentDriversDetailBinding
import com.sergisirvent.myf1app.model.Circuit
import com.sergisirvent.myf1app.model.F1Driver
import com.sergisirvent.myf1app.model.ResourceState
import com.sergisirvent.myf1app.presentation.viewmodel.CircuitsDetailState
import com.sergisirvent.myf1app.presentation.viewmodel.CircuitsViewModel
import com.sergisirvent.myf1app.presentation.viewmodel.DriversViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CircuitsDetailFragment : Fragment() {

    private val binding: FragmentCircuitsDetailBinding by lazy {
        FragmentCircuitsDetailBinding.inflate(layoutInflater)
    }

    private val args: CircuitsDetailFragmentArgs by navArgs()

    private val circuitsViewModel: CircuitsViewModel by activityViewModel()
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
        circuitsViewModel.fetchCircuit(args.circuitId)
    }

    private fun initViewModel() {
        circuitsViewModel.getCircuitDetailLiveData().observe(viewLifecycleOwner) {state ->
            handleDriverDetailState(state)
        }
    }

    private fun handleDriverDetailState(state: CircuitsDetailState) {
        when (state) {
            is ResourceState.Loading -> {
                binding.pbCircuitDetail.visibility = View.VISIBLE
            }

            is ResourceState.Success -> {
                binding.pbCircuitDetail.visibility = View.GONE
                initUI(state.result)
            }

            is ResourceState.Error -> {
                binding.pbCircuitDetail.visibility = View.GONE
                showErrorDialog(state.error)
            }
        }
    }

    private fun initUI(circuit: Circuit) {

        binding.tvDetailCircuitCountry.text = circuit.Location.country
        binding.tvDetailCiruitName.text = circuit.circuitName
        binding.tvCircuitDetailLocality.text = circuit.Location.locality


        Glide.with(requireContext())
            .load(R.drawable.ic_circuit_foreground)
            .into(binding.ivDetailCircuitImage)

        binding.btnCircuitDetailMoreInfo.setOnClickListener {
            val url = circuit.url
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        binding.ivDetailCircuitBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnCircuitDetailMaps.setOnClickListener {
            val googleMapsUri = Uri.parse("geo:${circuit.Location.lat.toDouble()},${circuit.Location.long.toDouble()}")
            val mapIntent = Intent(Intent.ACTION_VIEW, googleMapsUri)

            mapIntent.setPackage("com.google.android.apps.maps")// se fuerza el uso de google maps

            if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(mapIntent)
            } else {
                Toast.makeText(requireContext(),getString(R.string.error_google_maps),Toast.LENGTH_LONG).show()
            }
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