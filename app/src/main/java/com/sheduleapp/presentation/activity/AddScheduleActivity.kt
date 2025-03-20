package com.sheduleapp.presentation.activity

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.sheduleapp.R
import com.sheduleapp.databinding.ActivityAddScheduleBinding
import com.sheduleapp.domain.model.Schedule
import com.sheduleapp.presentation.viewmodel.ScheduleViewModel
import com.sheduleapp.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddScheduleBinding
    private val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    private val scheduleViewModel by viewModels<ScheduleViewModel>()
    private var thisDay:String = days[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddScheduleBinding.inflate(layoutInflater)
        supportActionBar?.title = "Add Schedule"
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.daySelect.adapter = spinnerAdapter

        binding.daySelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedDay = days[position]
                thisDay = selectedDay
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.timeInput.setOnClickListener {
            showTimePickerDialog()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.add_schedule -> {
                val title = binding.scheduleTitleInput.text.toString().trim()
                val desc = binding.scheduleDescInput.text.toString().trim()
                val time = binding.timeInput.text.toString().trim()

                if (title.isEmpty()) {
                    binding.scheduleTitleInput.error = "Judul tidak boleh kosong"
                    return true
                }

                if (desc.isEmpty()) {
                    binding.scheduleDescInput.error = "Deskripsi tidak boleh kosong"
                    return true
                }

                if (time.isEmpty()) {
                    binding.timeInput.error = "Waktu tidak boleh kosong"
                    return true
                }

                scheduleViewModel.addSchedule(
                    Schedule(
                        title = title,
                        desc = desc,
                        day = thisDay,
                        time = time
                    )
                )
                startActivity(Intent(this, HomeActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showTimePickerDialog() {
        val timeParts = Helper.getCurrentTime().split(":")
        val hour = timeParts[0].toInt()
        val minute = timeParts[1].toInt()

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                binding.timeInput.text = Editable.Factory.getInstance().newEditable(String.format("%02d:%02d", selectedHour, selectedMinute))
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }
}