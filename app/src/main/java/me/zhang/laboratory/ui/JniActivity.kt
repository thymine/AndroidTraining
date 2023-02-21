package me.zhang.laboratory.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import me.zhang.laboratory.R

class JniActivity : BaseActivity() {

    init {
        System.loadLibrary("hello-jni")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni)

        findViewById<Button>(R.id.btn_call_jni).setOnClickListener {
            Toast.makeText(it.context, callFromJni(), Toast.LENGTH_SHORT).show()
        }
    }

    private external fun callFromJni(): String
}