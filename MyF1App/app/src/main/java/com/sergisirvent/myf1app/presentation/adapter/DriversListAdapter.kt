package com.sergisirvent.myf1app.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergisirvent.myf1app.R
import com.sergisirvent.myf1app.databinding.RowListDriverItemBinding
import com.sergisirvent.myf1app.model.F1Driver

class DriversListAdapter() : RecyclerView.Adapter<DriversListAdapter.DriversListViewHolder>()  {

    private var f1DriversList : List<F1Driver> = emptyList()

    var onClickListener: () -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversListViewHolder {
        val binding = RowListDriverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DriversListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return f1DriversList.size
    }

    override fun onBindViewHolder(holder: DriversListViewHolder, position: Int) {
        val item = f1DriversList[position]

        holder.rootview.setOnClickListener {
            onClickListener.invoke()
        }

        val driverName = "${item.givenName} ${item.familyName}"

        holder.driverNameTextView.text = driverName

        Glide.with(holder.driverImageview)
            .load(R.drawable.helmet_default)//default image
            .into(holder.driverImageview)


    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list : List<F1Driver>) {
        f1DriversList = list
        notifyDataSetChanged()
    }


    inner class DriversListViewHolder(binding : RowListDriverItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val rootview = binding.root
        val driverNameTextView = binding.tvDriversListRow
        val driverImageview = binding.ivDriversListRow
    }
}