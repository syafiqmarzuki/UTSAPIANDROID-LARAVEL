package com.syafiqmarzuki21.msyafiqmarzuki.sepakbola

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.content.Intent
class SpalshActivity : AppCompatActivity() {

    private val mHandler = Handler()
    private val mRunnable = Runnable{ startActivity(
        Intent(this@SpalshActivity, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)
        supportActionBar?.hide()
        mHandler.postDelayed(mRunnable, 2000)
    }
}
