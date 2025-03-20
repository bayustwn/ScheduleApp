package com.sheduleapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.sheduleapp.R
import com.sheduleapp.databinding.ActivityHomeBinding
import com.sheduleapp.presentation.state.ScheduleUIState
import com.sheduleapp.presentation.viewmodel.ScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val scheduleViewModel by viewModels<ScheduleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Home"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            scheduleViewModel.nearestSchedule.collectLatest { state->
                when(state){
                    is ScheduleUIState.Empty -> {
                        binding.apply {
                            Log.d("TASK",state.toString())
                            this.loading.visibility = View.GONE
                            this.scheduleTitle.visibility = View.INVISIBLE
                            this.dateTime.visibility = View.INVISIBLE
                            this.textIndicator.visibility = View.VISIBLE
                            this.textIndicator.text = getString(R.string.no_task_today)
                        }
                    }
                    is ScheduleUIState.Error -> {
                        binding.apply {
                            this.loading.visibility = View.GONE
                            this.scheduleTitle.visibility = View.INVISIBLE
                            this.dateTime.visibility = View.INVISIBLE
                            this.textIndicator.visibility = View.VISIBLE
                            this.textIndicator.text = getString(R.string.error_getting_task)
                        }
                    }
                    is ScheduleUIState.Loading -> {
                        binding.apply {
                            this.scheduleTitle.visibility = View.INVISIBLE
                            this.dateTime.visibility = View.INVISIBLE
                            this.loading.visibility = View.VISIBLE
                            this.textIndicator.visibility = View.GONE
                        }
                    }
                    is ScheduleUIState.Success -> {
                        val nearest = state.schedules[0]
                        binding.apply {
                            this.scheduleTitle.visibility = View.VISIBLE
                            this.dateTime.visibility = View.VISIBLE
                            this.textIndicator.visibility = View.GONE
                            this.loading.visibility = View.GONE
                            this.scheduleTitle.text = nearest.title
                            this.dateTime.text = "${nearest.day}, ${nearest.time}"
                            this.content.setOnClickListener {
                                startActivity(Intent(this@HomeActivity, DetailScheduleActivity::class.java)
                                    .putExtra(DetailScheduleActivity.SCHEDULE,state.schedules[0])
                                )
                            }
                        }
                    }
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.add_schedule -> {
                startActivity(Intent(this@HomeActivity, AddScheduleActivity::class.java))
                true
            }
            R.id.list_schedule->{
                startActivity(Intent(this@HomeActivity, ListScheduleActivity::class.java))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}