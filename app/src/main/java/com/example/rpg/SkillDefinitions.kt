package com.example.rpg

// Define your skills here
val fireball = Skill(
    name = "Fireball",
    description = "Unleashes a fiery projectile at the target.",
    mpCost = 10,
    damage = 20,
    targetType = TargetType.SINGLE,
    isPassive = false,
    element = Element.FIRE
)

val heal = Skill(
    name = "Heal",
    description = "Restores health to a single target.",
    mpCost = 8,
    heal = 30,
    targetType = TargetType.SINGLE,
    isPassive = false,
    element = Element.HOLY
)

val thunder = Skill(
    name = "Thunder",
    description = "Calls down a bolt of lightning to strike the target.",
    mpCost = 12,
    damage = 25,
    targetType = TargetType.SINGLE,
    isPassive = false,
    element = Element.LIGHTNING
)

val sleep = Skill(
    name = "Sleep",
    description = "Puts the target to sleep, rendering them unable to act for a short duration.",
    mpCost = 15,
    statusEffect = StatusEffect(EffectType.SLEEP, duration = 3),
    targetType = TargetType.SINGLE,
    isPassive = false,
    element = Element.NONE
)

val tackle = Skill(
    name = "Tackle",
    description = "Charges at the target with brute force.",
    mpCost = 0,
    damage = 15,
    targetType = TargetType.SINGLE,
    isPassive = false,
    element = Element.NONE
)

val slash = Skill(
    name = "Slash",
    description = "Unleashes a powerful slashing attack against the target.",
    mpCost = 10,
    damage = 30,
    targetType = TargetType.SINGLE,
    isPassive = false,
    element = Element.NONE
)

val ice_shard = Skill(
    name = "Ice Shard",
    description = "Launches a sharp shard of ice at the target.",
    mpCost = 10,
    damage = 20,
    targetType = TargetType.SINGLE,
    isPassive = false,
    element = Element.ICE
)

val poison_sting = Skill(
    name = "Poison Sting",
    description = "Inflicts the target with a toxic poison, dealing damage over time.",
    mpCost = 12,
    statusEffect = StatusEffect(EffectType.POISON, duration = 3),
    targetType = TargetType.SINGLE,
    isPassive = false,
    element = Element.NONE
)

val earthquake = Skill(
    name = "Earthquake",
    description = "Shakes the ground violently, damaging all enemies.",
    mpCost = 15,
    damage = 25,
    targetType = TargetType.ALL,
    isPassive = false,
    element = Element.EARTH
)

val holy_light = Skill(
    name = "Holy Light",
    description = "Calls upon divine energy to smite evil and heal allies.",
    mpCost = 20,
    damage = 30,
    heal = 40,
    targetType = TargetType.ALL,
    isPassive = false,
    element = Element.HOLY
)

val shadow_strike = Skill(
    name = "Shadow Strike",
    description = "Conceals the user in darkness and delivers a deadly strike.",
    mpCost = 18,
    damage = 35,
    targetType = TargetType.SINGLE,
    isPassive = false,
    element = Element.DARK
)

val wind_blast = Skill(
    name = "Wind Blast",
    description = "Unleashes a powerful gust of wind to knock back enemies.",
    mpCost = 15,
    damage = 20,
    targetType = TargetType.SINGLE,
    isPassive = false,
    element = Element.WIND
)

val defenseMastery = Skill(
    name = "Defense Mastery",
    level = 1,
    mpCost = 0,
    isPassive = true,
    description = "Increases defense by 10%.",
    element = Element.NONE,
    targetType = TargetType.SELF
)

val criticalStrike = Skill(
    name = "Critical Strike",
    level = 1,
    mpCost = 0,
    isPassive = true,
    description = "Increases critical hit chance by 5%.",
    element = Element.NONE,
    targetType = TargetType.SELF
)

val evasionTraining = Skill(
    name = "Evasion Training",
    level = 1,
    mpCost = 0,
    isPassive = true,
    description = "Increases evasion by 10%.",
    element = Element.NONE,
    targetType = TargetType.SELF
)

val ElementalAffinity = Skill(
    name = "Elemental Affinity",
    level = 1,
    mpCost = 0,
    isPassive = true,
    description = "Increases damage dealt with elemental attacks by 15%.",
    element = Element.NONE, // Assuming it applies to all elements
    targetType = TargetType.SELF
)

val SwiftFeet = Skill(
    name = "Swift Feet",
    level = 1,
    mpCost = 0,
    isPassive = true,
    description = "Increases movement speed by 20% outside of combat.",
    element = Element.NONE,
    targetType = TargetType.SELF
)

val TreasureHunter = Skill(
    name = "Treasure Hunter",
    level = 1,
    mpCost = 0,
    isPassive = true,
    description = "Increases the chance of finding rare items by 10%.",
    element = Element.NONE,
    targetType = TargetType.SELF
)

val Pyromaniac = Skill(
    name = "Pyromaniac",
    level = 1,
    mpCost = 0,
    isPassive = true,
    description = "Increases damage dealt with fire-based attacks by 20%.",
    element = Element.FIRE,
    targetType = TargetType.SELF
)

val FrostAura = Skill(
    name = "Frost Aura",
    level = 1,
    mpCost = 0,
    isPassive = true,
    description = "Grants a chance to freeze enemies upon being hit with ice-based attacks.",
    element = Element.ICE,
    targetType = TargetType.SELF
)

val Shockwave = Skill(
    name = "Shockwave",
    level = 1,
    mpCost = 0,
    isPassive = true,
    description = "Chance to release a shockwave upon receiving damage, dealing damage to nearby enemies.",
    element = Element.LIGHTNING,
    targetType = TargetType.SELF
)

val EarthquakeResilience = Skill(
    name = "Earthquake Resilience",
    level = 1,
    mpCost = 0,
    isPassive = true,
    description = "Reduces damage taken from earth-based attacks by 20%.",
    element = Element.EARTH,
    targetType = TargetType.SELF
)

val GaleForce = Skill(
    name = "Gale Force",
    level = 1,
    mpCost = 0,
    isPassive = true,
    description = "Increases agility during windy weather, granting evasion and movement speed bonuses.",
    element = Element.WIND,
    targetType = TargetType.SELF
)
