package com.example.rpg

import android.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

// Story View
internal fun MainActivity.setupStoryViewListeners()
{
    //val ViewModel = ViewModelProvider(this).get(StoryData::class.java)

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
        setupSkillViewListeners()
    }

    // Inventory Button
    buttonInventory.setOnClickListener {
        var currentTxt = storyTextView.text.toString()

        ViewModel.currentTxt = currentTxt

        setContentView(R.layout.inventoryview)
        setupInventoryViewListeners()
    }

    // Setting Button
    buttonSetting1.setOnClickListener {
        var currentTxt = storyTextView.text.toString()

        ViewModel.currentTxt = currentTxt

        setContentView(R.layout.settingview)
        setupSettingViewListeners(true)
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
        if(ViewModel.storyIndex == 17) // Character Creation - Choose Gender
        {
            buttonChoice1.text = "You nervously laughed thinking this must be a joke of some sort"
            buttonChoice2.text = "You jokingly questioned how could a corpse be conscious like you are"
            buttonChoice3.text = "You were terrified from this news and ask for any other way to stay alive"
            buttonChoice4.text = "You angrily spit at the orb and didn’t believe a single word"

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