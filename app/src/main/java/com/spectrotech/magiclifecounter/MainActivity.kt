package com.spectrotech.magiclifecounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spectrotech.magiclifecounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }











    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}