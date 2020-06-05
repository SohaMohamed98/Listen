package com.soha.listen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*

class Main2Activity : AppCompatActivity() {

    //Voice
    private val Request_code_speech_input = 100

    //text to speech
    lateinit var mTTS: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        img_wave.animate().scaleXBy(.4f).scaleYBy(.4f).duration=6000
        img_wave2.animate().scaleXBy(-.4f).scaleYBy(-.4f).duration=8000


        //TEXT TO SPEECH
        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                mTTS.language = Locale.UK
            }
        })

        speak()

        btn_talk2.setOnClickListener {

            img_wave.animate().scaleXBy(.4f).scaleYBy(.4f).duration=6000
            img_wave2.animate().scaleXBy(-.4f).scaleYBy(-.4f).duration=8000
            speak()
        }


    }


    //Speak To Text
    private fun speak() {
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi Speak Something")

        try {
            startActivityForResult(mIntent, Request_code_speech_input)

        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    //Text to Speak
    private fun textToSpeak() {

        val toSpeak = textTv.text.toString()
        if (toSpeak == "") {
            Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show()
        } else {

            Toast.makeText(this, toSpeak, Toast.LENGTH_SHORT).show()
            mTTS.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){

            Request_code_speech_input -> {

                if (resultCode == Activity.RESULT_OK && data != null) {

                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    textTv.text = result[0]
                    textToSpeak()
                    
                    if(textTv.text=="Camera" || textTv.text=="camera"){

                        var i =Intent(this, Camera::class.java)
                        startActivity(i)
                    }else{

                        Toast.makeText(this,"Not Camera",Toast.LENGTH_LONG).show()
                        var intent = Intent(this, web_activity::class.java)
                        intent.putExtra("value",textTv.text.toString())
                        startActivity(intent)

                    }
                }
            }
        }
    }



    }

