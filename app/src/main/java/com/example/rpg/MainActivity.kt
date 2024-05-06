package com.example.rpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import android.widget.Button
import android.media.MediaPlayer

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
    var skillUpgradePoints: Int = 1
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
        "Breath quickens, blood is pumping....Within every second, you can feel your body weakening.\n",//5
        "And when your hand finally reaches for your heart, it is all too late.\n",//6
        "As if there’s a lightning that suddenly struck you, you moan in agony and pain.\n",//7
        "Your consciousness fades away alone with the burning feeling, and fragments of thoughts slowly sink down into the eternal darkness.\n",//8
        "",//9 flash image
        "When you regain your consciousness, in front of your eyes floats a large glowing orb as big as a truck.\n",//10 Character Creation Finish
        "You were stunned, not sure what you are looking at and why you are here.\n",
        "But before you got to do anything, a robotic voice came from the orb, it speaks.\n",
        "Orb:\"Connection with the subject is established. Now loading the Introduction module…..\"\n",
        "Orb:\"Loaded.\"\n",
        "Orb:\"Welcome to the Room of Rebirth. Foreigners, we are sorry to inform you that you have died in your original world.\"\n",//15
        "Orb:\"The reason for your death seems to be a sudden heart attack, likely due to your inconsistent sleeping hours.\"\n ",
        "Hearing the words, you try to dive back in your memory, yet only to find your memory missing and incomplete. So you...\n",// Choice 1
        "",//18 User Input
        "Orb:\"As much as you may be concerned, the reason you are here is a coincidence.... Your soul collided with the System of Reincarnation while we were traveling by your dimension.\"\n",
        "Orb:\"For that, we would like to reincarnate you into another world. However, you do have your own choice.\"\n",//20
        "Orb:\"Question – Would you like to accept the offer?\"\n",
        "",//22 User Input
        "Orb:\"Good….. Now, for your well being, we would like to inform you of the following.\"\n",
        "Orb:\"The world you are reincarnating to is a world of sword and magic. Monsters and different mythical creatures exist, but humans are still the dominant race….Yet it does not mean you are safe from danger, so please keep that in mind.\"\n",
        "Orb:\"Now, before your reincarnation begins, there is one last thing.\"\n",
        "Orb:\"Although we don’t have much authority to interfere with that world, yet we can at least let you choose your own name and gender…..\"\n",
        "Orb:\"Question – What would you like to be, male or female?\"\n",
        "",//28 User Input
        "Orb:\"....Let us proceed.\"\n",
        "Orb:\"Question – What would you like your name to be?\"\n",
        "",//31 Input Space
        "",//32 User Input
        "After you’ve made your decision, without giving you a chance to speak again, your vision starts to blur.\n",
        "The surrounding fades, then brightens once again.\n",// Character Creation Complete, click next will be scene change
        "This, is a big town named Britangila.\n",//35 Scene Changed
        "Britangila is located at the border of the Falion Empire, its economy heavily relies on the local farmers and miners, and every year, Lord Eliwood would host a Harvest Festival to thank the Goddess Chauntea for her blessing.\n",
        "Everyone in the town of Britangila is supposed to be happy, drinking and dancing for the whole day.\n",
        "But this year… Instead of joining the festival, Loyd stood in his living room, walking around impatiently, waiting for good news.\n",
        "While droplets of sweat slid down against his face, a loud cry of a baby burst from the room next door.\n",
        "You, ___, are born into this world.\n",//40 name
        "A nun walks out of the room, and Loyd hurries his steps, going up towards the nun with a worried expression.\n",
        "Loyd:\"Sister, is – is my wife alright? What about my child?\"\n",
        "Nun:\"Mr.Forgar, please don’t panic, both the mother and child are safe, he/she is a healthy just like the Oracle said he/she will be.\"\n",//43 Gender dependent
        "Hearing the nun’s words, Loyd let out a sigh and took a few deep breaths, then he finally allowed the joy to take over him.\n",
        "Loyd:\"Oh thank you! Thank you Goddess Chauntea! And thank you all as well sisters, I can’t ever be able to express the amount of gratitude!\"\n",
        "Loyd bowed down towards the nun.\n",
        "Nun:\"Mr.Forgar, please raise your head. It is our duty to help to give birth to a new life. And also… About the oracle…\"\n",
        "The nun seems a little troubled, but Loyd simply shook his head.\n",
        "Loyd:\"No worries sister, I accept the fate. If this is what the fate of my child is, his/her name will be ___ as Oracle stated.\"\n",//49 Gender dependent
        "The nun looked relieved as she heard the words.\n",
        "Nun:\"Thank you for your understanding, Mr.Loyd. Goddess Chauntea would really appreciate your actions. May the Goddess bless you…\"\n",
        "As she said her words, she led Loyd into the room where another older nun held the newborn infant -- Which is you.\n",
        "But instead of coming to hold you, Loyd quickly walked up to the young woman who lies in bed, looking exhausted.\n",
        "Loyd:\"Yoru, how are you feeling?\"\n",
        "He holds his wife’s hand, looking worried.\n",//55
        "Yoru raises her arms and hugs Loyd, patting him on the back, calming the man down.\n",
        "And that, was your first sight of seeing your parents.\n",

        "Perhaps it is due to your body not yet grown, or brain not yet fully developed, you find yourself to have vague memories for the next few years, while having a much childish way of thinking and naive personality.\n",
        "Yet, even so, you are still much smarter than those who are at your age.\n", //59
        "By the age of 8, many neighbors and some residents of this town had known your name. Those who met you had even considered you being a genius from your math skills and modern perspectives of looking at things.\n",
        "As your memories start to grow much more clear over time, you are becoming more and more like ‘you’.\n",
        "",//62 Level up, stat shows
        "You get up from the bed.\n",
        "Hearing the howling winds, you glance outside the window and see the gray sky.\n",
        "But the horrible weather did not seem to cloud your good mood, as it is the first day of school, you are looking forward to making some new friends.\n",
        "After all, humans are the type of race that require some social activities with those who are in their age group, and you are no exception…\n",
        "",//67 User Input
        "By the time you reached school, the rain hadn't yet fallen from the sky.\n",
        "You read the signs, guiding yourself to the assigned classroom.\n",
        "As you open the door, you are surprised to find that you weren’t the first one here.\n",//70
        "Near the back of the room, a young girl sits at a desk, reading a book that you have never known about.\n",
        "She has long beautiful golden hair that is braided with French Braid style; Her blue eyes are bright as sapphire even in this dim surrounding; The white dress brings out the purity and cuteness within her.\n",
        "While you look at her, she soon raises her head and looks back at you – it seems like your sight caught her attention.\n",
        "The girl looked you over from top to down, tip to toe, then smiled.\n",
        "Girl:“....You are early, very early. It is about half an hour before the starting time of class. Nobody is here yet.”\n",
        "",//76 User Input

        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]",
        "Unfinished [4]")

    var OriginStoryLines: MutableList<String> = mutableListOf

    var storyKeyFlags = mutableListOf(
        false, false,// decline offer of reincarnation
        false,// uncooperative during reincarnation stage
        false, // prevent raincoat error
        false, false, // Met Aster and become friends and invited
        false,
        false, false, // Met Marth and become friends and invited
        false, //
        false,false,false,false, // 4 Choice Button
        false,false // Char name
    )

    var aggressiveness: Int = 0
    var passiveness: Int = 0
    var assertiveness: Int = 0
    var passiveAgressiveness: Int = 0

    var currentTxt = "" // the current line of story, usually used to save text for view jumping
    var storyIndex: Int = 0 // which line is the story currently on
    var storyPushBack: Int = 0 // how much line was inserted into story

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
        PlayerCharacter(true,"Hero", "Male","Peasant","","",0,
            1,1,1,1,5,10,0),
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
    private lateinit var bgm: MediaPlayer
    // Start up menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewModel = ViewModelProvider(this).get(StoryData::class.java)

        // Main Menu
        setContentView(R.layout.mainmenu)

        // Initialize audio file
        bgm = MediaPlayer.create(this, R.raw.bgm1)
        bgm.isLooping = true
        bgm.start()

        //starting gear
        /*
        ViewModel.PlayerCharacters[0].inventoryItems[iron_sword] = 1
        ViewModel.PlayerCharacters[0].inventoryItems[wand] = 1
        ViewModel.PlayerCharacters[0].inventoryItems[leather_cap] = 1
        ViewModel.PlayerCharacters[0].inventoryItems[leather_armor] = 1
        ViewModel.PlayerCharacters[0].inventoryItems[leather_boots] = 1
        ViewModel.PlayerCharacters[0].inventoryItems[potion] = 3
        ViewModel.PlayerCharacters[0].inventoryItems[elixer] = 3*/

        val buttonStart = findViewById<Button>(R.id.Start)
        val buttonContinue = findViewById<Button>(R.id.Continue)
        val buttonSetting = findViewById<Button>(R.id.Setting)
        val buttonQuit = findViewById<Button>(R.id.Quit)

        buttonStart.setOnClickListener {
            bgm.release()
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