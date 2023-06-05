package com.example.rakibmachintest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rakibmachintest.R
import com.example.rakibmachintest.databinding.ActivityDetailsBinding
import com.example.rakibmachintest.databinding.ActivityMainBinding

class DetailsActivity : AppCompatActivity() {

    private var _binding: ActivityDetailsBinding? = null
    val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val firstName = intent.extras?.getString("f_name")
        val lastName = intent.extras?.getString("l_name")
        val bio = intent.extras?.getString("bio")
        val gender = intent.extras?.getString("gender")
        val exprience = intent.extras?.getString("exprience")


        binding.firstName.text = firstName
        binding.lastName.text = lastName
        binding.bio.text = bio
        binding.gender.text = gender
        binding.exprience.text = exprience

    }
}