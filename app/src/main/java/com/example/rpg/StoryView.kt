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
import kotlin.random.Random

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
        if(ViewModel.storyIndex == 17) // Type choice 1
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

            ViewModel.storyButtontag[5] = "You nervously laughed thinking this must be a joke of some sort"
            ViewModel.storyButtontag[6] = "You jokingly questioned how could a corpse be conscious like you are"
            ViewModel.storyButtontag[7] = "You were terrified from this news and ask for any other way to stay alive"
            ViewModel.storyButtontag[8] = "You angrily spit at the orb and didn’t believe a single word"

            ViewModel.storyButtonVisible[4] = false
            ViewModel.storyButtonVisible[5] = true
            ViewModel.storyButtonVisible[6] = true
            ViewModel.storyButtonVisible[7] = true
            ViewModel.storyButtonVisible[8] = true
        }

        if(!ViewModel.storyKeyFlags[0]) // if not yet declined the offer
        {

            if(ViewModel.storyIndex == 23 + ViewModel.storyPushBack &&
                ViewModel.storyLines[22 + ViewModel.storyPushBack-5] == "You Declined the offer.\n") //
            {
                ViewModel.currentTxt = ""
                ViewModel.storyIndex = 0
                ViewModel.storyPushBack = 0
                ViewModel.storyKeyFlags[0] = true
                ViewModel.storyLines = ViewModel.OriginStoryLines
                ViewModel.currentTxt = ViewModel.storyLines[ViewModel.storyIndex]
                setupStoryViewListeners()
            }

            if (!ViewModel.storyKeyFlags[1] && ViewModel.storyIndex == 21 + ViewModel.storyPushBack) // Character Growth - Selection1
            {
                buttonChoice1.text = "Yes"
                buttonChoice2.text = "No"

                ViewModel.storyButtontag[5] = "Yes"
                ViewModel.storyButtontag[6] = "No"

                buttonNext.visibility = View.INVISIBLE
                buttonChoice1.visibility = View.VISIBLE
                buttonChoice2.visibility = View.VISIBLE

                ViewModel.storyButtonVisible[4] = false
                ViewModel.storyButtonVisible[5] = true
                ViewModel.storyButtonVisible[6] = true
            }
        }
        else
        {
            if (ViewModel.storyIndex == 21 + ViewModel.storyPushBack) // Character Growth - Selection1
            {
                buttonChoice1.text = "Yes"
                buttonChoice2.text = "You feel this scene to be familiar. And out of instinct, you accepted the offer"

                ViewModel.storyButtontag[5] = "Yes"
                ViewModel.storyButtontag[6] = "You feel this scene to be familiar. And out of instinct, you accepted the offer"

                buttonNext.visibility = View.INVISIBLE
                buttonChoice1.visibility = View.VISIBLE
                buttonChoice2.visibility = View.VISIBLE

                ViewModel.storyButtonVisible[4] = false
                ViewModel.storyButtonVisible[5] = true
                ViewModel.storyButtonVisible[6] = true
            }
        }

        if (ViewModel.storyIndex == 27 + ViewModel.storyPushBack) // Character Gender
        {
            buttonChoice1.text = "Male"
            buttonChoice2.text = "Female"
            buttonChoice3.text = "You told the Orb that you are unsure"
            buttonChoice4.text = "You stood silently without a word"

            ViewModel.storyButtontag[5] = "Male"
            ViewModel.storyButtontag[6] = "Female"
            ViewModel.storyButtontag[7] = "You told the Orb that you are unsure"
            ViewModel.storyButtontag[8] = "You stood silently without a word"

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

        if (ViewModel.storyIndex == 31 + ViewModel.storyPushBack) // Character Nane
        {
            val input = EditText(this)
            val dialog = AlertDialog.Builder(this)
                .setTitle("Enter 'Your' First Name\n[please be appropriate at least]")
                .setView(input)
                .setPositiveButton("OK") { dialog, _ ->
                    // Update ViewModel.PlayerCharacters[0].name with the input value
                    ViewModel.PlayerCharacters[0].name = input.text.toString()
                    // Dismiss the dialog after getting input and update the story line
                    dialog.dismiss()

                    ViewModel.storyLines[32 + ViewModel.storyPushBack] =
                        "Orb:\"" + input.text.toString() + "? Okay, then we wish you the best luck in your new life. Farewell, " + input.text.toString() + ".\"\n"
                }
                .create()
            dialog.show()
        }

        if(ViewModel.storyIndex == 61+ViewModel.storyPushBack)
        {
            var string = ""
            var randomNumber = 1+Random.nextInt(5)
            ViewModel.PlayerCharacters[0].str+=randomNumber
            string+=("<Str+"+randomNumber+", ")

            randomNumber = 1+Random.nextInt(5)
            ViewModel.PlayerCharacters[0].vit+=randomNumber
            string+=("Vit+"+randomNumber+", ")

            randomNumber = 2+Random.nextInt(5)
            ViewModel.PlayerCharacters[0].agi+=randomNumber
            string+=("Agi+"+randomNumber+", ")

            randomNumber = 3+Random.nextInt(5)
            ViewModel.PlayerCharacters[0].dex+=randomNumber
            string+=("Dex+"+randomNumber+", ")

            randomNumber = 4+Random.nextInt(5)
            ViewModel.PlayerCharacters[0].int+=randomNumber
            string+=("Int+"+randomNumber+". You are now Level 1.>\n")
            ViewModel.PlayerCharacters[0].level+=1

            ViewModel.PlayerCharacters[0].inventoryItems[linen_clothes] = 1
            ViewModel.PlayerCharacters[0].inventoryItems[leather_boots] = 1

            ViewModel.storyLines[62+ViewModel.storyPushBack] = string
        }

        if(ViewModel.storyIndex == 67+ViewModel.storyPushBack) // Type choice 2
        {
            buttonChoice1.text = "You rushed yourself, took your bag and breakfast and ran to school."
            buttonChoice2.text = "You stay calm as you can, organize yourself nice and neat, then go out to eat breakfast first."

            buttonNext.visibility = View.INVISIBLE
            buttonChoice1.visibility = View.VISIBLE
            buttonChoice2.visibility = View.VISIBLE

            ViewModel.storyButtontag[5] =
                "You rushed yourself, took your bag and breakfast and ran to school."
            ViewModel.storyButtontag[6] =
                "You stay calm as you can, organize yourself nice and neat, then go out to eat breakfast first."

            ViewModel.storyButtonVisible[4] = false
            ViewModel.storyButtonVisible[5] = true
            ViewModel.storyButtonVisible[6] = true
        }

        // Stage Change section
        if(ViewModel.storyIndex == 35+ViewModel.storyPushBack) // Character Creation - Finish
        {
            buttonCharacter.visibility = View.VISIBLE
            buttonSkill.visibility = View.VISIBLE
            buttonInventory.visibility = View.VISIBLE

            ViewModel.storyKeyFlags[3] = true

            ViewModel.storyButtonVisible[0] = true
            ViewModel.storyButtonVisible[1] = true
            ViewModel.storyButtonVisible[2] = true

            // Add item test
            /*
            ViewModel.PlayerCharacters[0].inventoryItems[BlankBook] = 1
            ViewModel.storyLines[36 + ViewModel.storyPushBack] = "Book Added\n"
            */

            // Refresh
            ViewModel.storyLines[40 + ViewModel.storyPushBack] = "You, "+ ViewModel.PlayerCharacters[0].name+", are born into this world.\n"

            if(ViewModel.PlayerCharacters[0].gender == "Male")
            {
                ViewModel.storyLines[43 + ViewModel.storyPushBack] = "Nun:\"Mr.Forgar, please don’t panic, both the mother and child are safe. He is a healthy boy, just like the Oracle said he will be.\"\n"
                ViewModel.storyLines[49 + ViewModel.storyPushBack] = "Loyd:\"No worries sister, I accept the fate. If this is what the fate of my child is, his name will be " + ViewModel.PlayerCharacters[0].name + " as Oracle stated.\"\n"
            }
            else
            {
                ViewModel.storyLines[43 + ViewModel.storyPushBack] = "Nun:\"Mr.Forgar, please don’t panic, both the mother and child are safe. She is a healthy girl, just like the Oracle said he will be.\"\n"
                ViewModel.storyLines[49 + ViewModel.storyPushBack] = "Loyd:\"No worries sister, I accept the fate. If this is what the fate of my child is, her name will be " + ViewModel.PlayerCharacters[0].name + " as Oracle stated.\"\n"
            }

            ViewModel.currentTxt = ViewModel.storyLines[ViewModel.storyIndex]
            setupStoryViewListeners()
        }
    }

    buttonChoice1.setOnClickListener {
        // Answer section
        if(ViewModel.storyIndex == 17) // Type choice 1 -- Answer
        {
            ViewModel.storyLines[18+ViewModel.storyPushBack] = "You nervously laughed thinking this must be a joke of some sort.\n"
            ViewModel.storyLines.add(19+ViewModel.storyPushBack, "The orb did not stop at your nervous laughing face, it continued its statement.\n")
            ViewModel.storyPushBack+=1

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

        if(ViewModel.storyIndex == 21+ViewModel.storyPushBack) // Accept offer
        {
            ViewModel.storyLines[22+ViewModel.storyPushBack] = "You accepted the offer.\n"

            buttonNext.visibility = View.VISIBLE
            buttonChoice1.visibility = View.INVISIBLE
            buttonChoice2.visibility = View.INVISIBLE

            ViewModel.storyButtonVisible[4] = true
            ViewModel.storyButtonVisible[5] = false
            ViewModel.storyButtonVisible[6] = false
        }

        if(ViewModel.storyIndex == 27 + ViewModel.storyPushBack) // Character Gender
        {
            ViewModel.storyLines[28+ViewModel.storyPushBack] = "You choose to be a Male.\n"

            ViewModel.PlayerCharacters[0].gender = "Male"

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

        if(ViewModel.storyIndex == 67+ViewModel.storyPushBack) // Type choice 2
        {
            ViewModel.storyLines[68+ViewModel.storyPushBack] = "You rushed yourself, only keeping the basic organization.\n"
            ViewModel.storyLines.add(69+ViewModel.storyPushBack, "You carry your shoulder bag that holds the textbooks and grab the breakfast that Yoru had prepared you on your way out.\n")
            ViewModel.storyLines.add(70+ViewModel.storyPushBack, "As you dash out the door, you think you vaguely heard something, but it was not enough to catch your attention.\n")
            ViewModel.storyPushBack+=2


            buttonNext.visibility = View.VISIBLE
            buttonChoice1.visibility = View.INVISIBLE
            buttonChoice2.visibility = View.INVISIBLE

            ViewModel.storyButtonVisible[4] = true
            ViewModel.storyButtonVisible[5] = false
            ViewModel.storyButtonVisible[6] = false
        }

        /*
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
*/
        // Continue the story
        ViewModel.storyIndex++

        var currentTxt = storyTextView.text.toString()

        var newText = "${currentTxt}\n${ViewModel.storyLines[ViewModel.storyIndex]}"

        storyTextView.text = newText

        scrollView.post {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }

        // Question section
        /*
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
        }*/
    }

    buttonChoice2.setOnClickListener {
        // Answer section
        if(ViewModel.storyIndex == 17)
        {
            ViewModel.storyLines[18+ViewModel.storyPushBack] = "You jokingly questioned how could a corpse be conscious like you are\n"
            ViewModel.storyLines.add(19+ViewModel.storyPushBack, "The orb silenced for seconds, then replied to you.\n")
            ViewModel.storyLines.add(20+ViewModel.storyPushBack, "Orb:\"Answer – your current state is merely a soul, thus you still have consciousness before your souls also disperse into fragments.\"\n")
            ViewModel.storyPushBack+=2

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

        if(!ViewModel.storyKeyFlags[0]) // if first declined offer
        {
            if(ViewModel.storyIndex == 21+ViewModel.storyPushBack) //
            {
                ViewModel.storyLines[22+ViewModel.storyPushBack] = "You Declined the offer.\n"
                ViewModel.storyLines.add(23+ViewModel.storyPushBack, "The moment you decline the offer, with a flash boom, your head turns dizzy, and your consciousness fades away.\n")
                ViewModel.storyLines.add(24+ViewModel.storyPushBack,"Before you get to change your mind and say anything, you see a bright light that blinds you from soul to mind…..\n")
                ViewModel.storyLines.add(25+ViewModel.storyPushBack,"Then you realize, you woke up from the bed.\n")
                ViewModel.storyLines.add(26+ViewModel.storyPushBack,"Without remembering anything about the rough 20 minutes of sleep, you walked out with a tiring body that would collapse any second.\n")
                ViewModel.storyLines.add(27+ViewModel.storyPushBack,"You went to your normal day, and at one point, you sat on the chair and lay on the table.....\n")
                ViewModel.storyPushBack+=5

                buttonNext.visibility = View.VISIBLE
                buttonChoice1.visibility = View.INVISIBLE
                buttonChoice2.visibility = View.INVISIBLE
                buttonChoice3.visibility = View.INVISIBLE
                buttonChoice4.visibility = View.INVISIBLE

                ViewModel.storyKeyFlags[1] = true

                ViewModel.storyButtonVisible[4] = true
                ViewModel.storyButtonVisible[5] = false
                ViewModel.storyButtonVisible[6] = false
                ViewModel.storyButtonVisible[7] = false
                ViewModel.storyButtonVisible[8] = false
            }
        }
        else
        {
            if(ViewModel.storyIndex == 21+ViewModel.storyPushBack) //
            {
                ViewModel.storyLines[22+ViewModel.storyPushBack] = "You feel the scene being awkwardly familiar.\n"
                ViewModel.storyLines.add(23+ViewModel.storyPushBack, "Out of some sort of instinct, you choose to accept offer -- This time.\n")
                ViewModel.storyPushBack+=1

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

        if(ViewModel.storyIndex == 27 + ViewModel.storyPushBack) // Character Gender
        {
            ViewModel.storyLines[28+ViewModel.storyPushBack] = "You choose to be a Female.\n"

            ViewModel.PlayerCharacters[0].gender = "Female"
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

        if(ViewModel.storyIndex == 67+ViewModel.storyPushBack) // Type choice 2
        {
            ViewModel.storyLines[68+ViewModel.storyPushBack] = "You stay calm as you can, organize yourself nice and neat.\n"
            ViewModel.storyLines.add(69+ViewModel.storyPushBack, "You walked out the room, eating the breakfast Yoru had prepared you as you sit at the table.\n")
            ViewModel.storyLines.add(70+ViewModel.storyPushBack, "As you are eating, Yoru came out of kitchen.\n")
            ViewModel.storyLines.add(71+ViewModel.storyPushBack, "Yoru:\"Oh dear, I am glad you haven't left yet. It look like it will rain today, don't to bring your rainwears.\"\n")
            ViewModel.storyLines.add(72+ViewModel.storyPushBack, "Yoru handed you a raincoat made of furs\n")
            ViewModel.storyLines.add(73+ViewModel.storyPushBack, "You took the raincoat and put it in your bag as you leave the house, heading towards school.\n")
            ViewModel.storyLines.add(74+ViewModel.storyPushBack, "<Rain Coat +1>\n")
            ViewModel.storyPushBack+=6

            ViewModel.PlayerCharacters[0].inventoryItems[rain_coat] = 1

            buttonNext.visibility = View.VISIBLE
            buttonChoice1.visibility = View.INVISIBLE
            buttonChoice2.visibility = View.INVISIBLE

            ViewModel.storyButtonVisible[4] = true
            ViewModel.storyButtonVisible[5] = false
            ViewModel.storyButtonVisible[6] = false
        }

        /*
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
        }*/

        // Continue the story
        ViewModel.storyIndex++

        var currentTxt = storyTextView.text.toString()

        var newText = "${currentTxt}\n${ViewModel.storyLines[ViewModel.storyIndex]}"

        storyTextView.text = newText

        scrollView.post {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }

        // Question section
        /*
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
        }*/
    }

    buttonChoice3.setOnClickListener {
        // Answer section
        if(ViewModel.storyIndex == 17)
        {
            ViewModel.storyLines[18+ViewModel.storyPushBack] = "You were terrified from this news and ask for any other way to stay alive\n"
            ViewModel.storyLines.add(19+ViewModel.storyPushBack, "The white glowing orb flickered a few times.\n")
            ViewModel.storyLines.add(20+ViewModel.storyPushBack, "Orb: \"Answer – there is no way of returning to your world.\"\n")
            ViewModel.storyPushBack+=2

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

        if(ViewModel.storyIndex == 27 + ViewModel.storyPushBack) // Character Gender
        {
            ViewModel.storyLines[28+ViewModel.storyPushBack] = "Orb:\"Well then, we will let fate choose your destiny.\"\n"

            val randomNumber = Random.nextInt(2)

            if(randomNumber == 0)
            {
                ViewModel.PlayerCharacters[0].gender = "Male"
            }
            else
            {
                ViewModel.PlayerCharacters[0].gender = "Female"
            }

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

        /*
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
        }*/

        // Continue the story
        ViewModel.storyIndex++

        var currentTxt = storyTextView.text.toString()

        var newText = "${currentTxt}\n${ViewModel.storyLines[ViewModel.storyIndex]}"

        storyTextView.text = newText

        scrollView.post {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }

        // Question section
        /*if(ViewModel.storyIndex == 7) // Character Creation - Choose Weapon
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
        }*/
    }

    buttonChoice4.setOnClickListener {
        // Answer section
        if(ViewModel.storyIndex == 17+ViewModel.storyPushBack)
        {
            ViewModel.storyLines[18+ViewModel.storyPushBack] = "You angrily spit at the orb and didn’t believe a single word\n"
            ViewModel.storyLines.add(19+ViewModel.storyPushBack, "The orb stayed silent until your anger was let out, until reality finally crashed onto you and made you silenced yourself.\n")
            ViewModel.storyPushBack+=1

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

        if(ViewModel.storyIndex == 27 + ViewModel.storyPushBack) // Character Gender
        {
            ViewModel.storyLines[28+ViewModel.storyPushBack] = "Orb:\"....Uncooperative, is also a choice.\"\n"

            val randomNumber = Random.nextInt(2)

            if(randomNumber == 0)
            {
                ViewModel.PlayerCharacters[0].gender = "Male"
            }
            else
            {
                ViewModel.PlayerCharacters[0].gender = "Female"
            }

            buttonNext.visibility = View.VISIBLE
            buttonChoice1.visibility = View.INVISIBLE
            buttonChoice2.visibility = View.INVISIBLE
            buttonChoice3.visibility = View.INVISIBLE
            buttonChoice4.visibility = View.INVISIBLE

            ViewModel.storyKeyFlags[2] = true
            ViewModel.PlayerCharacters[0].luc -= 2

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
        /*
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
        }*/
    }
}