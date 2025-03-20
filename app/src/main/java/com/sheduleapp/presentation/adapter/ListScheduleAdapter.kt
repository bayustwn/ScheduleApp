package com.sheduleapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sheduleapp.databinding.ListScheduleItemBinding
import com.sheduleapp.domain.model.Schedule
import com.sheduleapp.presentation.adapter.ListScheduleAdapter.ViewHolder

class ListScheduleAdapter(private val schedule: List<Schedule>): RecyclerView.Adapter<ViewHolder>()  {

    private lateinit var onItemClick: onClick

    fun onItemClicked(onItemClick: onClick){
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ListScheduleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val data = schedule[position]
       holder.bind(data)
        holder.itemView.setOnClickListener { onItemClick.onItemClick(data)  }
    }

    override fun getItemCount(): Int {
        return schedule.size
    }

    inner class ViewHolder(private val binding: ListScheduleItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Schedule){
            binding.scheduleTitle.text = item.title
            binding.scheduleTime.text = "${item.day}, ${item.time}"
        }

    }

    interface onClick{
        fun onItemClick(schedule: Schedule)
    }

}