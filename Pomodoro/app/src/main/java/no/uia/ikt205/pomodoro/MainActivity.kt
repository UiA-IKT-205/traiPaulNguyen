package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView
    lateinit var thirtybtn:Button
    lateinit var sixtybtn:Button
    lateinit var ninetybtn:Button
    lateinit var hundredtwentybtn: Button
    private var timerOn:Boolean = false


    private var timeToCountDownInMs = 5000L
    private val timeTicks = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       startButton = findViewById<Button>(R.id.startCountdownButton)
       startButton.setOnClickListener(){
           startCountDown(it)
       }

        // 30 second count
        thirtybtn = findViewById<Button>(R.id.button1)
        thirtybtn.setOnClickListener(){
            // Put countdown to 1800000 ms
            timeToCountDownInMs = 1800000L
            // Timer on check
            if (timerOn){
                Toast.makeText(this@MainActivity,"Nedtelling pågår", Toast.LENGTH_SHORT).show()
            }
            // Update time in display
            else
                updateCountDownDisplay(timeToCountDownInMs)
        }

        // 60 second count
        sixtybtn = findViewById<Button>(R.id.button2)
        sixtybtn.setOnClickListener(){
            timeToCountDownInMs = 3600000L
            if (timerOn){
                Toast.makeText(this@MainActivity,"Nedtelling pågår", Toast.LENGTH_SHORT).show()
            } else
                updateCountDownDisplay(timeToCountDownInMs)
        }

        // 90 second count
        ninetybtn = findViewById<Button>(R.id.button3)
        ninetybtn.setOnClickListener(){
            timeToCountDownInMs = 5400000L
            if (timerOn){
                Toast.makeText(this@MainActivity,"Nedtelling pågår", Toast.LENGTH_SHORT).show()
            } else
                updateCountDownDisplay(timeToCountDownInMs)
        }

        // 120 second count
        hundredtwentybtn = findViewById<Button>(R.id.button4)
        hundredtwentybtn.setOnClickListener(){
            timeToCountDownInMs = 7200000
            if (timerOn){
                Toast.makeText(this@MainActivity,"Nedtelling pågår", Toast.LENGTH_SHORT).show()
            } else
                updateCountDownDisplay(timeToCountDownInMs)
        }

       coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }

    fun startCountDown(v: View){

        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                timerOn = false
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
        timerOn = true
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}