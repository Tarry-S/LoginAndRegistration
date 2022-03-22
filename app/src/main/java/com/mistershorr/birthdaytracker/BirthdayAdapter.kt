package com.mistershorr.birthdaytracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class BirthdayAdapter(var birthdayList: List<Person>) : RecyclerView.Adapter<BirthdayAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textViewName : TextView
        val textViewDaysUntil: TextView
        val layout : ConstraintLayout
        val checkBoxGiftBought : CheckBox

        init {
            textViewName = view.findViewById(R.id.textView_birthdayItem_name)
            textViewDaysUntil = view.findViewById(R.id.textView_birthdayItem_daysUntil)
            layout = view.findViewById(R.id.layout_birthdayItem)
            checkBoxGiftBought = view.findViewById(R.id.checkBox_birthdayItem_giftBought)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_birthday, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.layout.context
        val person = birthdayList[position]
        holder.textViewName.text = person.name
        //calculate how many days until the persons next birthday
        val calenderBday = Calendar.getInstance()
        calenderBday.time = person.birthday
        val calendarToday = Calendar.getInstance()
        val bdayMonth = calenderBday.get(Calendar.MONTH)
        val bdayDay = calenderBday.get(Calendar.DAY_OF_MONTH)
        val todayMonth = calendarToday.get(Calendar.MONTH)
        val todayDay = calendarToday.get(Calendar.DAY_OF_MONTH)
        val todayYear = calendarToday.get(Calendar.YEAR)

        //if month/day are after current date, calenderBday current year
        if(bdayMonth > todayMonth || bdayMonth == todayMonth && bdayDay > todayDay){
            calenderBday.set(Calendar.YEAR, todayYear)
        }
        //if same, display TODAY
        else if (bdayMonth == todayMonth && bdayDay == todayDay){
            calenderBday.set(Calendar.YEAR, todayYear)
        }
        //if month/day before current date, callenderBday next year
        else{
            calenderBday.set(Calendar.YEAR, todayYear + 1)
        }

        var difference = calenderBday.timeInMillis - calendarToday.timeInMillis
        var daysDiff = difference / (1000 * 60 * 60 * 24)
        holder.textViewDaysUntil.text = "${daysDiff} left"
        holder.checkBoxGiftBought.isChecked = person.giftPurchased
    }

    override fun getItemCount(): Int {
        return birthdayList.size
    }
}