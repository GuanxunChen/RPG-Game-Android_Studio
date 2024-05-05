package com.example.rpg

import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

internal fun MainActivity.setupSkillViewListeners() {

    class SkillAdapter(
        private val skillItems: MutableMap<String, Skill>,
        private val buttonActive: Button,
        private val buttonPassive: Button,
        private val buttonAll: Button
    ) : RecyclerView.Adapter<SkillAdapter.SkillViewHolder>() {

        private var filteredItems: MutableMap<String, Skill> = skillItems.toMutableMap()
        private var isShowingPassiveSkills: Boolean = false

        init {
            setupButtonListeners()
        }

        private fun setupButtonListeners() {
            buttonActive.setOnClickListener {
                filterSkillsByType(false)
            }

            buttonPassive.setOnClickListener {
                filterSkillsByType(true)
            }

            buttonAll.setOnClickListener {
                filterSkillsByType(null)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.skill_item, parent, false)
            val textViewUpgradePoints = findViewById<TextView>(R.id.upgrade_points_count)
            val remainingUpgradePoints = ViewModel.PlayerCharacters[ViewModel.charIndex].skillUpgradePoints
            textViewUpgradePoints.text = "$remainingUpgradePoints remaining"
            return SkillViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
            val currentItem = filteredItems.entries.toList()[position].value
            holder.textViewSkillName.text = currentItem.name
            holder.textViewSkillLevel.text = "Lvl ${currentItem.level}"
            holder.textViewSkillCost.text = "${currentItem.mpCost}mp"

            // Set click listener for the "Upgrade" button
            holder.buttonUpgrade.setOnClickListener {
                val upgradePoints = ViewModel.PlayerCharacters[ViewModel.charIndex].skillUpgradePoints
                if (upgradePoints > 0) {
                    currentItem.level++
                    ViewModel.PlayerCharacters[ViewModel.charIndex].skillUpgradePoints--
                    val textViewUpgradePoints = findViewById<TextView>(R.id.upgrade_points_count)
                    val remainingUpgradePoints = ViewModel.PlayerCharacters[ViewModel.charIndex].skillUpgradePoints
                    textViewUpgradePoints.text = "$remainingUpgradePoints remaining"
                    notifyDataSetChanged()
                }
            }
        }

        override fun getItemCount(): Int {
            return filteredItems.size
        }

        inner class SkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textViewSkillName: TextView = itemView.findViewById(R.id.text_view_skill_name)
            val textViewSkillLevel: TextView = itemView.findViewById(R.id.text_view_skill_level)
            val textViewSkillCost: TextView = itemView.findViewById(R.id.text_view_skill_cost)
            val buttonUpgrade: Button = itemView.findViewById(R.id.button_upgrade)
            val buttonCast: Button = itemView.findViewById(R.id.button_use)
        }

        fun filterSkillsByType(isPassive: Boolean?) {
            filteredItems = if (isPassive != null) {
                skillItems.filter { it.value.isPassive == isPassive }.toMutableMap()
            } else {
                skillItems.toMutableMap()
            }
            notifyDataSetChanged()
        }
    }

    val buttonBack = findViewById<Button>(R.id.button8)
    val buttonActive = findViewById<Button>(R.id.button3)
    val buttonPassive = findViewById<Button>(R.id.button2)
    val buttonAll = findViewById<Button>(R.id.button4)
    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

    val skillItems = ViewModel.PlayerCharacters[ViewModel.charIndex].skills

    recyclerView.layoutManager = LinearLayoutManager(this)
    val adapter = SkillAdapter(skillItems, buttonActive, buttonPassive, buttonAll)
    recyclerView.adapter = adapter

    buttonBack.setOnClickListener {
        // Handle back button click event
        ViewModel.charIndex = 0
        setContentView(R.layout.storyview)
        setupStoryViewListeners()
    }
}
