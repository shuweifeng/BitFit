package com.example.bitfit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EnterActivity : AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)

        button = findViewById(R.id.addButton)
        button.setOnClickListener {

            val foodName = findViewById<EditText>(R.id.itemNameEditText).text.toString()
            val calories = findViewById<EditText>(R.id.caloriesEditText).text.toString()
            //val newItem = Food(itemName, calories)
            val newFood = Food(foodName, calories.toInt())

            val newFoodEntity = FoodEntity(newFood.itemName, newFood.calories)

            lifecycleScope.launch(Dispatchers.IO) {
                (application as FoodApplication).db.itemDao().insert(newFoodEntity)
            }

            finish()
        }

    }

}