package com.example.bitfit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Original statement
//class FoodAdapter(private val foods: MutableList<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
class FoodAdapter(private val context: Context, private val foods: MutableList<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access

    private lateinit var longListener: OnItemLongClickListener
    // Define the long listener interface
    interface OnItemLongClickListener {
        fun onItemLongClick(itemView: View?, position: Int)
    }
    // Define the method that allows the parent activity or fragment to define the long listener
    fun setOnItemLongClickListener(longListener: OnItemLongClickListener) {
        this.longListener = longListener
    }


    class ViewHolder(itemView: View, longListener: OnItemLongClickListener) : RecyclerView.ViewHolder(itemView) {

        // Your holder should contain a member variable for any view that will be set as you render a row
        val foodNameTextView: TextView
        val caloriesTextView: TextView

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each sub-view
        init {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            foodNameTextView = itemView.findViewById(R.id.foodName)
            caloriesTextView = itemView.findViewById(R.id.calories)

            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    longListener.onItemLongClick(itemView, position)
                }
                return@setOnLongClickListener true
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_food, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView, longListener)
    }

    // Populate data into the item through the holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val item = foods.get(position)
        // Set item views based on views and data model
        holder.foodNameTextView.text = item.itemName
        holder.caloriesTextView.text = item.calories.toString()
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    fun deleteItem(position: Int) {
        foods.removeAt(position)
    }
}

