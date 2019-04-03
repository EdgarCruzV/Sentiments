package com.example.sentimentanalysis
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.annotation.RequiresApi
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.text.*

val negativos : Array<String> = arrayOf(
    "no", "triste", "harto", "estrés", "mal", "feo"
)
val positivos : Array<String> = arrayOf(
    "sí",  "feliz", "contento", "bien", "bonito"
)

class MainActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    var countPos = 0
    var countNeg = 0
    private var tts: TextToSpeech? = null
    private var buttonSpeak: Button? = null
    private var editText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSpeak = this.button_speak
        editText = this.edittext_input

        buttonSpeak!!.isEnabled = false;
        tts = TextToSpeech(this, this)

        buttonSpeak!!.setOnClickListener { speakOut() }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                buttonSpeak!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun speakOut() {
        countNeg = 0
        countPos = 0
        val text = editText!!.text.toString()
        var contains : Boolean = false
        for (i in positivos.indices) {
            contains = text.contains(positivos[i], true)
            if(contains){
                countPos ++
            }else{
                countNeg ++
            }
        }
        val estado = countPos - countNeg
        textView3.setText(estado.toString())
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

}


