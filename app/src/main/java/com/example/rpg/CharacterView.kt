package com.example.rpg

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

internal fun MainActivity.setupCharacterViewListeners() {
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