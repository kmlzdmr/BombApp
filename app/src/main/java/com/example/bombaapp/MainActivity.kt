package com.example.bombaapp

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import com.example.bombaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var countdownTimer: CountDownTimer
    private var timeLeftInMillis: Long = 70000
    private lateinit var geriSayimText: TextView
    private lateinit var deleteButton: Button
    private lateinit var sendButton: Button
    private lateinit var numberButtons: List<Button>
    private lateinit var kodTextView: TextView
    private lateinit var sonucView: TextView
    private var bombTick: MediaPlayer? = null
    private var bombExplosion: MediaPlayer? = null
    private var bombDefuse: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val incomingTime = intent.getIntExtra("Time",70000)
        val incomingCode = intent.getStringExtra("Code")
        timeLeftInMillis = incomingTime.toLong()

        bombTick = MediaPlayer.create(this, R.raw.bombtimer1sec)
        bombDefuse = MediaPlayer.create(this,R.raw.bombdefuse)
        bombExplosion = MediaPlayer.create(this,R.raw.bombexplosion)

        geriSayimText = findViewById(R.id.geriSayimTextView)
        deleteButton = findViewById(R.id.deleteButton)
        sendButton = findViewById(R.id.sendButton)
        kodTextView = findViewById(R.id.kodTextView)
        sonucView = findViewById(R.id.sonucView)


        numberButtons = listOf(
            findViewById(R.id.button0),
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9)
        )


        deleteButton.setOnClickListener {//Silme Butonu
            var text = kodTextView.text.toString()
            if(text.length > 0)
            {
                var newText = text.substring(0,text.length -1)
                kodTextView.text = newText
            }


        }

        sendButton.setOnClickListener {// Gönder butonu işlemleri
            var girilenKod = kodTextView.text.toString()
            if(incomingCode == girilenKod)
            {
                countdownTimer?.cancel()
                geriSayimText.setTextColor(Color.BLUE)
                sonucView.text = "IMHA EDILDI"
                sonucView.setTextColor(Color.BLUE)
                bombDefuse!!.start()
                bombTick!!.stop()
            }
        }

        for (button in numberButtons) {
            button.setOnClickListener {
                // Her rakam butonuna tıklandığında yapılacak işlemler

                val clickedButton = it as Button  // it, burada tıklanan butonu temsil eder
                val enteredDigit = clickedButton.text.toString()
                var text = kodTextView.text.toString()
                if(text.length < 6)
                {
                    text += enteredDigit
                    kodTextView.text = text
                }

            }
        }

        binding.settingsButton.setOnClickListener{ //Settings Butonu İşlemi
            val yeniIntent = Intent(this@MainActivity,settingsActivity::class.java)
            startActivity(yeniIntent)
        }



        startCountdown() // Sayacı başlatır
    }


    private fun startCountdown() {
        countdownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountdownText()
                bombTick!!.start()
            }

            override fun onFinish() {
                // Geri sayım tamamlandığında yapılacak işlemler
                bombTick!!.stop()
            }
        }.start()
    }

    private fun updateCountdownText() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60

        val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
        if(timeLeftFormatted == "00:00")
        {
            geriSayimText.setTextColor(Color.RED)
            geriSayimText.text = timeLeftFormatted
            sonucView.text = "BOMBA PATLADI"
            sonucView.setTextColor(Color.RED)
            bombExplosion!!.start()
        }
        else{
            geriSayimText.text = timeLeftFormatted
        }
    }



}