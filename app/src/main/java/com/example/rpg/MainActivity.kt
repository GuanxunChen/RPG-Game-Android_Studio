package com.example.rpg

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.widget.Button
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import android.widget.ImageView
import androidx.activity.viewModels

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
    var relationlevel: Int = 0
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

    var PlayerCharacters = mutableListOf(
        PlayerCharacter(true,"Hero", "Male","Peasant","","",1,
            1,1,1,1,10,10,5),
        PlayerCharacter(false,"Heroine", "Female","Peasant","","",1,
            10,10,10,10,10,10),
        PlayerCharacter(false,"Saint", "Male","Peasant","","",1,
            10,10,10,10,10,10),
    )

    var FriendlyNPC = mutableListOf(
        NPC("Guard",5,75,30,67,42,57,47,75,30,10),
        NPC("Teacher",3,65,54,45,51,46,45,59,29,8),
        NPC("Students",1,50,30,45,35,40,35,55,25,8),
    )

    var EnemyNPC = mutableListOf(
        NPC("Bats",1,20,9,19,13,19,17,31,16,5),
        NPC("Skeleton",2,50,6,70,16,45,26,41,11,6),
        NPC("SkeletonKnight",5,75,15,92,30,62,40,62,17,8),
        NPC("Ghoul",7,65,12,109,33,68,44,92,35,10),
        NPC("Vampire",10,110,30,129,53,94,69,117,45,13),
    )
}

class MainActivity : ComponentActivity() {

    private lateinit var ViewModel: StoryData

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

