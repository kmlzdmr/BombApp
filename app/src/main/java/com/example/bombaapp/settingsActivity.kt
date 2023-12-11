package com.example.bombaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.bombaapp.databinding.ActivitySettingsBinding

class settingsActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySettingsBinding
    private lateinit var editTimeNumber: TextView
    private lateinit var editDefuseCodeNumber: TextView
    private lateinit var setTimeButton: Button
    private lateinit var defuseCodeButton: Button
    private lateinit var startButton: Button

    var time:Int = 0
    var defuseCode:String = "000000"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTimeNumber = findViewById(R.id.editTimeNumber)
        editDefuseCodeNumber = findViewById(R.id.editDefuseCodeNumber)
        setTimeButton = findViewById(R.id.setTimeButton)
        defuseCodeButton = findViewById(R.id.defuseCodeButton)
        startButton = findViewById(R.id.startButton)


        setTimeButton.setOnClickListener{//girilen zamanı kaydeder

            time = editTimeNumber.text.toString().toInt()

        }

        defuseCodeButton.setOnClickListener{//girilen defusecode kaydeder

            defuseCode = editDefuseCodeNumber.text.toString()
        }


        startButton.setOnClickListener{//Bomba Ayarlarını kaydedip bombayı başlatır

            val intent = Intent(this@settingsActivity,MainActivity::class.java)
            intent.putExtra("Time",time)
            intent.putExtra("Code",defuseCode)
            startActivity(intent)
        }



    }
}