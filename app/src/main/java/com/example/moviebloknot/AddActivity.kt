package com.example.moviebloknot

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.moviebloknot.databinding.ActivityAddBinding
import com.example.moviebloknot.models.MyMovie
import java.util.Calendar

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var list = MySharedPreferences.catchList
        val name = intent.getStringExtra("name")
        val author = intent.getStringExtra("author")
        val about = intent.getStringExtra("about")
        val date = intent.getStringExtra("data")
        val position = intent.getStringExtra("position")

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        binding.edtDate.setOnClickListener{
            val dp = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { datePicker, mYear, mMonth, mDay ->
                binding.edtDate.setText(""+mDay+"/"+mMonth+"/"+mYear)
            },year,month,day)
            dp.show()
        }
        binding.apply {
            edtName.setText(name)
            edtAuthor.setText(author)
            edtAbout.setText(about)
            edtDate.setText(date)
        }
        binding.apply {
            btnSave.setOnClickListener {
                val myMovie = MyMovie(edtName.text.toString().trim(), edtAuthor.text.toString().trim(), edtAbout.text.toString().trim(), edtDate.text.toString().trim())
                if (myMovie.name.isNotEmpty() && myMovie.about.isNotEmpty() && myMovie.date.isNotEmpty() && myMovie.author.isNotEmpty()) {
                    list = MySharedPreferences.catchList
                    list.add(myMovie)
                    Toast.makeText(this@AddActivity, "${binding.edtName.text} Saqlandi", Toast.LENGTH_SHORT).show()
                    MySharedPreferences.catchList = list
                    finish()
                } else {
                    Toast.makeText(this@AddActivity, "Malumotlarni to'ldiring", Toast.LENGTH_SHORT).show()
                }
                if (list.contains(MyMovie(edtName.text.toString().trim(), edtAuthor.text.toString().trim(),edtAbout.text.toString().trim(), edtDate.text.toString().trim())))
                    if (position != null) {
                        list.removeAt(position.toInt())
                        MySharedPreferences.catchList = list }
            }
        }
    }
    }