    // Story View
    private fun setupStoryViewListeners() {
        val viewModel = ViewModelProvider(this).get(StoryData::class.java)

        val buttonCharacter = findViewById<Button>(R.id.CharMenu)
        val buttonSkill = findViewById<Button>(R.id.SkillMenu)
        val buttonInventory = findViewById<Button>(R.id.InventoryMenu)
        val buttonSetting1 = findViewById<Button>(R.id.Setting1)
        val buttonNext = findViewById<Button>(R.id.ContinueButton)
        val buttonChoice1 = findViewById<Button>(R.id.Choice1)
        val buttonChoice2 = findViewById<Button>(R.id.Choice2)
        val buttonChoice3 = findViewById<Button>(R.id.Choice3)
        val buttonChoice4 = findViewById<Button>(R.id.Choice4)

        val scrollView = findViewById<ScrollView>(R.id.StoryHistory)

        val storyTextView = findViewById<TextView>(R.id.Story)
        val char1Name = findViewById<TextView>(R.id.Char1name)
        val char2Name = findViewById<TextView>(R.id.Char2name)

        val char1Image = findViewById<ImageView>(R.id.Char1photo)
        val char2Image = findViewById<ImageView>(R.id.Char2photo)

        // Invisible buttons
        if (!ViewModel.storyButtonVisible[0]){buttonCharacter.visibility = View.INVISIBLE}
        else{buttonCharacter.visibility = View.VISIBLE}
        if (!ViewModel.storyButtonVisible[1]){buttonSkill.visibility = View.INVISIBLE}
        else{buttonSkill.visibility = View.VISIBLE}
        if (!ViewModel.storyButtonVisible[2]){buttonInventory.visibility = View.INVISIBLE}
        else{buttonInventory.visibility = View.VISIBLE}
        if (!ViewModel.storyButtonVisible[3]){buttonSetting1.visibility = View.INVISIBLE}
        else{buttonSetting1.visibility = View.VISIBLE}
        if (!ViewModel.storyButtonVisible[4]){buttonNext.visibility = View.INVISIBLE}
        else{buttonNext.visibility = View.VISIBLE}
        if (!ViewModel.storyButtonVisible[5]){buttonChoice1.visibility = View.INVISIBLE}
        else{buttonChoice1.visibility = View.VISIBLE}
        if (!ViewModel.storyButtonVisible[6]){buttonChoice2.visibility = View.INVISIBLE}
        else{buttonChoice2.visibility = View.VISIBLE}
        if (!ViewModel.storyButtonVisible[7]){buttonChoice3.visibility = View.INVISIBLE}
        else{buttonChoice3.visibility = View.VISIBLE}
        if (!ViewModel.storyButtonVisible[8]){buttonChoice4.visibility = View.INVISIBLE}
        else{buttonChoice4.visibility = View.VISIBLE}
        if (!ViewModel.storyButtonVisible[9]){char1Name.visibility = View.INVISIBLE}
        else{char1Name.visibility = View.VISIBLE}
        if (!ViewModel.storyButtonVisible[10]){char2Name.visibility = View.INVISIBLE}
        else{char2Name.visibility = View.VISIBLE}

        buttonChoice1.text = ViewModel.storyButtontag[5]
        buttonChoice2.text = ViewModel.storyButtontag[6]
        buttonChoice3.text = ViewModel.storyButtontag[7]
        buttonChoice4.text = ViewModel.storyButtontag[8]

        // Display current line
        storyTextView.setTextColor(ContextCompat.getColor(this, R.color.black))
        //storyTextView.text = ViewModel.storyLines[ViewModel.storyIndex] // Access storyLines
        storyTextView.text = ViewModel.currentTxt

        scrollView.post {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }

        // Character Button
        buttonCharacter.setOnClickListener {
            var currentTxt = storyTextView.text.toString()

            ViewModel.currentTxt = currentTxt

            setContentView(R.layout.characterview)
            setupCharacterViewListeners()
        }

        // Skill Button
        buttonSkill.setOnClickListener {
            var currentTxt = storyTextView.text.toString()

            ViewModel.currentTxt = currentTxt

            setContentView(R.layout.skillview)
            //setupSkillViewListeners()
        }

        // Inventory Button
        buttonInventory.setOnClickListener {
            var currentTxt = storyTextView.text.toString()

            ViewModel.currentTxt = currentTxt

            setContentView(R.layout.inventoryview)
            //setupInventoryViewListeners()
        }

        // Setting Button
        buttonSetting1.setOnClickListener {
            var currentTxt = storyTextView.text.toString()

            ViewModel.currentTxt = currentTxt

            setContentView(R.layout.settingview)
            //setupSettingViewListeners()
        }

        // Next Button
        buttonNext.setOnClickListener {

            // Continue the story
            ViewModel.storyIndex++

            var currentTxt = storyTextView.text.toString()

            var newText = "${currentTxt}\n${ViewModel.storyLines[ViewModel.storyIndex]}"

            storyTextView.text = newText

            scrollView.post {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }

            // Question section
            if(ViewModel.storyIndex == 5) // Character Creation - Choose Gender
            {
                buttonChoice1.text = "Male"
                buttonChoice2.text = "Female"

                buttonNext.visibility = View.INVISIBLE
                buttonChoice1.visibility = View.VISIBLE
                buttonChoice2.visibility = View.VISIBLE

                ViewModel.storyButtonVisible[4] = false
                ViewModel.storyButtonVisible[5] = true
                ViewModel.storyButtonVisible[6] = true
            }

            if(ViewModel.storyIndex == 22) // Character Growth - Selection1
            {
                buttonChoice1.text = "Slap him on the face" // Aggressive
                buttonChoice2.text = "Kiss him on the cheek" // Passive
                buttonChoice3.text = "Crying out loud" // Passive Aggressive
                buttonChoice4.text = "Stare at him silently" // Assertive

                ViewModel.storyButtontag[5] = "Slap him on the face"
                ViewModel.storyButtontag[6] = "Kiss him on the cheek"
                ViewModel.storyButtontag[7] = "Crying out loud"
                ViewModel.storyButtontag[8] = "Stare at him silently"

                buttonNext.visibility = View.INVISIBLE
                buttonChoice1.visibility = View.VISIBLE
                buttonChoice2.visibility = View.VISIBLE
                buttonChoice3.visibility = View.VISIBLE
                buttonChoice4.visibility = View.VISIBLE

                ViewModel.storyButtonVisible[4] = false
                ViewModel.storyButtonVisible[5] = true
                ViewModel.storyButtonVisible[6] = true
                ViewModel.storyButtonVisible[7] = true
                ViewModel.storyButtonVisible[8] = true
            }

            // Stage Change section
            if(ViewModel.storyIndex == 10) // Character Creation - Finish
            {
                buttonCharacter.visibility = View.VISIBLE
                buttonSkill.visibility = View.VISIBLE
                buttonInventory.visibility = View.VISIBLE

                ViewModel.storyButtonVisible[0] = true
                ViewModel.storyButtonVisible[1] = true
                ViewModel.storyButtonVisible[2] = true

                // Refresh
                ViewModel.currentTxt = ViewModel.storyLines[ViewModel.storyIndex]
                setupStoryViewListeners()
            }
        }

        buttonChoice1.setOnClickListener {
            // Answer section
            if(ViewModel.storyIndex == 5)// Character Creation - Choose Gender
            {
                ViewModel.PlayerCharacters[0].gender = "Male"

                buttonNext.visibility = View.VISIBLE
                buttonChoice1.visibility = View.INVISIBLE
                buttonChoice2.visibility = View.INVISIBLE

                ViewModel.storyButtonVisible[4] = true
                ViewModel.storyButtonVisible[5] = false
                ViewModel.storyButtonVisible[6] = false
            }

            if(ViewModel.storyIndex == 6)// Character Creation - Choose Power
            {
                ViewModel.PlayerCharacters[0].type = "Magic"

                buttonNext.visibility = View.VISIBLE
                buttonChoice1.visibility = View.INVISIBLE
                buttonChoice2.visibility = View.INVISIBLE
                buttonChoice3.visibility = View.INVISIBLE

                ViewModel.storyButtonVisible[4] = true
                ViewModel.storyButtonVisible[5] = false
                ViewModel.storyButtonVisible[6] = false
                ViewModel.storyButtonVisible[7] = false
            }

            if(ViewModel.storyIndex == 7) // Character Creation - Choose Weapon
            {
                when(ViewModel.PlayerCharacters[0].type)
                {
                    "Magic" -> {
                        ViewModel.PlayerCharacters[0].weaponType =  "Staff"

                        buttonNext.visibility = View.VISIBLE
                        buttonChoice1.visibility = View.INVISIBLE
                        buttonChoice2.visibility = View.INVISIBLE
                        buttonChoice3.visibility = View.INVISIBLE

                        ViewModel.storyButtonVisible[4] = true
                        ViewModel.storyButtonVisible[5] = false
                        ViewModel.storyButtonVisible[6] = false
                        ViewModel.storyButtonVisible[7] = false
                    }
                    "Holy" -> {
                        ViewModel.PlayerCharacters[0].weaponType =  "Staff"

                        buttonNext.visibility = View.VISIBLE
                        buttonChoice1.visibility = View.INVISIBLE
                        buttonChoice2.visibility = View.INVISIBLE
                        buttonChoice3.visibility = View.INVISIBLE

                        ViewModel.storyButtonVisible[4] = true
                        ViewModel.storyButtonVisible[5] = false
                        ViewModel.storyButtonVisible[6] = false
                        ViewModel.storyButtonVisible[7] = false
                    }
                    "Ki" -> {
                        ViewModel.PlayerCharacters[0].weaponType =  "Sword"

                        buttonNext.visibility = View.VISIBLE
                        buttonChoice1.visibility = View.INVISIBLE
                        buttonChoice2.visibility = View.INVISIBLE
                        buttonChoice3.visibility = View.INVISIBLE
                        buttonChoice4.visibility = View.INVISIBLE

                        ViewModel.storyButtonVisible[4] = true
                        ViewModel.storyButtonVisible[5] = false
                        ViewModel.storyButtonVisible[6] = false
                        ViewModel.storyButtonVisible[7] = false
                        ViewModel.storyButtonVisible[8] = false
                    }
                }
            }

            if(ViewModel.storyIndex == 22) // Character Growth - Selection1
            {
                ViewModel.aggressiveness++

                ViewModel.storyLines[23] = "You slapped him right on the face!\n"

                ViewModel.storyButtontag[5] = "Choice 1"
                ViewModel.storyButtontag[6] = "Choice 2"
                ViewModel.storyButtontag[7] = "Choice 3"
                ViewModel.storyButtontag[8] = "Choice 4"

                buttonNext.visibility = View.VISIBLE
                buttonChoice1.visibility = View.INVISIBLE
                buttonChoice2.visibility = View.INVISIBLE
                buttonChoice3.visibility = View.INVISIBLE
                buttonChoice4.visibility = View.INVISIBLE

                ViewModel.storyButtonVisible[4] = true
                ViewModel.storyButtonVisible[5] = false
                ViewModel.storyButtonVisible[6] = false
                ViewModel.storyButtonVisible[7] = false
                ViewModel.storyButtonVisible[8] = false
            }

            // Continue the story
            ViewModel.storyIndex++

            var currentTxt = storyTextView.text.toString()

            var newText = "${currentTxt}\n${ViewModel.storyLines[ViewModel.storyIndex]}"

            storyTextView.text = newText

            scrollView.post {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }

            // Question section
            if(ViewModel.storyIndex == 6) // Character Creation - Choose Power
            {
                buttonChoice1.text = "Power of Magic"
                buttonChoice2.text = "Power of Ki and Martial Arts"
                buttonChoice3.text = "Power from the Divine"

                buttonNext.visibility = View.INVISIBLE
                buttonChoice1.visibility = View.VISIBLE
                buttonChoice2.visibility = View.VISIBLE
                buttonChoice3.visibility = View.VISIBLE

                ViewModel.storyButtonVisible[4] = false
                ViewModel.storyButtonVisible[5] = true
                ViewModel.storyButtonVisible[6] = true
                ViewModel.storyButtonVisible[7] = true
            }

            if(ViewModel.storyIndex == 7) // Character Creation - Choose Weapon
            {
                when(ViewModel.PlayerCharacters[0].type)
                {
                    "Magic" -> {
                        buttonChoice1.text = "Staff"
                        buttonChoice2.text = "Wand"
                        buttonChoice3.text = "Tomb"

                        buttonNext.visibility = View.INVISIBLE
                        buttonChoice1.visibility = View.VISIBLE
                        buttonChoice2.visibility = View.VISIBLE
                        buttonChoice3.visibility = View.VISIBLE

                        ViewModel.storyButtonVisible[4] = false
                        ViewModel.storyButtonVisible[5] = true
                        ViewModel.storyButtonVisible[6] = true
                        ViewModel.storyButtonVisible[7] = true
                    }
                    "Holy" -> {
                        buttonChoice1.text = "Staff"
                        buttonChoice2.text = "Ritual Sword"
                        buttonChoice3.text = "Amulet"

                        buttonNext.visibility = View.INVISIBLE
                        buttonChoice1.visibility = View.VISIBLE
                        buttonChoice2.visibility = View.VISIBLE
                        buttonChoice3.visibility = View.VISIBLE

                        ViewModel.storyButtonVisible[4] = false
                        ViewModel.storyButtonVisible[5] = true
                        ViewModel.storyButtonVisible[6] = true
                        ViewModel.storyButtonVisible[7] = true
                    }
                    "Ki" -> {
                        buttonChoice1.text = "Sword"
                        buttonChoice2.text = "Lance"
                        buttonChoice3.text = "Claymore"
                        buttonChoice4.text = "Bow & Arrow"

                        buttonNext.visibility = View.INVISIBLE
                        buttonChoice1.visibility = View.VISIBLE
                        buttonChoice2.visibility = View.VISIBLE
                        buttonChoice3.visibility = View.VISIBLE
                        buttonChoice4.visibility = View.VISIBLE

                        ViewModel.storyButtonVisible[4] = false
                        ViewModel.storyButtonVisible[5] = true
                        ViewModel.storyButtonVisible[6] = true
                        ViewModel.storyButtonVisible[7] = true
                        ViewModel.storyButtonVisible[8] = true
                    }
                }
            }

            if(ViewModel.storyIndex == 8) // Character Creation - Name
            {
                val input = EditText(this)
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Enter 'Your' First Name\n[please be appropriate at least]")
                    .setView(input)
                    .setPositiveButton("OK") { dialog, _ ->
                        ViewModel.PlayerCharacters[0].name = input.text.toString()
                        dialog.dismiss() // Dismiss the dialog after getting input
                    }
                    .create()
                dialog.show()
            }
        }

        buttonChoice2.setOnClickListener {
            // Answer section
            if(ViewModel.storyIndex == 5)// Character Creation - Choose Gender
            {
                ViewModel.PlayerCharacters[0].gender = "Female"
                buttonChoice1.visibility = View.INVISIBLE
                buttonChoice2.visibility = View.INVISIBLE
                buttonNext.visibility = View.VISIBLE
            }

            if(ViewModel.storyIndex == 6)// Character Creation - Choose Power
            {
                ViewModel.PlayerCharacters[0].type = "Ki"

                buttonNext.visibility = View.VISIBLE
                buttonChoice1.visibility = View.INVISIBLE
                buttonChoice2.visibility = View.INVISIBLE
                buttonChoice3.visibility = View.INVISIBLE

                ViewModel.storyButtonVisible[4] = true
                ViewModel.storyButtonVisible[5] = false
                ViewModel.storyButtonVisible[6] = false
                ViewModel.storyButtonVisible[7] = false
            }

            if(ViewModel.storyIndex == 7) // Character Creation - Choose Weapon
            {
                when(ViewModel.PlayerCharacters[0].type)
                {
                    "Magic" -> {
                        ViewModel.PlayerCharacters[0].weaponType =  "Wand"

                        buttonNext.visibility = View.VISIBLE
                        buttonChoice1.visibility = View.INVISIBLE
                        buttonChoice2.visibility = View.INVISIBLE
                        buttonChoice3.visibility = View.INVISIBLE

                        ViewModel.storyButtonVisible[4] = true
                        ViewModel.storyButtonVisible[5] = false
                        ViewModel.storyButtonVisible[6] = false
                        ViewModel.storyButtonVisible[7] = false
                    }
                    "Holy" -> {
                        ViewModel.PlayerCharacters[0].weaponType =  "Ritual Sword"

                        buttonNext.visibility = View.VISIBLE
                        buttonChoice1.visibility = View.INVISIBLE
                        buttonChoice2.visibility = View.INVISIBLE
                        buttonChoice3.visibility = View.INVISIBLE

                        ViewModel.storyButtonVisible[4] = true
                        ViewModel.storyButtonVisible[5] = false
                        ViewModel.storyButtonVisible[6] = false
                        ViewModel.storyButtonVisible[7] = false
                    }
                    "Ki" -> {
                        ViewModel.PlayerCharacters[0].weaponType =  "Lance"

                        buttonNext.visibility = View.VISIBLE
                        buttonChoice1.visibility = View.INVISIBLE
                        buttonChoice2.visibility = View.INVISIBLE
                        buttonChoice3.visibility = View.INVISIBLE
                        buttonChoice4.visibility = View.INVISIBLE

                        ViewModel.storyButtonVisible[4] = true
                        ViewModel.storyButtonVisible[5] = false
                        ViewModel.storyButtonVisible[6] = false
                        ViewModel.storyButtonVisible[7] = false
                        ViewModel.storyButtonVisible[8] = false
                    }
                }
            }

            if(ViewModel.storyIndex == 22) // Character Growth - Selection1
            {
                ViewModel.passiveness++

                ViewModel.storyLines[23] = "You gave him a peck on the cheek.\n"

                ViewModel.storyButtontag[5] = "Choice 1"
                ViewModel.storyButtontag[6] = "Choice 2"
                ViewModel.storyButtontag[7] = "Choice 3"
                ViewModel.storyButtontag[8] = "Choice 4"

                buttonNext.visibility = View.VISIBLE
                buttonChoice1.visibility = View.INVISIBLE
                buttonChoice2.visibility = View.INVISIBLE
                buttonChoice3.visibility = View.INVISIBLE
                buttonChoice4.visibility = View.INVISIBLE

                ViewModel.storyButtonVisible[4] = true
                ViewModel.storyButtonVisible[5] = false
                ViewModel.storyButtonVisible[6] = false
                ViewModel.storyButtonVisible[7] = false
                ViewModel.storyButtonVisible[8] = false
            }

            // Continue the story
            ViewModel.storyIndex++

            var currentTxt = storyTextView.text.toString()

            var newText = "${currentTxt}\n${ViewModel.storyLines[ViewModel.storyIndex]}"

            storyTextView.text = newText

            scrollView.post {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }

            // Question section
            if(ViewModel.storyIndex == 6) // Character Creation - Choose Power
            {
                buttonChoice1.text = "Power of Magic"
                buttonChoice2.text = "Power of Ki and Martial Arts"
                buttonChoice3.text = "Power from the Divine"

                buttonNext.visibility = View.INVISIBLE
                buttonChoice1.visibility = View.VISIBLE
                buttonChoice2.visibility = View.VISIBLE
                buttonChoice3.visibility = View.VISIBLE

                ViewModel.storyButtonVisible[4] = false
                ViewModel.storyButtonVisible[5] = true
                ViewModel.storyButtonVisible[6] = true
                ViewModel.storyButtonVisible[7] = true
            }

            if(ViewModel.storyIndex == 7) // Character Creation - Choose Weapon
            {
                when(ViewModel.PlayerCharacters[0].type)
                {
                    "Magic" -> {
                        buttonChoice1.text = "Staff"
                        buttonChoice2.text = "Wand"
                        buttonChoice3.text = "Tomb"

                        buttonNext.visibility = View.INVISIBLE
                        buttonChoice1.visibility = View.VISIBLE
                        buttonChoice2.visibility = View.VISIBLE
                        buttonChoice3.visibility = View.VISIBLE

                        ViewModel.storyButtonVisible[4] = false
                        ViewModel.storyButtonVisible[5] = true
                        ViewModel.storyButtonVisible[6] = true
                        ViewModel.storyButtonVisible[7] = true
                    }
                    "Holy" -> {
                        buttonChoice1.text = "Staff"
                        buttonChoice2.text = "Ritual Sword"
                        buttonChoice3.text = "Amulet"

                        buttonNext.visibility = View.INVISIBLE
                        buttonChoice1.visibility = View.VISIBLE
                        buttonChoice2.visibility = View.VISIBLE
                        buttonChoice3.visibility = View.VISIBLE

                        ViewModel.storyButtonVisible[4] = false
                        ViewModel.storyButtonVisible[5] = true
                        ViewModel.storyButtonVisible[6] = true
                        ViewModel.storyButtonVisible[7] = true
                    }
                    "Ki" -> {
                        buttonChoice1.text = "Sword"
                        buttonChoice2.text = "Lance"
                        buttonChoice3.text = "Claymore"
                        buttonChoice4.text = "Bow & Arrow"

                        buttonNext.visibility = View.INVISIBLE
                        buttonChoice1.visibility = View.VISIBLE
                        buttonChoice2.visibility = View.VISIBLE
                        buttonChoice3.visibility = View.VISIBLE
                        buttonChoice4.visibility = View.VISIBLE

                        ViewModel.storyButtonVisible[4] = false
                        ViewModel.storyButtonVisible[5] = true
                        ViewModel.storyButtonVisible[6] = true
                        ViewModel.storyButtonVisible[7] = true
                        ViewModel.storyButtonVisible[8] = true
                    }
                }
            }

            if(ViewModel.storyIndex == 8) // Character Creation - Name
            {
                val input = EditText(this)
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Enter 'Your' First Name\n[please be appropriate at least]")
                    .setView(input)
                    .setPositiveButton("OK") { dialog, _ ->
                        ViewModel.PlayerCharacters[0].name = input.text.toString()
                        dialog.dismiss() // Dismiss the dialog after getting input
                    }
                    .create()
                dialog.show()
            }
        }

        buttonChoice3.setOnClickListener {
            // Answer section
            if(ViewModel.storyIndex == 6)// Character Creation - Choose Power
            {
                ViewModel.PlayerCharacters[0].type = "Holy"

                buttonNext.visibility = View.VISIBLE
                buttonChoice1.visibility = View.INVISIBLE
                buttonChoice2.visibility = View.INVISIBLE
                buttonChoice3.visibility = View.INVISIBLE

                ViewModel.storyButtonVisible[4] = true
                ViewModel.storyButtonVisible[5] = false
                ViewModel.storyButtonVisible[6] = false
                ViewModel.storyButtonVisible[7] = false
            }

            if(ViewModel.storyIndex == 7) // Character Creation - Choose Weapon
            {
                when(ViewModel.PlayerCharacters[0].type)
                {
                    "Magic" -> {
                        ViewModel.PlayerCharacters[0].weaponType =  "Tomb"

                        buttonNext.visibility = View.VISIBLE
                        buttonChoice1.visibility = View.INVISIBLE
                        buttonChoice2.visibility = View.INVISIBLE
                        buttonChoice3.visibility = View.INVISIBLE

                        ViewModel.storyButtonVisible[4] = true
                        ViewModel.storyButtonVisible[5] = false
                        ViewModel.storyButtonVisible[6] = false
                        ViewModel.storyButtonVisible[7] = false
                    }
                    "Holy" -> {
                        ViewModel.PlayerCharacters[0].weaponType =  "Amulet"

                        buttonNext.visibility = View.VISIBLE
                        buttonChoice1.visibility = View.INVISIBLE
                        buttonChoice2.visibility = View.INVISIBLE
                        buttonChoice3.visibility = View.INVISIBLE

                        ViewModel.storyButtonVisible[4] = true
                        ViewModel.storyButtonVisible[5] = false
                        ViewModel.storyButtonVisible[6] = false
                        ViewModel.storyButtonVisible[7] = false
                    }
                    "Ki" -> {
                        ViewModel.PlayerCharacters[0].weaponType =  "Claymore"

                        buttonNext.visibility = View.VISIBLE
                        buttonChoice1.visibility = View.INVISIBLE
                        buttonChoice2.visibility = View.INVISIBLE
                        buttonChoice3.visibility = View.INVISIBLE
                        buttonChoice4.visibility = View.INVISIBLE

                        ViewModel.storyButtonVisible[4] = true
                        ViewModel.storyButtonVisible[5] = false
                        ViewModel.storyButtonVisible[6] = false
                        ViewModel.storyButtonVisible[7] = false
                        ViewModel.storyButtonVisible[8] = false
                    }
                }
            }

            if(ViewModel.storyIndex == 22) // Character Growth - Selection1
            {
                ViewModel.passiveAgressiveness++

                ViewModel.storyLines[23] = "You started crying.\n"

                ViewModel.storyButtontag[5] = "Choice 1"
                ViewModel.storyButtontag[6] = "Choice 2"
                ViewModel.storyButtontag[7] = "Choice 3"
                ViewModel.storyButtontag[8] = "Choice 4"

                buttonNext.visibility = View.VISIBLE
                buttonChoice1.visibility = View.INVISIBLE
                buttonChoice2.visibility = View.INVISIBLE
                buttonChoice3.visibility = View.INVISIBLE
                buttonChoice4.visibility = View.INVISIBLE

                ViewModel.storyButtonVisible[4] = true
                ViewModel.storyButtonVisible[5] = false
                ViewModel.storyButtonVisible[6] = false
                ViewModel.storyButtonVisible[7] = false
                ViewModel.storyButtonVisible[8] = false
            }

            // Continue the story
            ViewModel.storyIndex++

            var currentTxt = storyTextView.text.toString()

            var newText = "${currentTxt}\n${ViewModel.storyLines[ViewModel.storyIndex]}"

            storyTextView.text = newText

            scrollView.post {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }

            // Question section
            if(ViewModel.storyIndex == 7) // Character Creation - Choose Weapon
            {
                when(ViewModel.PlayerCharacters[0].type)
                {
                    "Magic" -> {
                        buttonChoice1.text = "Staff"
                        buttonChoice2.text = "Wand"
                        buttonChoice3.text = "Tomb"

                        buttonNext.visibility = View.INVISIBLE
                        buttonChoice1.visibility = View.VISIBLE
                        buttonChoice2.visibility = View.VISIBLE
                        buttonChoice3.visibility = View.VISIBLE

                        ViewModel.storyButtonVisible[4] = false
                        ViewModel.storyButtonVisible[5] = true
                        ViewModel.storyButtonVisible[6] = true
                        ViewModel.storyButtonVisible[7] = true
                    }
                    "Holy" -> {
                        buttonChoice1.text = "Staff"
                        buttonChoice2.text = "Ritual Sword"
                        buttonChoice3.text = "Amulet"

                        buttonNext.visibility = View.INVISIBLE
                        buttonChoice1.visibility = View.VISIBLE
                        buttonChoice2.visibility = View.VISIBLE
                        buttonChoice3.visibility = View.VISIBLE

                        ViewModel.storyButtonVisible[4] = false
                        ViewModel.storyButtonVisible[5] = true
                        ViewModel.storyButtonVisible[6] = true
                        ViewModel.storyButtonVisible[7] = true
                    }
                    "Ki" -> {
                        buttonChoice1.text = "Sword"
                        buttonChoice2.text = "Lance"
                        buttonChoice3.text = "Claymore"
                        buttonChoice4.text = "Bow & Arrow"

                        buttonNext.visibility = View.INVISIBLE
                        buttonChoice1.visibility = View.VISIBLE
                        buttonChoice2.visibility = View.VISIBLE
                        buttonChoice3.visibility = View.VISIBLE
                        buttonChoice4.visibility = View.VISIBLE

                        ViewModel.storyButtonVisible[4] = false
                        ViewModel.storyButtonVisible[5] = true
                        ViewModel.storyButtonVisible[6] = true
                        ViewModel.storyButtonVisible[7] = true
                        ViewModel.storyButtonVisible[8] = true
                    }
                }
            }

            if(ViewModel.storyIndex == 8) // Character Creation - Name
            {
                val input = EditText(this)
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Enter 'Your' First Name\n[please be appropriate at least]")
                    .setView(input)
                    .setPositiveButton("OK") { dialog, _ ->
                        ViewModel.PlayerCharacters[0].name = input.text.toString()
                        dialog.dismiss() // Dismiss the dialog after getting input
                    }
                    .create()
                dialog.show()
            }
        }

        buttonChoice4.setOnClickListener {
            // Answer section
            if(ViewModel.storyIndex == 7) // Character Creation - Choose Weapon
            {
                ViewModel.PlayerCharacters[0].weaponType = "Bow & Arrow"

                buttonNext.visibility = View.VISIBLE
                buttonChoice1.visibility = View.INVISIBLE
                buttonChoice2.visibility = View.INVISIBLE
                buttonChoice3.visibility = View.INVISIBLE
                buttonChoice4.visibility = View.INVISIBLE

                ViewModel.storyButtonVisible[4] = true
                ViewModel.storyButtonVisible[5] = false
                ViewModel.storyButtonVisible[6] = false
                ViewModel.storyButtonVisible[7] = false
                ViewModel.storyButtonVisible[8] = false
            }

            if(ViewModel.storyIndex == 22) // Character Growth - Selection1
            {
                ViewModel.assertiveness++

                ViewModel.storyLines[23] = "You stared at him silently.\n"

                ViewModel.storyButtontag[5] = "Choice 1"
                ViewModel.storyButtontag[6] = "Choice 2"
                ViewModel.storyButtontag[7] = "Choice 3"
                ViewModel.storyButtontag[8] = "Choice 4"

                buttonNext.visibility = View.VISIBLE
                buttonChoice1.visibility = View.INVISIBLE
                buttonChoice2.visibility = View.INVISIBLE
                buttonChoice3.visibility = View.INVISIBLE
                buttonChoice4.visibility = View.INVISIBLE

                ViewModel.storyButtonVisible[4] = true
                ViewModel.storyButtonVisible[5] = false
                ViewModel.storyButtonVisible[6] = false
                ViewModel.storyButtonVisible[7] = false
                ViewModel.storyButtonVisible[8] = false
            }

            // Continue the story
            ViewModel.storyIndex++

            var currentTxt = storyTextView.text.toString()

            var newText = "${currentTxt}\n${ViewModel.storyLines[ViewModel.storyIndex]}"

            storyTextView.text = newText

            scrollView.post {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }

            // Question section
            if(ViewModel.storyIndex == 8) // Character Creation - Name
            {
                val input = EditText(this)
                val dialog = AlertDialog.Builder(this)
                .setTitle("Enter 'Your' First Name\n[please be appropriate at least]")
                .setView(input)
                .setPositiveButton("OK") { dialog, _ ->
                    ViewModel.PlayerCharacters[0].name = input.text.toString()
                    dialog.dismiss() // Dismiss the dialog after getting input
                }
                .create()
                dialog.show()
            }
        }
    }

