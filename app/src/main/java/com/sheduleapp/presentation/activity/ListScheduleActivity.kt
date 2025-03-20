package com.sheduleapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sheduleapp.R
import com.sheduleapp.databinding.ActivityListScheduleBinding
import com.sheduleapp.domain.model.Schedule
import com.sheduleapp.presentation.adapter.ListScheduleAdapter
import com.sheduleapp.presentation.state.ScheduleUIState
import com.sheduleapp.presentation.viewmodel.ScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListScheduleActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListScheduleBinding
    private val scheduleViewModel by viewModels<ScheduleViewModel>()
    private lateinit var scheduleAdapter: ListScheduleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListScheduleBinding.inflate(layoutInflater)
        supportActionBar?.title = "Schedule"
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

   lifecycleScope.launch {
       scheduleViewModel.scheduleState.collect { state->
            when(state){
                is ScheduleUIState.Empty -> {
                    binding.apply {
                        this.loading.visibility = View.GONE
                        this.textIndicator.visibility = View.VISIBLE
                        this.textIndicator.text=getString(R.string.no_task_today)
                        this.listScheduleRv.visibility = View.GONE
                    }
                }
                is ScheduleUIState.Error ->{
                    binding.apply {
                        this.loading.visibility = View.GONE
                        this.textIndicator.visibility = View.VISIBLE
                        this.textIndicator.text=getString(R.string.error_getting_task)
                        this.listScheduleRv.visibility = View.GONE
                    }
                }
                is ScheduleUIState.Loading -> {
                    binding.apply {
                        this.loading.visibility = View.VISIBLE
                        this.textIndicator.visibility = View.GONE
                        this.listScheduleRv.visibility = View.GONE
                    }
                }
                is ScheduleUIState.Success -> {
                    scheduleAdapter = ListScheduleAdapter(state.schedules)
                    binding.apply {
                        this.loading.visibility = View.GONE
                        this.textIndicator.visibility = View.GONE
                    }
                    binding.listScheduleRv.apply {
                        scheduleAdapter.onItemClicked(object : ListScheduleAdapter.onClick{
                            override fun onItemClick(schedule: Schedule) {
                                startActivity(Intent(this@ListScheduleActivity,DetailScheduleActivity::class.java)
                                    .putExtra(DetailScheduleActivity.SCHEDULE,schedule)
                                )
                            }
                        })
                        this.visibility = View.VISIBLE
                        this.adapter = scheduleAdapter
                        this.layoutManager = LinearLayoutManager(this@ListScheduleActivity)
                    }

                }
            }
       }
   }


    }
}