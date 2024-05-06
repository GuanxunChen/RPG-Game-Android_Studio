package com.example.rpg

import android.view.View
import android.widget.Button

internal fun MainActivity.setupInCityViewListeners() {
    val buttonFarm = findViewById<Button>(R.id.ButtonFarm)
    val buttonForest = findViewById<Button>(R.id.ButtonForest)
    val buttonHome = findViewById<Button>(R.id.ButtonHome)
    val buttonSchool = findViewById<Button>(R.id.ButtonSchool)
    val buttonShop = findViewById<Button>(R.id.ButtonShop)
    val buttonAsterHouse = findViewById<Button>(R.id.ButtonAsterHouse)
    val buttonMarthHouse = findViewById<Button>(R.id.ButtonMarthHouse)
    val buttonTownSquare = findViewById<Button>(R.id.ButtonTownSquare)

    if(!ViewModel.storyKeyFlags[5]){buttonAsterHouse.visibility = View.INVISIBLE} else {buttonAsterHouse.visibility = View.VISIBLE}
    if(!ViewModel.storyKeyFlags[8]){buttonMarthHouse.visibility = View.INVISIBLE} else {buttonMarthHouse.visibility = View.VISIBLE}

    buttonFarm.setOnClickListener {

    }
    buttonForest.setOnClickListener {

    }
    buttonHome.setOnClickListener {

    }
    buttonSchool.setOnClickListener {

    }
    buttonShop.setOnClickListener {

    }
    buttonAsterHouse.setOnClickListener {

    }
    buttonMarthHouse.setOnClickListener {

    }
    buttonTownSquare.setOnClickListener {

    }
}