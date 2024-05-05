package com.example.rpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import android.widget.Button

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

// damage = (atk - def) * ((hitLevel - takeLevel) > 1)
// damage = (matk - mdef) * ((hitLevel - takeLevel) > 1)
// HP = 5*vit + equip
// mp = 3*int + equip
// atk = 3*str + agi + 1/2*dex + equip
// matk = 2*int + dex + 1/2*agi + equip
// def = 2*vit + 1/2*agi + 1/2*luc + str + equip
// mdef = 2*vit + 1/2*agi + 1/2*luc + 1/2*int + equip
// hit = 1.5*dex + 2*agi + 1/2*str + 1/2*int + luc + equip
// dodge = agi + luc + 1/2*int + equip
// crit = .2*dex + .3*luc + .1*agi + .1*str + .1*int + equip

//str      10
//vit      10
//agi      10
//dex      10
//int      10
//luc      10

data class PlayerCharacter(
    var inTeam: Boolean = false,
    var name: String,
    var gender: String = "Male",
    var classes: String = "Peasant",
    var type: String = "",
    var weaponType: String = "",
    var level: Int,
    var str: Int,
    var vit: Int,
    var agi: Int,
    var dex: Int,
    var int: Int,
    var luc: Int,
    var remainAbilityPt: Int = 0,
    var remainSkillPt: Int = 0,
    var HPtempbattleOnly: Int = 0,
    var MPtempbattleOnly: Int = 0,
    var atktempbattleOnly: Int = 0,
    var matktempbattleOnly: Int = 0,
    var deftempbattleOnly: Int = 0,
    var mdeftempbattleOnly: Int = 0,
    var hittempbattleOnly: Int = 0,
    var dodgetempbattleOnly: Int = 0,
    var crittempbattleOnly: Int = 0,
    var relationlevel: Int = 0,
    var currentWeapon: Item? = null,
    var currentHelm: Item? = null,
    var currentArmor: Item? = null,
    var currentBoots: Item? = null,
    val inventoryItems: MutableMap<Item, Int> = mutableMapOf(),
    val skills: MutableMap<String, Skill> = mutableMapOf(
        "Fireball" to fireball,
        "Heal" to heal,
        "Sleep" to sleep,
        "Tackle" to tackle,
        "Thunder" to thunder,
        "Slash" to slash,
        "Pyromaniac" to Pyromaniac,
        "Elemental Affinity" to ElementalAffinity,
        "Gale Force" to GaleForce,
    ),
    var skillUpgradePoints: Int = 10
)

data class NPC(
    var name: String,
    var level: Int,
    var hp: Int,
    var mp: Int,
    var atk: Int,
    var matk: Int,
    var def: Int,
    var mdef: Int,
    var hit: Int,
    var dodge: Int,
    var crit: Int,
)

data class Item(
    var name: String,
    var type: String,
    var description: String,
    var attack: Int = 0,
    var mattack: Int = 0,
    var defense: Int = 0,
    var mdefense: Int = 0,
    var hpBoost: Int = 0,
    var strBoost: Int = 0,
    var vitBoost: Int = 0,
    var agiBoost: Int = 0,
    var dexBoost: Int = 0,
    var intBoost: Int = 0,
    var lucBoost: Int = 0,
    var heals: Int = 0,
    var mana: Int = 0,
    var quantity: Int = 0,
    var isKey: Boolean = false,
    var isHelmet: Boolean = false,
    var isArmor: Boolean = false,
    var isBoot: Boolean = false,
    var isWeapon: Boolean = false,
    var isSelfUsable: Boolean = false,
    var isUsable: Boolean = false,
    var isEquipped: Boolean = false
)
data class Skill(
    var name: String,
    var description: String,
    var isPassive: Boolean,
    var mpCost: Int,
    var level: Int = 1,
    var damage: Int = 0,
    var heal: Int = 0,
    var targetType: TargetType,
    var element: Element,
    var statusEffect: StatusEffect? = null,
    var cooldown: Int = 0
)

enum class TargetType {
    SINGLE, // Target one character
    ALL,    // Target all characters
    SELF    // Target the user
}

enum class Element {
    FIRE,
    ICE,
    LIGHTNING,
    EARTH,
    WATER,
    WIND,
    HOLY,
    DARK,
    NONE
}

data class StatusEffect(
    val effectType: EffectType,
    val duration: Int
)

enum class EffectType {
    POISON,
    PARALYSIS,
    SLEEP,
    BURN,
    FREEZE,
    STUN,
    BUFF,
    DEBUFF
}

