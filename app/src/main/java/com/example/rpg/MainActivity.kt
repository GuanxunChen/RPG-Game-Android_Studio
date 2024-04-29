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
    val inventoryItems: MutableMap<String, Int> = mutableMapOf(
        "sword" to 5,
        "potion" to 3,
        "apple" to 2,
        "boot" to 10,
        "arrow" to 69,
        "staff" to 0,
        "sheild" to 0,
        "dagger" to 0
    )
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
    var element: String,
    var attack: Int,
    var defense: Int,
    var damage: Int,
    var heals: Int,
    var quantity: Int,
    var isKey: Int,
    var isHelmet: Boolean,
    var isArmor: Boolean,
    var isBoot: Boolean,
    var isWeapon: Boolean,
    var isSelfUsable: Boolean,
    var isUsable: Boolean,
)

class StoryData : ViewModel() {
    var storyLines: MutableList<String> = mutableListOf(
        "",
        "Hello, I am sorry to tell you, another you in the parallel world has just died.\n",//1
        "To express my sorrows, I've decided to send that poor soul to another new start.\n",
        "And you, got the privilege of helping him during this process!\n",
        "Now allow me to ask some questions.....\n",
        "What gender do you prefer to be?\n",//5
        "Which of following power do you think is the strongest?\n",//6
        "What is your favorite weapon in between these choices?\n",//7
        "What is your name?\n",//8
        "Okay! Then let the new life start!",//9
        "",//10 Character Creation Finish
        "When you regain your consciousness, you found yourself to be laying on a bed\n",
        "The bed has guardrails on the sides, while on top of the blankets lies many childish toys\n",
        "It was then, you started to have a vaguely clue on what is going on.\n",
        "Immediately, you looked down onto your hands.\n",
        "And as expected.... It was a small set of hands that you don't recognize\n",//15
        "You, have turn into a Toddler right now -- a cute child who can't even quite speak yet!\n ",
        "The door creaks and opens, a handsome man with silver hair walks into the room.\n",
        "He wears an Studded Leather Armor, and on his belt is a long sword sheathed within the scabbard.\n",
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
            //setupSettingViewListeners()
        }

        buttonQuit.setOnClickListener {
            finish()
        }
    }
}
