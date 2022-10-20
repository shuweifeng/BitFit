package com.example.bitfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FoodListFragment : Fragment() {
    // Add these properties
    var foods = mutableListOf <Food>()

    private lateinit var foodsRecyclerView: RecyclerView
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)
        super.onViewCreated(view, savedInstanceState)


        // Add these configurations for the recyclerView and to configure the adapter
        val layoutManager = LinearLayoutManager(context)
        foodsRecyclerView = view.findViewById(R.id.food_recycler_view)
        foodsRecyclerView.layoutManager = layoutManager
        foodsRecyclerView.setHasFixedSize(true)
        foodAdapter = FoodAdapter(view.context, foods)
        foodsRecyclerView.adapter = foodAdapter

        // lifecycleScope.launch (Dispatchers.IO){
        //    (activity?.application as FoodApplication).db.itemDao().deleteAll()}


        lifecycleScope.launch {
            (activity?.application as FoodApplication).db.itemDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    Food(
                        entity.itemName,
                        entity.calories
                    )
                }.also { mappedList ->
                    foods.clear()
                    foods.addAll(mappedList)
                    foodAdapter.notifyDataSetChanged()
                }
            }
        }


        // Set layout manager to position the items
        foodsRecyclerView.layoutManager = LinearLayoutManager(context).also {
            val dividerItemDecoration = DividerItemDecoration(context, it.orientation)
            foodsRecyclerView.addItemDecoration(dividerItemDecoration)
        }


        foodAdapter.setOnItemLongClickListener(object : FoodAdapter.OnItemLongClickListener {
            override fun onItemLongClick(itemView: View?, position: Int) {
                val itemName = foods[position].itemName
                foods.removeAt(position)
                foodAdapter.notifyItemRemoved(position)
                Toast.makeText(context, "$itemName Deleted", Toast.LENGTH_LONG).show()
            }
        })

        // Update the return statement to return the inflated view from above
        return view
    }

    companion object {
        fun newInstance(): FoodListFragment {
            return FoodListFragment()
        }
    }

}