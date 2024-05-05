package com.example.rpg

// Define your items here
val iron_sword = Item(
    name = "Iron Sword",
    type = "Weapon",
    description = "A standard iron sword commonly used by adventurers.",
    attack = 10,
    strBoost = 5,
    isWeapon = true
)
val wooden_bow = Item(
    name = "Wooden Bow",
    type = "Weapon",
    description = "A simple bow made from wood, effective for ranged attacks.",
    attack = 8,
    isWeapon = true
)
val dagger = Item(
    name = "Dagger",
    type = "Weapon",
    description = "A small, sharp blade suitable for quick and stealthy attacks.",
    attack = 6,
    isWeapon = true
)


//Basic armor
val leather_armor = Item(
    name = "Leather Armor",
    type = "Armor",
    description = "Lightweight armor made from leather, offering basic protection.",
    defense = 5,
    isArmor = true
)
val chainmill_vest = Item(
    name = "Chainmail Vest",
    type = "Armor",
    description = "Armor made from interlocking metal rings, providing good defense.",
    defense = 8,
    isArmor = true
)
val cloth_robe = Item(
    name = "Cloth Robe",
    type = "Armor",
    description = "Simple robe made from cloth, offering minimal protection.",
    defense = 3,
    isArmor = true
)

//Basic Boots
val leather_boots = Item(
    name = "Leather Boots",
    type = "Boots",
    description = "Simple leather boots offering basic protection and mobility.",
    defense = 3,
    agiBoost = 10,
    isBoot = true
)
val steel_greaves = Item(
    name = "Steel Greaves",
    type = "Boots",
    description = "Sturdy steel greaves that provide excellent protection for the feet and legs.",
    defense = 6,
    isBoot = true
)
val cloth_shoes = Item(
    name = "Cloth Shoes",
    type = "Boots",
    description = "Lightweight cloth shoes suitable for agile movements.",
    defense = 2,
    isBoot = true
)

//Basic Helmets
val iron_helmet = Item(
    name = "Iron Helmet",
    type = "Helmet",
    description = "A basic iron helmet that protects the head from incoming attacks.",
    defense = 5,
    isHelmet = true
)
val leather_cap = Item(
    name = "Leather Cap",
    type = "Helmet",
    description = "A simple leather cap that provides minimal protection for the head.",
    defense = 2,
    isHelmet = true
)
val steel_helm = Item(
    name = "Steel Helm",
    type = "Helmet",
    description = "A sturdy steel helmet offering excellent defense for the head.",
    defense = 8,
    isHelmet = true
)

//Magic Weapons
val fire_staff = Item(
    name = "Staff of Fire",
    type = "Weapon",
    description = "A magical staff imbued with the power of fire, capable of unleashing flames upon enemies.",
    attack = 12,
    isWeapon = true
)
val wand = Item(
    name = "Enchanted Wand",
    type = "Weapon",
    description = "A wand infused with magical energy, allowing the caster to cast spells more effectively.",
    attack = 10,
    isWeapon = true
)
val lightning_staff = Item(
    name = "Scepter of Lightning",
    type = "Weapon",
    description = "A royal scepter crackling with electrical energy, capable of summoning lightning bolts.",
    attack = 14,
    isWeapon = true
)

//Melee weapons
val greatsword = Item(
    name = "Greatsword",
    type = "Weapon",
    description = "A massive sword wielded with two hands, delivering devastating blows to enemies.",
    attack = 15,
    isWeapon = true
)
val warhammer = Item(
    name = "Warhammer",
    type = "Weapon",
    description = "A heavy hammer designed for crushing armor and breaking bones.",
    attack = 13,
    isWeapon = true
)
val dual_daggers = Item(
    name = "Twin Daggers",
    type = "Weapon",
    description = "A pair of sharp daggers used for swift and deadly strikes in close combat.",
    attack = 11,
    strBoost = 30,
    agiBoost = 20,
    isWeapon = true
)

//Basic consumables
val potion = Item(
    name = "Health Potion",
    type = "Consumable",
    description = "Restores a small amount of health when consumed.",
    heals = 25,
    isUsable = true
)
val elixer = Item(
    name = "Mana Elixir",
    type = "Consumable",
    description = "Restores a small amount of mana when consumed.",
    mana = 25,
    isUsable = true
)
val antidote = Item(
    name = "Antidote",
    type = "Consumable",
    description = "Cures poison status when consumed.",
    isUsable = true
)


//Legendary Items
val excalibur = Item(
    name = "Excalibur Sword",
    type = "Weapon",
    description = "Legendary sword with high attack power and a chance to deal critical hits.",
    attack = 50,
    isWeapon = true
)
val dragon_armor = Item(
    name = "Dragon Scale Armor",
    type = "Armor",
    description = "Armor made from dragon scales, providing excellent defense against fire attacks.",
    defense = 40,
    isArmor = true
)
val amulet_healing = Item(
    name = "Amulet of Healing",
    type = "Key",
    description = "Magical amulet that restores health over time when equipped.",
    heals = 10,
    isSelfUsable = true
)

// Key Items
val BlankBook = Item(
    name = "Blank Book",
    type = "Key",
    description = "A Blank Book? Why is a book blank?",
    isKey = true
)