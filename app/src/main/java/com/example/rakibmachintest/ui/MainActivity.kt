package com.example.rakibmachintest.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.rakibmachintest.R
import com.example.rakibmachintest.databinding.ActivityMainBinding
import com.example.rakibmachintest.model.FormModelItem
import com.example.rakibmachintest.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var dynamicTextView : TextView
    private lateinit var dynamicEditTextFirstName : EditText
    private lateinit var dynamicEditTextLastName : EditText
    private lateinit var dynamicMultiLineEditText : EditText
    private lateinit var radioGroup : RadioGroup
    private lateinit var dynamicButton : Button

    private var holdGender : String? = null
    private var holdExprience : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getAllForm()


        mainViewModel.formLiveData.observe(this, Observer {

            when (it) {
                is NetworkResult.Success -> {


                    it.data?.forEach {

                        if (it.type.equals("textField")  && it.label.equals("First name")) {

                            createTextView(it)
                            createEditTextFirstName(it)
                        }
                        else if (it.type.equals("textField")  && it.label.equals("Last name"))
                        {
                            createTextView(it)
                            createEditTextLastName(it)
                        }
                        else if (it.type.equals("textArea"))
                        {
                            createTextView(it)
                            createMultiLineEditText(it)
                        }
                        else if (it.type.equals("radio"))
                        {
                            createTextView(it)
                            createRadioButton(it)
                        }


                    }

                    createButton()

                }

                is NetworkResult.Error -> {
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {}
            }

        })

    }


    private fun createTextView(it: FormModelItem) {

        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(30, 30, 30, 10)

        dynamicTextView = TextView(this)
        dynamicTextView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dynamicTextView.textSize = 16f
        dynamicTextView.text = it.label

        binding.linear.addView(dynamicTextView, layoutParams)
    }

    private fun createEditTextFirstName(it: FormModelItem) {

        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(30, 10, 30, 10)

        dynamicEditTextFirstName = EditText(this)
        dynamicEditTextFirstName.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dynamicEditTextFirstName.hint = it.label
        dynamicEditTextFirstName.setPadding(20,30,10,30)
        dynamicEditTextFirstName.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_bg))

        binding.linear.addView(dynamicEditTextFirstName, layoutParams)
    }

    private fun createEditTextLastName(it: FormModelItem) {

        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(30, 10, 30, 10)

        dynamicEditTextLastName = EditText(this)
        dynamicEditTextLastName.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dynamicEditTextLastName.hint = it.label
        dynamicEditTextLastName.setPadding(20,30,10,30)
        dynamicEditTextLastName.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_bg))

        binding.linear.addView(dynamicEditTextLastName, layoutParams)
    }


    private fun createMultiLineEditText(it: FormModelItem) {

        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(30, 10, 30, 10)

        dynamicMultiLineEditText = EditText(this)
        dynamicMultiLineEditText.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dynamicMultiLineEditText.hint = it.label
        dynamicMultiLineEditText.setPadding(20,30,10,30)
        dynamicMultiLineEditText.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_bg))
        dynamicMultiLineEditText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE)
        dynamicMultiLineEditText.setMinLines(3)
        dynamicMultiLineEditText.gravity = Gravity.TOP.and(Gravity.START)

        binding.linear.addView(dynamicMultiLineEditText, layoutParams)
    }


    private fun createRadioButton(it: FormModelItem) {

        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(30, 10, 30, 10)

        radioGroup = RadioGroup(this)
        radioGroup.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )


        binding.linear.addView(radioGroup, layoutParams)


        it.options.forEach {


            val radioButton = RadioButton(this)
            radioButton.text = it.toString()

            // Set an ID for each RadioButton to differentiate them
            radioButton.id = View.generateViewId()

            // Add the RadioButton to the RadioGroup
            radioGroup.addView(radioButton)

        }




        radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->

            val checkedRadioButton = findViewById<RadioButton>(checkedId)

            if (it.label.equals("Gender")) {

                val selectedOptionGender = checkedRadioButton.text.toString()

                holdGender = selectedOptionGender

                //Toast.makeText(this, selectedOption, Toast.LENGTH_SHORT).show()


            }
            else
            {
                val selectedOption = checkedRadioButton.text.toString()

                holdExprience = selectedOption

                //Toast.makeText(this, selectedOption, Toast.LENGTH_SHORT).show()
            }


        }


    }


    private fun createButton() {

        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(30, 10, 30, 10)

        dynamicButton = Button(this)
        dynamicButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dynamicButton.text = "Submit"
        dynamicButton.setTextColor(Color.WHITE);
        dynamicButton.setBackgroundColor(Color.BLUE);
        dynamicButton.setTextSize(16f);


        binding.linear.addView(dynamicButton, layoutParams)

        dynamicButton.setOnClickListener {


            if (dynamicEditTextFirstName.text.toString().isEmpty())
            {
                Toast.makeText(this,"First Name is required", Toast.LENGTH_SHORT).show()
            }

            else if (dynamicEditTextLastName.text.toString().isEmpty())
            {
                Toast.makeText(this,"Last Name is required", Toast.LENGTH_SHORT).show()
            }
            else if (dynamicMultiLineEditText.text.toString().isEmpty())
            {
                Toast.makeText(this,"Bio is required", Toast.LENGTH_SHORT).show()

            }
            else if (holdGender == null)
            {
                Toast.makeText(this,"Gender is required", Toast.LENGTH_SHORT).show()

            }
            else if (holdExprience == null)
            {
                Toast.makeText(this,"Exprience is required", Toast.LENGTH_SHORT).show()

            }
            else
            {
                val intent = Intent(this,DetailsActivity::class.java)
                intent.putExtra("f_name",dynamicEditTextFirstName.text.toString())
                intent.putExtra("l_name",dynamicEditTextLastName.text.toString())
                intent.putExtra("bio",dynamicMultiLineEditText.text.toString())
                intent.putExtra("gender",holdGender)
                intent.putExtra("exprience",holdExprience)

                startActivity(intent)
            }

        }

    }

}