package com.example.musicdance

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayer2: MediaPlayer
    private lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(this, R.raw.audio)
        mediaPlayer2 = MediaPlayer.create(this, R.raw.audio2)
        seekBar = findViewById(R.id.seekBar)
        seekBar.max = mediaPlayer.duration

        val playButton: Button = findViewById(R.id.playButton)
        playButton.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
        }

        val pauseButton: Button = findViewById(R.id.pauseButton)
        pauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }

        val stopButton: Button = findViewById(R.id.stopButton)
        stopButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer.create(this, R.raw.audio)
            }
        }

        val playButton2: Button = findViewById(R.id.next_muscic)
        playButton2.setOnClickListener {
            if (!mediaPlayer2.isPlaying) {
                mediaPlayer2.start()
            }
        }

        mediaPlayer.setOnCompletionListener {
            seekBar.progress = 0
        }

        val thread = Thread {
            while (true) {
                try {
                    Thread.sleep(1000)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                seekBar.progress = mediaPlayer.currentPosition
            }
        }
        thread.start()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}