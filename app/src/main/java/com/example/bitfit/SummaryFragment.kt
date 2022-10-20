package com.example.bitfit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.w3c.dom.Text


class SummaryFragment : Fragment(){
    lateinit var averageCaloriesText: TextView
    lateinit var minimumCaloriesText: TextView
    lateinit var maximumCaloriesText: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        // val view = inflater.inflate(R.layout.fragment_summary, container, false)
        val view = inflater.inflate(R.layout.fragment_summary, container, false)
        super.onViewCreated(view, savedInstanceState)

        averageCaloriesText = view.findViewById<TextView>(R.id.averageCalories)
        minimumCaloriesText = view.findViewById<TextView>(R.id.minimumCalories)
        maximumCaloriesText = view.findViewById<TextView>(R.id.maximumCalories)

        lifecycleScope.launch (Dispatchers.IO) {
            averageCaloriesText.text = (activity?.application as FoodApplication).db.itemDao().getAverage().toString()
            minimumCaloriesText.text = (activity?.application as FoodApplication).db.itemDao().getMinimum().toString()
            maximumCaloriesText.text = (activity?.application as FoodApplication).db.itemDao().getMaximum().toString()
        }

        // Update the return statement to return the inflated view from above
        return view
    }

    override fun onResume(){
        super.onResume()

        var average = 0
        var minimum = 0
        var maximum = 0

        val job = lifecycleScope.launch (Dispatchers.IO) {
            average = (activity?.application as FoodApplication).db.itemDao().getAverage()
            minimum = (activity?.application as FoodApplication).db.itemDao().getMinimum()
            maximum = (activity?.application as FoodApplication).db.itemDao().getMaximum()
        }

        runBlocking {
            job.join()
        }

        averageCaloriesText.text = average.toString()
        minimumCaloriesText.text = minimum.toString()
        maximumCaloriesText.text = maximum.toString()
    }


    companion object {
        fun newInstance(): SummaryFragment {
            return SummaryFragment()
        }
    }
}