class StoryData : ViewModel() {
    // Story View Use
    var storyLines: MutableList<String> = mutableListOf(
        "",
        "It is a sunny day, and the weather forecast says there will be no rain or snow. \n",//1
        "Birds are chirping, flowers are blooming....\n",
        "On days like this, people as young as you…. Should really sleep on time.\n",
        "The light shining onto your body feels so hot, your closed eyes twitch slightly as you feel your heart start to ache.\n",
        "Breathe is fastening, blood is pumping…. within every second, you can feel your body weakening.\n",//5
        "And when your hand finally reaches for your heart, it is all too late.\n",//6
        "As if there’s a lightning that suddenly struck you, you moan in agony and pain.\n",//7
        "Your consciousness fades away alone with the burning feeling, and fragments of thoughts slowly sink down into the eternal darkness.\n",//8
        "",//9
        "When you regain your consciousness, in front of your eyes floats a large glowing orb as big as a truck.",//10 Character Creation Finish
        "You were stunned, not sure what you are looking at and why you are here.\n",
        "But before you got to do anything, a robotic voice came from the orb, it speaks.\n",
        "\"Connection with the subject is established. Now loading the Introduction module…..\"\n",
        "\"Loaded.\"\n",
        "\"Welcome to the Room of Rebirth. Foreigners, we are sorry to inform you that you have died in your original world.\"\n",//15
        "\"The reason for your death seems to be a sudden heart attack, likely due to your inconsistent sleeping hours.\"\n ",
        "Hearing the words, you try to dive back in your memory, yet only to find your memory missing and incomplete. So you...\n",// Choice 1
        "",// User Input
        "The man sees you and smiles. He quickly walk up to you, and despite your resistance, picked you up and tightly hugs you.\n",
        "As he does that, the man talked in a language that you don't seem to understand.\n",//20
        "The man: \"哇~我的小可爱，今天在家有没有乖乖的？好孩子好孩子，来给爸爸抱抱~！\"\n",
        "Hearing this, you decide to......\n",//22 growth selection 1
        "UserOutput",
        "His movement stopped for the moment, looking right at your eye, before started to smile again.\n",
        "Unfinished [1]",
        "Unfinished [2]",
        "Unfinished [3]",
        "Unfinished [4]")

    var aggressiveness: Int = 0
    var passiveness: Int = 0
    var assertiveness: Int = 0
    var passiveAgressiveness: Int = 0

    var currentTxt = "" // the current line of story, usually used to save text for view jumping
    var storyIndex: Int = 0 // which line is the story currently on

    var charIndex: Int = 0 // the position of character showing in characterSheet

    var storyButtonVisible = mutableListOf(
        false,false,false,true, // Char, Skill, Inventory, Setting
        true, // Continue Button
        false,false,false,false, // 4 Choice Button
        false,false // Char name
    )
    var storyButtontag = mutableListOf(
        "Character","Skill","Inventory","Setting", // Char, Skill, Inventory, Setting
        "Next", // Continue Button
        "","","","", // 4 Choice Button
        "","" // Char name
    )

    // setting use

    var ItemList = mutableListOf<Item>(

    )

    var PlayerCharacters = mutableListOf<PlayerCharacter>(
        PlayerCharacter(true,"Hero", "Male","Peasant","","",1,
            1,1,1,1,10,10,5),
        PlayerCharacter(false,"Heroine", "Female","Peasant","","",1,
            10,10,10,10,10,10),
        PlayerCharacter(false,"Saint", "Male","Peasant","","",1,
            10,10,10,10,10,10),
    )

    var FriendlyNPC = mutableListOf<NPC>(
        NPC("Guard",5,75,30,67,42,57,47,75,30,10),
        NPC("Teacher",3,65,54,45,51,46,45,59,29,8),
        NPC("Students",1,50,30,45,35,40,35,55,25,8),
    )

    var EnemyNPC = mutableListOf<NPC>(
        NPC("Bats",1,20,9,19,13,19,17,31,16,5),
        NPC("Skeleton",2,50,6,70,16,45,26,41,11,6),
        NPC("SkeletonKnight",5,75,15,92,30,62,40,62,17,8),
        NPC("Ghoul",7,65,12,109,33,68,44,92,35,10),
        NPC("Vampire",10,110,30,129,53,94,69,117,45,13),
    )
}

class MainActivity : ComponentActivity() {

    internal lateinit var ViewModel: StoryData

    // Start up menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewModel = ViewModelProvider(this).get(StoryData::class.java)

        // Main Menu
        setContentView(R.layout.mainmenu)

        //starting gear
        ViewModel.PlayerCharacters[0].inventoryItems[iron_sword] = 1
        ViewModel.PlayerCharacters[0].inventoryItems[wand] = 1
        ViewModel.PlayerCharacters[0].inventoryItems[leather_cap] = 1
        ViewModel.PlayerCharacters[0].inventoryItems[leather_armor] = 1
        ViewModel.PlayerCharacters[0].inventoryItems[leather_boots] = 1
        ViewModel.PlayerCharacters[0].inventoryItems[potion] = 3
        ViewModel.PlayerCharacters[0].inventoryItems[elixer] = 3

        val buttonStart = findViewById<Button>(R.id.Start)
        val buttonContinue = findViewById<Button>(R.id.Continue)
        val buttonSetting = findViewById<Button>(R.id.Setting)
        val buttonQuit = findViewById<Button>(R.id.Quit)

        buttonStart.setOnClickListener {
            setContentView(R.layout.storyview)
            setupStoryViewListeners()
        }

        buttonContinue.setOnClickListener {
            setContentView(R.layout.loadview)
            //setupLoadViewListeners()
        }

        buttonSetting.setOnClickListener {
            setContentView(R.layout.settingview)
            setupSettingViewListeners(false)
        }

        buttonQuit.setOnClickListener {
            finish()
        }
    }
}
