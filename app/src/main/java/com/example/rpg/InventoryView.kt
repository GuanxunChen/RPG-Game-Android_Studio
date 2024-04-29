package com.example.rpg

import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
internal fun MainActivity.setupInventoryViewListeners() {

    class InventoryAdapter(private val inventoryItems: MutableMap<String, Int>) : RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.inventory_item, parent, false)
            return InventoryViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
            val currentItem = inventoryItems.entries.toList()[position]
            holder.textViewItemName.text = currentItem.key
            holder.textViewItemCount.text = currentItem.value.toString()

            // Set click listener for the "Use" button
            holder.buttonUse.setOnClickListener {
                // Decrement the item count when the "Use" button is clicked
                if (currentItem.value > 0) {
                    val updatedItemCount = currentItem.value - 1
                    inventoryItems[currentItem.key] = updatedItemCount
                    ViewModel.PlayerCharacters[ViewModel.charIndex].inventoryItems[currentItem.key] = updatedItemCount
                    notifyDataSetChanged()
                }
            }
        }

        override fun getItemCount() = inventoryItems.size

        inner class InventoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textViewItemName: TextView = itemView.findViewById(R.id.text_view_item_name)
            val textViewItemCount: TextView = itemView.findViewById(R.id.text_view_item_count)
            val buttonUse: Button = itemView.findViewById(R.id.button_use)
        }
    }

    val buttonBack = findViewById<Button>(R.id.button8)
    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

    val inventoryItems = ViewModel.PlayerCharacters[ViewModel.charIndex].inventoryItems

    val filteredInventoryItems = inventoryItems.filterValues { it > 0 }

    recyclerView.layoutManager = LinearLayoutManager(this)
    val adapter = InventoryAdapter(filteredInventoryItems.toMutableMap())
    recyclerView.adapter = adapter

    buttonBack.setOnClickListener {
        // Handle back button click event
        ViewModel.charIndex = 0
        setContentView(R.layout.storyview)
        setupStoryViewListeners()
    }
}
