package com.example.analogueclock.activity

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.example.analogueclock.databinding.ActivityAlarmBinding
import com.example.analogueclock.utility.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : Activity() {
    private lateinit var binding: ActivityAlarmBinding
    var alarmTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        val now = Calendar.getInstance()

        binding.pickertime.hour = now[Calendar.HOUR_OF_DAY]
        binding.pickertime.minute = now[Calendar.MINUTE]

        binding.setalarm.setOnClickListener {
            val current = Calendar.getInstance()
            val cal = Calendar.getInstance()

            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MINUTE, binding.pickertime.minute)
            if (binding.pickertime.hour <= 12) {
                cal.set(Calendar.HOUR, binding.pickertime.hour)
                cal.set(Calendar.AM_PM, Calendar.AM);
            } else {
                cal.set(Calendar.HOUR, binding.pickertime.hour - 12)
                cal.set(Calendar.AM_PM, Calendar.PM);
            }
            cal.set(Calendar.MONTH, binding.datepicker.month);
            cal.set(Calendar.DAY_OF_MONTH, binding.datepicker.dayOfMonth);
            cal.set(Calendar.YEAR, binding.datepicker.year);
            var test = binding.pickertime.hour

            if (cal <= current) {
                Toast.makeText(
                    applicationContext,
                    "Invalid Date/Time", Toast.LENGTH_SHORT
                ).show()
            } else {
                setAlarm(cal)
            }
        }
        binding.cancel.setOnClickListener {
            cancelAlarm()
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "channel_1", "Channel 1", NotificationManager.IMPORTANCE_HIGH
        )
        val manager = getSystemService(
            NotificationManager::class.java
        )
        manager.createNotificationChannel(channel)
    }


    @SuppressLint("SimpleDateFormat")
    private fun setAlarm(targetCal: Calendar) {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a")
        alarmTime = sdf.format(targetCal.time)
        Toast.makeText(this, "Next alarm at $alarmTime", Toast.LENGTH_SHORT).show()
        val intent = Intent(baseContext, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(baseContext, RQS_1, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, targetCal.timeInMillis] = pendingIntent
    }

    private fun cancelAlarm() {
        Toast.makeText(this, "Alarm cancelled", Toast.LENGTH_SHORT).show()
        val intent = Intent(baseContext, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(baseContext, RQS_1, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    companion object {
        const val RQS_1 = 1
    }
}