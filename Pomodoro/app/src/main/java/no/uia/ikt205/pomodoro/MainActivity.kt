package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import no.uia.ikt205.pomodoro.util.*



class MainActivity : AppCompatActivity() {

    lateinit var pauseTimer:CountDownTimer
    lateinit var workTimer:CountDownTimer
    lateinit var startButton:Button

    lateinit var countdownDisplay:TextView
    lateinit var pauseCountdownDisplay:TextView
    lateinit var repetitionInput:EditText

    var pauseTimerOn:Boolean = false
    var workTimerOn:Boolean = false
    lateinit var pauseTimeSeekBar:SeekBar
    lateinit var workTimeSeekBar:SeekBar

    var pauseTimerMs = minutesToMs(15)
    var workTimerMs = minutesToMs(15)
    val timeTicks = 1000L
    var repetitions:Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       startButton = findViewById<Button>(R.id.startCountdownButton)
       startButton.setOnClickListener(){
           startCountDown(it)
       }

       countdownDisplay = findViewById<TextView>(R.id.countDownView)
       pauseCountdownDisplay = findViewById<TextView>(R.id.pauseCountDownView)

        workTimeSeekBar = findViewById(R.id.workSeekBar)
        workTimeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val newTime = minutesToMs(progress.toLong())
                countdownDisplay.text = millisecondsToDescriptiveTime(newTime)
                workTimerMs = newTime
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                print("Not implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                print("Not implemented")
            }
        })

       pauseTimeSeekBar = findViewById(R.id.pauseSeekBar)
       pauseTimeSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val newTime =  minutesToMs(progress.toLong())
                pauseCountdownDisplay.text = millisecondsToDescriptiveTime(newTime)
                pauseTimerMs = newTime
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                print("Not implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                print("Not implemented")
            }
        })

        repetitionInput = findViewById(R.id.repetitionInputEdittext)

    }

    fun startCountDown(v: View){
        if (workTimerOn){
            Toast.makeText(
                    this@MainActivity,
                    "Nedtelling pågår",
                    Toast.LENGTH_SHORT).show()
            return
        }

        workTimer = object : CountDownTimer(workTimerMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(
                        this@MainActivity,
                        "Pause",
                        Toast.LENGTH_SHORT).show()

                workTimerOn = false

                repetitions = repetitionInput.text.toString().toInt()
                if (repetitions > 0){
                    startCountdownForPause(v)
                    repetitions--
                    repetitionInput.setText(repetitions.toString())
                    //countdownStatusText.text = "PAUSED FOR:"
                }

            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }

        }

        workTimer.start()
        workTimerOn = true
    }

    private fun startCountdownForPause(v: View){
        if(pauseTimerOn){
            Toast.makeText(
                    this@MainActivity,
                    "Pausen pågår",
                    Toast.LENGTH_SHORT).show()
            return
        }

        pauseTimer = object : CountDownTimer(pauseTimerMs, timeTicks){
            override fun onFinish() {
                Toast.makeText(
                        this@MainActivity,
                        "Pausen er over",
                        Toast.LENGTH_SHORT).show()
                pauseTimerOn = false

                repetitions = repetitionInput.text.toString().toInt()
                if (repetitions > 0){
                    workTimer.start()
                    //countdownStatusText.text = resources.getText(R.string.countdownStatusText)
                }
                else{
                    workTimer.cancel()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }
        pauseTimer.start()
        pauseTimerOn = true
    }


    fun updateCountDownDisplay(timeInMs:Long){
        countdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}