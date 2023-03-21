package com.example.moviebloknot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviebloknot.databinding.ActivityInfoBinding
import com.example.moviebloknot.models.MyMovie

class Info : AppCompatActivity() {
    lateinit var binding: ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val a = intent.getSerializableExtra("shu") as MyMovie
        binding.name.text = a.name
        binding.about.text = a.about
        binding.author.text = a.author
        binding.date.text = a.date
    }
}