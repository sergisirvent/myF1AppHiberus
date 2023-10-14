package com.sergisirvent.myf1app.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import com.sergisirvent.myf1app.R
import com.sergisirvent.myf1app.databinding.RowListDriverItemBinding
import com.sergisirvent.myf1app.model.F1Driver

class DriversListAdapter() : RecyclerView.Adapter<DriversListAdapter.DriversListViewHolder>()  {

    private var f1DriversList : List<F1Driver> = emptyList()
    private var contextFragment : Context? = null

    private var randomColors = listOf("#07E32C", "#0750E3", "#A70063","#ffc61a","#ff0080","#5c00e6",
        "#40bf40","#cc9966","#cc66cc","#3399ff","#85adad","#4d004d")

    var onClickListener: (F1Driver) -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversListViewHolder {
        val binding = RowListDriverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DriversListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return f1DriversList.size
    }

    override fun onBindViewHolder(holder: DriversListViewHolder, position: Int) {

        if (contextFragment != null){
            val item = f1DriversList[position]
            val driverName = "${item.givenName} ${item.familyName}"
            holder.driverNameTextView.text = driverName
            holder.driverBigNumberTextView.text = item.permanentNumber

            val preferences = contextFragment?.getSharedPreferences(getString(contextFragment!!,R.string.DRIVER_PREFERENCES_NAME,),Context.MODE_PRIVATE)
            if (preferences != null){
                initDriverColor(item, preferences,holder)
                initDriverFont(item, preferences, holder)
            }

            holder.rootview.setOnClickListener {
                onClickListener.invoke(item)
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list : List<F1Driver>, context: Context) {
        f1DriversList = list
        contextFragment = context
        notifyDataSetChanged()
    }


    inner class DriversListViewHolder(binding : RowListDriverItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val rootview = binding.root
        val driverNameTextView = binding.tvDriversListRow
        val driverBigNumberTextView = binding.tvDriverListRowBigNumber
        val bigEmptyCircleDriverRow = binding.ivDriverListRowEmptyCircle
    }

    private fun initDriverColor(item : F1Driver, preferences : SharedPreferences, holder : DriversListViewHolder){
        val editor = preferences.edit()
        val prefixColor = R.string.DRIVER_PREFERENCES_KEY_PREFIX_COLOR.toString()
        val colorKey = "${prefixColor}${item.driverId}"
        val savedPreferenceColor = preferences.getString(colorKey,null)

        if (savedPreferenceColor != null){
            holder.driverBigNumberTextView.setTextColor(Color.parseColor(savedPreferenceColor))
            holder.bigEmptyCircleDriverRow.setColorFilter(Color.parseColor(savedPreferenceColor))
        }else {
            val randomColorIndex = (0..<getString(contextFragment!!,R.string.drivers_colors_available).toInt()).random()
            val randomColor = randomColors[randomColorIndex]
            editor.putString(colorKey,randomColor)
            editor.apply()
            holder.driverBigNumberTextView.setTextColor(Color.parseColor(randomColor))
            holder.bigEmptyCircleDriverRow.setColorFilter(Color.parseColor(randomColor))
        }
    }
    private fun initDriverFont(item : F1Driver, preferences : SharedPreferences, holder : DriversListViewHolder){
        val editor = preferences.edit()
        val prefixFont = R.string.DRIVER_PREFERENCES_KEY_PREFIX_FONT.toString()
        val fontKey = "${prefixFont}${item.driverId}"
        val savedPreferenceFont = preferences.getString(fontKey,null)

        if (savedPreferenceFont != null){
            holder.driverBigNumberTextView.typeface = contextFragment!!.resources.getFont(savedPreferenceFont.toInt())
        }else {
            val randomFontIndex = (0..<getString(contextFragment!!,R.string.fonts_available).toInt()).random()

            val fontfields = R.font::class.java.fields

            val randomInt = fontfields[randomFontIndex].getInt(null)
            editor.putString(fontKey,randomInt.toString())
            editor.apply()
            holder.driverBigNumberTextView.typeface = contextFragment!!.resources.getFont(randomInt)
        }
    }

}