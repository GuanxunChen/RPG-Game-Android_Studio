package com.example.rpg

import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
internal fun MainActivity.setupInventoryViewListeners() {
    class InventoryAdapter(
        private val inventoryItems: MutableMap<Item, Int>,
        private val buttonWeapon: Button,
        private val buttonArmor: Button,
        private val buttonHelmet: Button,
        private val buttonBoot: Button,
        private val buttonUsable: Button,
        private val buttonKeyItem: Button,
        private val buttonAll: Button
    ) : RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

        private var filteredItems: MutableMap<Item, Int> = inventoryItems.toMutableMap()

        init {
            setupButtonListeners()
        }

        private fun setupButtonListeners() {
            buttonWeapon.setOnClickListener {
                filterItemsByType("Weapon")
            }

            buttonArmor.setOnClickListener {
                filterItemsByType("Armor")
            }

            buttonHelmet.setOnClickListener {
                filterItemsByType("Helmet")
            }

            buttonBoot.setOnClickListener {
                filterItemsByType("Boots")
            }

            buttonUsable.setOnClickListener {
                filterItemsByType("Consumable")
            }

            buttonKeyItem.setOnClickListener {
                filterItemsByType("Key")
            }

            buttonAll.setOnClickListener {
                filterItemsByType("") // Display all items
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.inventory_item, parent, false)
            return InventoryViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
            val currentItem = filteredItems.entries.toList()[position]
            holder.textViewItemName.text = currentItem.key.name

            // Set button text based on item type
            if (currentItem.key.type == "Weapon" || currentItem.key.type == "Armor" || currentItem.key.type == "Boots" || currentItem.key.type == "Helmet") {
                if (isEquipped(currentItem.key)) {
                    holder.buttonUse.text = "Unequip"
                } else {
                    holder.buttonUse.text = "Equip"
                }
                holder.textViewItemCount.visibility = View.GONE // Hide count for equip/unequip items
            } else {
                holder.buttonUse.text = "Use"
                holder.textViewItemCount.visibility = View.VISIBLE // Show count for other items
                holder.textViewItemCount.text = currentItem.value.toString()
            }

            holder.buttonUse.setOnClickListener {
                if (currentItem.key.type == "Weapon" || currentItem.key.type == "Armor" || currentItem.key.type == "Boots" || currentItem.key.type == "Helmet") {
                    if (isEquipped(currentItem.key)) {
                        unequipItem(currentItem.key)
                    } else {
                        equipItem(currentItem.key)
                    }
                } else {
                    if (currentItem.value > 0) {
                        val updatedItemCount = currentItem.value - 1
                        inventoryItems[currentItem.key] = updatedItemCount
                        filteredItems[currentItem.key] = updatedItemCount
                        useItem(currentItem.key)
                        notifyDataSetChanged()
                    }

                }
            }
        }

        private fun isEquipped(item: Item): Boolean {
            return item.isEquipped
        }

        private fun equipItem(item: Item) {
            ViewModel.PlayerCharacters[ViewModel.charIndex].atktempbattleOnly += item.attack
            ViewModel.PlayerCharacters[ViewModel.charIndex].matktempbattleOnly += item.mattack
            ViewModel.PlayerCharacters[ViewModel.charIndex].deftempbattleOnly += item.defense
            ViewModel.PlayerCharacters[ViewModel.charIndex].mdeftempbattleOnly += item.mdefense
            ViewModel.PlayerCharacters[ViewModel.charIndex].str += item.strBoost
            ViewModel.PlayerCharacters[ViewModel.charIndex].vit += item.vitBoost
            ViewModel.PlayerCharacters[ViewModel.charIndex].agi += item.agiBoost
            ViewModel.PlayerCharacters[ViewModel.charIndex].dex += item.dexBoost
            ViewModel.PlayerCharacters[ViewModel.charIndex].int += item.intBoost
            ViewModel.PlayerCharacters[ViewModel.charIndex].luc += item.lucBoost

            item.isEquipped = true
            notifyDataSetChanged()
        }

        private fun unequipItem(item: Item) {
            ViewModel.PlayerCharacters[ViewModel.charIndex].atktempbattleOnly -= item.attack
            ViewModel.PlayerCharacters[ViewModel.charIndex].matktempbattleOnly -= item.mattack
            ViewModel.PlayerCharacters[ViewModel.charIndex].deftempbattleOnly -= item.defense
            ViewModel.PlayerCharacters[ViewModel.charIndex].mdeftempbattleOnly -= item.mdefense
            ViewModel.PlayerCharacters[ViewModel.charIndex].str -= item.strBoost
            ViewModel.PlayerCharacters[ViewModel.charIndex].vit -= item.vitBoost
            ViewModel.PlayerCharacters[ViewModel.charIndex].agi -= item.agiBoost
            ViewModel.PlayerCharacters[ViewModel.charIndex].dex -= item.dexBoost
            ViewModel.PlayerCharacters[ViewModel.charIndex].int -= item.intBoost
            ViewModel.PlayerCharacters[ViewModel.charIndex].luc -= item.lucBoost

            item.isEquipped = false
            notifyDataSetChanged()
        }

        private fun useItem(item: Item) {
            ViewModel.PlayerCharacters[ViewModel.charIndex].HPtempbattleOnly += item.heals
            ViewModel.PlayerCharacters[ViewModel.charIndex].MPtempbattleOnly += item.mana
        }



        override fun getItemCount() = filteredItems.size

        inner class InventoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textViewItemName: TextView = itemView.findViewById(R.id.text_view_item_name)
            val textViewItemCount: TextView = itemView.findViewById(R.id.text_view_item_count)
            val buttonUse: Button = itemView.findViewById(R.id.button_use)
        }

        fun filterItemsByType(itemType: String) {
            filteredItems = if (itemType.isNotEmpty()) {
                inventoryItems.filter { it.key.type == itemType }.toMutableMap()
            } else {
                inventoryItems.toMutableMap() // Show all items
            }
            notifyDataSetChanged()
        }
    }


    val buttonBack = findViewById<Button>(R.id.button8)
    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

    val inventoryItems = ViewModel.PlayerCharacters[ViewModel.charIndex].inventoryItems

    recyclerView.layoutManager = LinearLayoutManager(this)
    val adapter = InventoryAdapter(
        inventoryItems,
        findViewById(R.id.button3),
        findViewById(R.id.button2),
        findViewById(R.id.button),
        findViewById(R.id.button6),
        findViewById(R.id.button5),
        findViewById(R.id.button7),
        findViewById(R.id.button4)
    )
    recyclerView.adapter = adapter

    buttonBack.setOnClickListener {
        // Handle back button click event
        ViewModel.charIndex = 0
        setContentView(R.layout.storyview)
        setupStoryViewListeners()
    }
}
