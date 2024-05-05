package com.example.rpg

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import java.io.File

internal fun MainActivity.setupSettingViewListeners(flag:Boolean) {
    val buttonBack = findViewById<Button>(R.id.button21)
    val buttonGraphic = findViewById<Button>(R.id.button20)
    val buttonAudio = findViewById<Button>(R.id.button19)
    val buttonControl = findViewById<Button>(R.id.button18)
    val buttonMainMenu = findViewById<Button>(R.id.button17)
    val buttonQuit = findViewById<Button>(R.id.button16)
    val buttonSave = findViewById<Button>(R.id.button22)

    lateinit var dataSaver: DataSaver

    val savetxt0 = findViewById<TextView>(R.id.saveText0)
    val savetxt1 = findViewById<TextView>(R.id.saveText1)
    val savetxt2 = findViewById<TextView>(R.id.saveText2)
    val savetxt3 = findViewById<TextView>(R.id.saveText3)
    val savetxt4 = findViewById<TextView>(R.id.saveText4)
    val savetxt5 = findViewById<TextView>(R.id.saveText5)
    val savetxt6 = findViewById<TextView>(R.id.saveText6)
    val savetxt7 = findViewById<TextView>(R.id.saveText7)

    val saveButton0 = findViewById<Button>(R.id.save0)
    val saveButton1 = findViewById<Button>(R.id.save1)
    val saveButton2 = findViewById<Button>(R.id.save2)
    val saveButton3 = findViewById<Button>(R.id.save3)
    val saveButton4 = findViewById<Button>(R.id.save4)
    val saveButton5 = findViewById<Button>(R.id.save5)
    val saveButton6 = findViewById<Button>(R.id.save6)
    val saveButton7 = findViewById<Button>(R.id.save7)


    val delButton0 = findViewById<Button>(R.id.delete0)
    val delButton1 = findViewById<Button>(R.id.delete1)
    val delButton2 = findViewById<Button>(R.id.delete2)
    val delButton3 = findViewById<Button>(R.id.delete3)
    val delButton4 = findViewById<Button>(R.id.delete4)
    val delButton5 = findViewById<Button>(R.id.delete5)
    val delButton6 = findViewById<Button>(R.id.delete6)
    val delButton7 = findViewById<Button>(R.id.delete7)

    // Visibility
    if (!flag || !ViewModel.storyKeyFlags[3]){buttonSave.visibility = View.INVISIBLE}
    else{buttonSave.visibility = View.VISIBLE}

    if (savetxt0.text == "----------None----------"){delButton0.visibility = View.INVISIBLE} else{delButton0.visibility = View.VISIBLE}
    if (savetxt1.text == "----------None----------"){delButton1.visibility = View.INVISIBLE} else{delButton1.visibility = View.VISIBLE}
    if (savetxt2.text == "----------None----------"){delButton2.visibility = View.INVISIBLE} else{delButton2.visibility = View.VISIBLE}
    if (savetxt3.text == "----------None----------"){delButton3.visibility = View.INVISIBLE} else{delButton3.visibility = View.VISIBLE}
    if (savetxt4.text == "----------None----------"){delButton4.visibility = View.INVISIBLE} else{delButton4.visibility = View.VISIBLE}
    if (savetxt5.text == "----------None----------"){delButton5.visibility = View.INVISIBLE} else{delButton5.visibility = View.VISIBLE}
    if (savetxt6.text == "----------None----------"){delButton6.visibility = View.INVISIBLE} else{delButton6.visibility = View.VISIBLE}
    if (savetxt7.text == "----------None----------"){delButton7.visibility = View.INVISIBLE} else{delButton7.visibility = View.VISIBLE}

    // Back Button
    buttonBack.setOnClickListener {
        if(!flag){
            setContentView(R.layout.mainmenu)
            MainActivity()
        }
        else{
            setContentView(R.layout.storyview)
            setupStoryViewListeners()
            for (x in ViewModel.storyLines.indices) {
                println("$x ${ViewModel.storyLines[x]}\n")
            }
        }
        println(ViewModel.storyIndex)
        println(ViewModel.storyPushBack)
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

    saveButton0.setOnClickListener {
        dataSaver = DataSaver("R0.ser")
        //savetxt0.text  = getLastModifiedDate("R0.ser")
        dataSaver.saveData(ViewModel)
    }
    saveButton1.setOnClickListener {
        dataSaver = DataSaver("R1.ser")
        dataSaver.saveData(ViewModel)
    }
    saveButton2.setOnClickListener {
        dataSaver = DataSaver("R2.ser")
        dataSaver.saveData(ViewModel)
    }
    saveButton3.setOnClickListener {
        dataSaver = DataSaver("R3.ser")
        dataSaver.saveData(ViewModel)
    }
    saveButton4.setOnClickListener {
        dataSaver = DataSaver("R4.ser")
        dataSaver.saveData(ViewModel)
    }
    saveButton5.setOnClickListener {
        dataSaver = DataSaver("R5.ser")
        dataSaver.saveData(ViewModel)
    }
    saveButton6.setOnClickListener {
        dataSaver = DataSaver("R6.ser")
        dataSaver.saveData(ViewModel)
    }
    saveButton7.setOnClickListener {
        dataSaver = DataSaver("R7.ser")
        dataSaver.saveData(ViewModel)
    }

    delButton0.setOnClickListener {
        deleteFile("R0.ser")
    }

    delButton1.setOnClickListener {
        deleteFile("R1.ser")
    }

    delButton2.setOnClickListener {
        deleteFile("R2.ser")
    }

    delButton3.setOnClickListener {
        deleteFile("R3.ser")
    }

    delButton4.setOnClickListener {
        deleteFile("R4.ser")
    }

    delButton5.setOnClickListener {
        deleteFile("R5.ser")
    }

    delButton6.setOnClickListener {
        deleteFile("R6.ser")
    }

    delButton7.setOnClickListener {
        deleteFile("R7.ser")
    }
}

class DataSaver(private val fileName: String) {
    fun saveData(data: ViewModel) {
        try {
            val fileOut = FileOutputStream(fileName) // Use fileName parameter here
            val objectOut = ObjectOutputStream(fileOut)
            objectOut.writeObject(data)
            objectOut.close()
            fileOut.close()
            println("Data saved successfully.")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

fun deleteFile(fileName: String): Boolean {
    val file = File(fileName)
    return file.delete()
}