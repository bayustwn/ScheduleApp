package com.sheduleapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sheduleapp.R
import com.sheduleapp.databinding.ActivityDetailScheduleBinding
import com.sheduleapp.domain.model.Schedule
import com.sheduleapp.presentation.viewmodel.ScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailScheduleBinding
    private val scheduleViewModel by viewModels<ScheduleViewModel>()
    private lateinit var schedule: Schedule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailScheduleBinding.inflate(layoutInflater)
        supportActionBar?.title=""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        schedule = intent.getParcelableExtra<Schedule>(SCHEDULE)!!

        binding.apply {
            this.scheduleTitle.text = schedule.title
            this.scheduleDate.text = "${schedule.day}, ${schedule.time}"
            this.scheduleDesc.text = schedule.desc
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_schedule -> {
                scheduleViewModel.deleteSchedule(schedule = schedule)
                startActivity(Intent(this@DetailScheduleActivity, ListScheduleActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                startActivity(Intent(this, HomeActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    companion object{
        const val SCHEDULE = "schedule"
    }
}