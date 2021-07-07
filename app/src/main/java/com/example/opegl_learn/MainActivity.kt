package com.example.opegl_learn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.opegl_learn.activity.OpenGL20Activity
import com.example.opegl_learn.activity.Triangle2Activity

class MainActivity : AppCompatActivity() , View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bind()
    }
    private fun bind(){
        val blackBT = findViewById<Button>(R.id.black_bt)
        val twoBT = findViewById<Button>(R.id.triangle2)
        blackBT.setOnClickListener(this)
        twoBT.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.black_bt -> {
                Log.d("TAG", "onClick: 11")
                val intent = Intent(this, OpenGL20Activity::class.java)
                startActivity(intent)

            }
            R.id.triangle2 -> {
                val intent = Intent(this, Triangle2Activity::class.java)
                startActivity(intent)
            }

        }
    }
}