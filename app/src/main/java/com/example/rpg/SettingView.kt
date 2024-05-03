package com.example.rpg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

internal fun MainActivity.setupSettingViewListeners(flag:Boolean) {
    val buttonBack = findViewById<Button>(R.id.button21)
    val buttonGraphic = findViewById<Button>(R.id.button20)
    val buttonAudio = findViewById<Button>(R.id.button19)
    val buttonControl = findViewById<Button>(R.id.button18)
    val buttonMainMenu = findViewById<Button>(R.id.button17)
    val buttonQuit = findViewById<Button>(R.id.button16)
    val buttonSave = findViewById<Button>(R.id.button22)

    // Visibility
    if (!flag){buttonSave.visibility = View.INVISIBLE}
    else{buttonSave.visibility = View.VISIBLE}

    // Back Button
    buttonBack.setOnClickListener {
        if(!flag){
            setContentView(R.layout.mainmenu)
            MainActivity()
        }
        else{
            setContentView(R.layout.storyview)
            setupStoryViewListeners()
        }
    }

    // MainMenu
    buttonMainMenu.setOnClickListener {
        setContentView(R.layout.mainmenu)
        MainActivity()
    }

    // Quit
    buttonQuit.setOnClickListener {
        finish()
    }

    buttonGraphic.setOnClickListener {
        //
    }
    buttonAudio.setOnClickListener {
        //
    }
    buttonControl.setOnClickListener {
        //
    }
    buttonSave.setOnClickListener {
        //
    }
}