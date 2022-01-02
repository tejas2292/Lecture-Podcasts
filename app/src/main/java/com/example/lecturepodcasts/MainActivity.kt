package com.example.lecturepodcasts

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()
    private var pause: Boolean = false
    private lateinit var seek_bar: SeekBar
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView
    private lateinit var songName: TextView
    private lateinit var songBy: TextView
    private lateinit var pauseBtn: ImageButton
    private lateinit var nextBtn: ImageButton
    private lateinit var backBtn: ImageButton
    private lateinit var forward10: ImageButton
    private lateinit var replay10: ImageButton
    var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pauseBtn = findViewById(R.id.pauseBtna)
        nextBtn = findViewById(R.id.nextBtn)
        backBtn = findViewById(R.id.backBtn)
        seek_bar = findViewById(R.id.seekBarTime)
        songName = findViewById(R.id.songName)
        songBy = findViewById(R.id.songBy)
        tvStartTime = findViewById(R.id.tvStartTime)
        tvEndTime = findViewById(R.id.tvEndTime)
        forward10 = findViewById(R.id.forward10)
        replay10 = findViewById(R.id.replay10)

        var strUser: String = intent.getStringExtra("position").toString()
        count = strUser.toInt();
        startSong()

        // Pause the media player
        pauseBtn.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                pause = true
                pauseBtn.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24)
            } else {
                mediaPlayer.start()
                pauseBtn.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24)
            }
        }

        nextBtn.setOnClickListener {
            mediaPlayer.stop()
            pauseBtn.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24)
            if (count < 4) {
                count += 1
                startSong()
            } else if (count == 4) {
                count = 0
                startSong()
            }
        }

        backBtn.setOnClickListener {
            mediaPlayer.stop()
            pauseBtn.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24)
            if (count > 0) {
                count -= 1
                startSong()
            } else if (count == 0) {
                count = 4
                startSong()
            }
        }

        forward10.setOnClickListener {
            mediaPlayer.seekTo(mediaPlayer.currentPosition + 10000)
        }

        replay10.setOnClickListener {
            mediaPlayer.seekTo(mediaPlayer.currentPosition - 10000)
        }

        // Stop the media player
        /*  stopBtn.setOnClickListener {
              if (mediaPlayer.isPlaying || pause.equals(true)) {
                  pause = false
                  seek_bar.setProgress(0)
                  mediaPlayer.stop()
                  mediaPlayer.reset()
                  mediaPlayer.release()
                  handler.removeCallbacks(runnable)

                  playBtn.isEnabled = true
                  pauseBtn.isEnabled = false
                  stopBtn.isEnabled = false
                  tv_pass.text = ""
                  tv_due.text = ""
                  Toast.makeText(this, "media stop", Toast.LENGTH_SHORT).show()
              }
          }

         */
        // Seek bar change listener
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    mediaPlayer.seekTo(i * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    private fun startSong() {
        if (count == 0) {
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.maths1)
            pauseBtn.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24)
            mediaPlayer.start()
            songName.setText("Discrete Mathematics Lecture 1")
            songBy.setText("Prof. A.D. Patel")
        } else if (count == 1) {
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.maths2)
            pauseBtn.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24)
            mediaPlayer.start()
            songBy.setText("Discrete Mathematics Lecture 2")
            songName.setText("Prof. A.D. Patel")

        } else if (count == 2) {
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.toc1)
            pauseBtn.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24)
            mediaPlayer.start()
            songName.setText("Theory of Computation Lecture 1")
            songBy.setText("Prof. D.D. Kale")
        } else if (count == 3) {
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.toc2)
            pauseBtn.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24)
            mediaPlayer.start()
            songName.setText("Theory of Computation Lecture 2")
            songBy.setText("Prof. D.D. Kale")
        } else if (count == 4) {
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.toc3)
            pauseBtn.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24)
            mediaPlayer.start()
            songName.setText("Theory of Computation Lecture 3")
            songBy.setText("Prof. D.D. Kale")
        }

        initializeSeekBar()
    }

    // Method to initialize seek bar and audio stats
    private fun initializeSeekBar() {
        seek_bar.max = mediaPlayer.seconds

        runnable = Runnable {
            seek_bar.progress = mediaPlayer.currentSeconds
            val diff = mediaPlayer.currentSeconds
            var elapsed: String? = ""
            var minutes: Int = mediaPlayer.currentPosition / 1000 / 60
            var second: Int = mediaPlayer.currentPosition / 1000 % 60
            elapsed = minutes.toString() + ":"
            if (second < 10) {
                elapsed += "0"
            }
            elapsed += second
            tvStartTime.text = elapsed

            tvEndTime.text = milisectosec(mediaPlayer.duration)


            handler.postDelayed(runnable, 1000)
        }

        handler.postDelayed(runnable, 1000)

        mediaPlayer.setOnCompletionListener(OnCompletionListener {
            mediaPlayer.stop()
            pauseBtn.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24)
            if (count == 0) {
                count = 1
                startSong()
            } else if (count == 1) {
                count = 2
                startSong()
            } else if (count == 2) {
                count = 3
                startSong()
            } else if (count == 3) {
                count = 4
                startSong()
            } else if (count == 4) {
                count = 0
                startSong()
            }
        })
    }

    private fun milisectosec(duration: Int): String? {
        var elapsedTime: String? = ""
        var minutes: Int = duration / 1000 / 60
        var seconds: Int = duration / 1000 % 60
        elapsedTime = minutes.toString() + ":"
        if (seconds < 10) {
            elapsedTime += "0"
        }
        elapsedTime += seconds

        return elapsedTime
    }

    override fun onPause() {
        mediaPlayer.pause()
        pauseBtn.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24)
        super.onPause()
    }

    override fun onResume() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            pause = true
            pauseBtn.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24)
        } else {
            mediaPlayer.start()
            pauseBtn.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24)
        }
        super.onResume()
    }


}


val MediaPlayer.seconds: Int
    get() {
        return this.duration / 1000

    }


// Creating an extension property to get media player current position in seconds
val MediaPlayer.currentSeconds: Int
    get() {
        return this.currentPosition / 1000
    }