    private fun setupCharacterViewListeners() {
        val buttonStrAdd = findViewById<Button>(R.id.StrAdd)
        val buttonVitAdd = findViewById<Button>(R.id.VitAdd)
        val buttonAgiAdd = findViewById<Button>(R.id.AgiAdd)
        val buttonDexAdd = findViewById<Button>(R.id.DexAdd)
        val buttonIntAdd = findViewById<Button>(R.id.IntAdd)
        val buttonLucAdd = findViewById<Button>(R.id.LucAdd)

        val buttonChangeHelm = findViewById<Button>(R.id.changeHelm)
        val buttonChangeArmor = findViewById<Button>(R.id.changeArmor)
        val buttonChangeBoots = findViewById<Button>(R.id.changeBoots)
        val buttonChangeRing = findViewById<Button>(R.id.changeRing)
        val buttonChangeMain = findViewById<Button>(R.id.changeMain)
        val buttonChangeSub = findViewById<Button>(R.id.changeSub)

        val buttonBack = findViewById<Button>(R.id.returnStory)
        val buttonSkill = findViewById<Button>(R.id.toSkill)
        val buttonItem = findViewById<Button>(R.id.toItem)

        val char1Name = findViewById<TextView>(R.id.CharName1)
        val gender = findViewById<TextView>(R.id.Gender)
        val classes = findViewById<TextView>(R.id.Classes)
        val level = findViewById<TextView>(R.id.Level)

        val str = findViewById<TextView>(R.id.StrValue)
        val vit = findViewById<TextView>(R.id.VitValue)
        val agi = findViewById<TextView>(R.id.AgiValue)
        val dex = findViewById<TextView>(R.id.DexValue)
        val int = findViewById<TextView>(R.id.IntValue)
        val luc = findViewById<TextView>(R.id.LucValue)

        val helm = findViewById<TextView>(R.id.currentHelm)
        val armor = findViewById<TextView>(R.id.currentArmor)
        val boots = findViewById<TextView>(R.id.currentBoots)
        val ring = findViewById<TextView>(R.id.currentRing)
        val main = findViewById<TextView>(R.id.currentMain)
        val sub = findViewById<TextView>(R.id.currentSub)

        val ptAbility = findViewById<TextView>(R.id.AbilityPointValue)

        val char1Image = findViewById<ImageView>(R.id.Char1photo)

        char1Name.text = ViewModel.PlayerCharacters[ViewModel.charIndex].name
        gender.text = ViewModel.PlayerCharacters[ViewModel.charIndex].gender
        classes.text = ViewModel.PlayerCharacters[ViewModel.charIndex].classes
        level.text = ViewModel.PlayerCharacters[ViewModel.charIndex].level.toString()

        str.text = ViewModel.PlayerCharacters[ViewModel.charIndex].str.toString()
        vit.text = ViewModel.PlayerCharacters[ViewModel.charIndex].vit.toString()
        agi.text = ViewModel.PlayerCharacters[ViewModel.charIndex].agi.toString()
        dex.text = ViewModel.PlayerCharacters[ViewModel.charIndex].dex.toString()
        int.text = ViewModel.PlayerCharacters[ViewModel.charIndex].int.toString()
        luc.text = ViewModel.PlayerCharacters[ViewModel.charIndex].luc.toString()
        ptAbility.text = ViewModel.PlayerCharacters[ViewModel.charIndex].remainAbilityPt.toString()

        //char1Image.setImageResource(R.drawable.adult1)

        buttonStrAdd.setOnClickListener {
            if (ViewModel.PlayerCharacters[ViewModel.charIndex].remainAbilityPt > 0)
            {
                ViewModel.PlayerCharacters[ViewModel.charIndex].str++
                ViewModel.PlayerCharacters[ViewModel.charIndex].remainAbilityPt--
            }
            setupCharacterViewListeners()
        }
        buttonVitAdd.setOnClickListener {
            if (ViewModel.PlayerCharacters[ViewModel.charIndex].remainAbilityPt > 0)
            {
                ViewModel.PlayerCharacters[ViewModel.charIndex].vit++
                ViewModel.PlayerCharacters[ViewModel.charIndex].remainAbilityPt--
            }
            setupCharacterViewListeners()
        }
        buttonAgiAdd.setOnClickListener {
            if (ViewModel.PlayerCharacters[ViewModel.charIndex].remainAbilityPt > 0)
            {
                ViewModel.PlayerCharacters[ViewModel.charIndex].agi++
                ViewModel.PlayerCharacters[ViewModel.charIndex].remainAbilityPt--
            }
            setupCharacterViewListeners()
        }
        buttonDexAdd.setOnClickListener {
            if (ViewModel.PlayerCharacters[ViewModel.charIndex].remainAbilityPt > 0)
            {
                ViewModel.PlayerCharacters[ViewModel.charIndex].dex++
                ViewModel.PlayerCharacters[ViewModel.charIndex].remainAbilityPt--
            }
            setupCharacterViewListeners()
        }
        buttonIntAdd.setOnClickListener {
            if (ViewModel.PlayerCharacters[ViewModel.charIndex].remainAbilityPt > 0)
            {
                ViewModel.PlayerCharacters[ViewModel.charIndex].int++
                ViewModel.PlayerCharacters[ViewModel.charIndex].remainAbilityPt--
            }
            setupCharacterViewListeners()
        }
        buttonLucAdd.setOnClickListener {
            if (ViewModel.PlayerCharacters[ViewModel.charIndex].remainAbilityPt > 0)
            {
                ViewModel.PlayerCharacters[ViewModel.charIndex].luc++
                ViewModel.PlayerCharacters[ViewModel.charIndex].remainAbilityPt--
            }
            setupCharacterViewListeners()
        }

        buttonChangeHelm.setOnClickListener {
            //setContentView(R.layout.)
        }
        buttonChangeArmor.setOnClickListener {
            //setContentView(R.layout.)
        }
        buttonChangeBoots.setOnClickListener {
            //setContentView(R.layout.)
        }
        buttonChangeRing.setOnClickListener {
            //setContentView(R.layout.)
        }
        buttonChangeMain.setOnClickListener {
            //setContentView(R.layout.)
        }
        buttonChangeSub.setOnClickListener {
            //setContentView(R.layout.)
        }

        buttonBack.setOnClickListener {
            ViewModel.charIndex = 0
            setContentView(R.layout.storyview)
            setupStoryViewListeners()
        }
        buttonSkill.setOnClickListener {
            setContentView(R.layout.skillview)
        }
        buttonItem.setOnClickListener {
            //setContentView(R.layout.)
        }
    }
}
