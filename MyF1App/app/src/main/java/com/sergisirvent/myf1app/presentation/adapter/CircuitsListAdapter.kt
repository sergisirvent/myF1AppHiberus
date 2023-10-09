package com.sergisirvent.myf1app.presentation.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergisirvent.myf1app.R
import com.sergisirvent.myf1app.databinding.RowListCircuitItemBinding
import com.sergisirvent.myf1app.model.Circuit
import com.sergisirvent.myf1app.model.Location

class CircuitsListAdapter : RecyclerView.Adapter<CircuitsListAdapter.CircuitsListViewHolder> (){

    private var defaultCircuit : Circuit = Circuit("Hola", "Montmelo", Location("d","d","d","d"))
    private var circuitsList : List<Circuit> = emptyList()

    var onClickListener: (Circuit) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircuitsListViewHolder {
        val binding = RowListCircuitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CircuitsListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return circuitsList.size
    }

    override fun onBindViewHolder(holder: CircuitsListViewHolder, position: Int) {
        Log.d("Entro", "Entro on bind")
        val item = circuitsList[position]

        val circuitName = item.circuitName

        holder.circuitNameTextView.text = circuitName

        Glide.with(holder.circuitImageview)
            .load(R.drawable.ic_circuit_foreground)//default image
            .into(holder.circuitImageview)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list : List<Circuit>) {
        circuitsList = list
        notifyDataSetChanged()
    }


    inner class CircuitsListViewHolder(binding : RowListCircuitItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val circuitNameTextView = binding.tvCircuitsListRow
        val circuitImageview = binding.ivCircuitsListRow
    }
}