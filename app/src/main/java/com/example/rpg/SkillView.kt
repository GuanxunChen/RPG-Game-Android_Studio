package com.example.rpg

import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

internal fun MainActivity.setupSkillViewListeners() {


    class SkillAdapter(private val skillItems: MutableMap<String, Int>) : RecyclerView.Adapter<SkillAdapter.SkillViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.skill_item, parent, false)
            val textViewUpgradePoints = findViewById<TextView>(R.id.upgrade_points_count)
            val remainingUpgradePoints = ViewModel.PlayerCharacters[ViewModel.charIndex].skillUpgradePoints
            textViewUpgradePoints.text = "$remainingUpgradePoints remaining"
            return SkillViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
            val currentItem = skillItems.entries.toList()[position]
            holder.textViewSkillName.text = currentItem.key
            holder.textViewSkillLevel.text = currentItem.value.toString()

            // Set click listener for the "Upgrade" button
            holder.buttonUpgrade.setOnClickListener {
                val upgradePoints = ViewModel.PlayerCharacters[ViewModel.charIndex].skillUpgradePoints
                if (upgradePoints > 0) {
                    skillItems[currentItem.key] = currentItem.value+1
                    ViewModel.PlayerCharacters[ViewModel.charIndex].skillUpgradePoints--
                    val textViewUpgradePoints = findViewById<TextView>(R.id.upgrade_points_count)
                    val remainingUpgradePoints = ViewModel.PlayerCharacters[ViewModel.charIndex].skillUpgradePoints
                    textViewUpgradePoints.text = "$remainingUpgradePoints remaining"

                    notifyDataSetChanged()
                }
            }
        }

        override fun getItemCount() = skillItems.size

        inner class SkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textViewSkillName: TextView = itemView.findViewById(R.id.text_view_skill_name)
            val textViewSkillLevel: TextView = itemView.findViewById(R.id.text_view_skill_level)
            val buttonUpgrade: Button = itemView.findViewById(R.id.button_use)
        }
    }

    val buttonBack = findViewById<Button>(R.id.button8)
    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

    val skillItems = ViewModel.PlayerCharacters[ViewModel.charIndex].skills

    recyclerView.layoutManager = LinearLayoutManager(this)
    val adapter = SkillAdapter(skillItems)
    recyclerView.adapter = adapter

    buttonBack.setOnClickListener {
        // Handle back button click event
        ViewModel.charIndex = 0
        setContentView(R.layout.storyview)
        setupStoryViewListeners()
    }
}