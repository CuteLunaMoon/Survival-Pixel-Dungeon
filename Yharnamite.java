/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;


import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Sword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.Dart;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.YharnamiteSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndCaptain;

import com.shatteredpixel.shatteredpixeldungeon.windows.WndQuest;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndQuestGascoigne;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Yharnamite extends NPC {

	{
		spriteClass = YharnamiteSprite.class;

		properties.add(Property.IMMOVABLE);
	}

	public  int doorType; //1 = candle, 2 = lantern
	public  int doorType(){
		return  doorType;
	}
	public  String DOORTYPE = "doorType";

//TODO: questlines for these shut-in NPCs

	public String[] TALK_TXT ={
//(0) Hungry folks
	"Oh, are you an adventurer? May I ask you a favor? My family is running out of food. And we dare not go out on a night like this. So could you bring us 5 _rations of foood_ ? Thank you, and please becareful, kind adventurer.",

//(1) Female NPC with no quest #1	
	"Entschuldigen Sie! Es ist niemand zu Hause... ",  

//(2) Gasscoigne's family 1st encounter
	" Hmm? Who's that? Daddy? Mommy? Oh no. It's not mommy... I'm very sorry, my mom told me not to talk to strangers...",

//(3) Rude unnamed male NPC #1	
	"Not from around here, are ya? An outsider, on the night of the hunt? You must be sick, mate. Get away from me now, go.",

//(4) Female NPC with no quest #2	
	"Ohh, I'm terribly sorry... There's no one home... I'm really, really sorry... So sorry for ya...",

//(5) Yharnam brothel, encounter as male
" Your husband isn't here. I'm off during hunts!... Oh, I thought you were some angry housewives. I'm off during hunts. But if you are still...thirsty, darling. Come back in the morning.",

//(6) Zealot
	" Bless us with blood, bless us with blood... Hmm? Who's that? Get out of my door step you impudent lout.",


//(7) Unnamed male NPC #1 GIVE SWORD
	"Lousy offcomer. I'll have no business with anyone while the hunt's on. Good luck staying alive till morning",

//(8) Deceiver
	"Oh, hello there. I'm so sorry I can't open this door. But wait, wait a minute. Do you have Blood vials? If you bring me some of those sweet nectars, I think I'll even let you in. It's so cold and lonely in here for a maiden for me to handle...",


//(9) Rude unnamed male NPC #2
	" Wretched outsider. I hope they have your head before the morning",

//(11) Unnamed male NPC #2
	" What's that? ...I...I can't help you, apologies. Good night and good luck",

//(12) Unnamed male NPC #3 
	 "Niemand ist hier krank. Uns geht es gut... Gute Jagd.",

//(13) Deutch Gentlemen
	"ver bist du? An ouseiter? Nein, nein... Vee don't hafe anythink to do vith ya, trot along, vill ya?",

//(14) Rude Female NPC #1	
	"What is it? I smell that... You must be a hunter. And not one of ours, either. Well, get lost, and don't come back!",

//(15)	the rude archer
	" Sick creature, don't pretend you are normal. Away with you, now!",

//(16)  Rude Female NPC #2	
	"Get lost! Oh, help me, gods...",

//(17) 	Pixelmart
	"Oh hello, weary traveller. Yes, you have come to the right place. This is Pixelmart. Well, we are off during hunts and I can't let you in, but if you want to buy or sell anything, just tell me. Note that we can only trade goods that fits through this privacy window. ",
	
//(18)  Unnamed male NPC #4	
	"What's zat? Oh, we're fine here mate. All's in tip top shape, not even a sniff of cold. Gute Jagd.",};
	
	
	
	public String[] TALK_TXT2 ={
//(0) Hungry Folks 2nd txt
		" Any luck finding the rations, kind /s?",

//(1) Female NPC with no quest #1
		"_No response_",

//(2) Gascoigne's daughter
	"Is it you again? And you are an adventurer? Mm... Then, please, can I ask you a small kindness? My dad went to Loran for the hunt, and rarely came back on his own so my mum and grand dad went to find him, and now they are gone, too. Can you please look for them? I'm home with my sister but we are so scared.",

//(3) Rude unnamed NPC #1
	"You again? Get out of my sight, you sicko, or I'll sic guards on you.", 
	
//(4) Female NPC with no quest #2	
	" I said there was nobody home. And we can't help you... Please, get away from us...",

//(5) Yharnam brothel, encounter as male
	"Oh, you seem desperate... But I'm off during hunts. Come back in the morning, hun.",

//(6) Zealot
	" Ia! Ia! Great Yog-Sothoth! Y'AI'NG'NGAHYOG-SOTHOTHH'EE-L'GEBF'AI THRODOGUAAAHH...",


//(7) Unnamed male NPC #1 ==> Give Short Sword +1
	" Tsk... You again? What do you want this time? I can't open this door. It's too dangerous for my family. And you better go hiding now before they find you... Hey! Wait a minute...Tsk... I can't let you go like this... Take this sword, I have little use of it for now. At night like this, the Eastern quarter is boarded up, you should try the canal system. ",

//(8) Deceiver
	"Oh, hello there. I'm so sorry I can't open this door. But wait, wait a minute. Do you have Blood vials? If you bring me some of those sweet nectars, I think I'll even let you in. It's so cold and lonely in here for a maiden for me to handle...",


//Rude unnamed male NPC #2
	" Wretched outsider. Go feed yourself to a beast!",

// Unnamed male NPC #2
	" Poor you. Stuck outside on the night of the hunt...But I-I can't open this door. Forgive me. Good night and good luck",

// Unnamed male NPC #3 
	"Niemand ist hier krank. Uns geht es gut... Gute Jagd.",

// Deutch Gentlemen ==> Give 10x Throwing Daggers
	"Vat iss it? You again? ... *sigh*... Es tut mir leid, dass ich fruher unhoflich war. But I Kan't open zis door... Take zese, and viel gluck shtayink alife till dawn. Now, go!",

//Rude Female NPC #1		
	" Stay away from my door step, hunter. You are not wanted here.",

//the rude archer
	" Trying to fool me to open this door? Ha, typical beast... Go before I put a bolt between your eyes",

//Rude Female NPC #2	
	"Oh gods please help me, I must live. Ia Ia Cthulhu fhtagn!",

//Pixelmart ==> DOES NOT HAVE DIALOGUE HERE, OPEN SPECIAL BUY/ SELL MENU INSTEAD
	"...",

//Unnamed male NPC #4	
	"Ia Ia. Shub-Niggurath! Zee Black Goat of zee voods vith a Thousand Young!",};
	

	
	
	public String[] TALK_TXT3 ={
//(0)Hungry folks end txt
	" Beware of the beast, kind /s",

//(1) Female NPC with no quest #1
	" _No response_ ",


//(2) Gasscoigne's family last encounter (given they have met)
	" Good luck, ",

//(3) Rude unnamed NPC #1 ==> YELL==> Trigger Scroll of Challenge
	" ALARM! ALARM! The outsider is over here!", 
	
//Female NPC with no quest #2	
	" _No response_ ",

//(5) Yharnam brothel, encounter as male
	"_No response_ ",

//(6) Yharnam brothel, encounter as female
	"  _No response_ ",


//(7) Unnamed male NPC #1
	" Sorry, but I can't open this door, not on the nights of the hunt. Go find somewhere else to hide.",
//(8) Deceiver
	"  Good luck staying alive till dawn. Ah ha ha ha. ",


//Rude unnamed male NPC #2
	" We're cursed. We're all cursed... This is the gods' judgement! Ah ha ha ha!",

// Unnamed male NPC #2 
	" Good luck staying alive till dawn breaks.",

// Unnamed male NPC #3 
	"Lobe die Kirche des guten Blutes. Gute Jagd.",


// Deutch Gentlemen ==> his family has left Loran
	" _No response_ ",

//Rude Female NPC #1		
	" Stay away from my door step, hunter. You are not wanted here.",

//the rude archer ==> YELL==> Shoot dart (similar to poison dart in bookshelves)
	" consider this a mercy kill",

//Rude Female NPC #2	
	"In his house at R'lyeh, dead Cthulhu waits dreaming. Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn.",

//Pixelmart ==> DOES NOT HAVE DIALOGUE HERE, OPEN SPECIAL BUY? SELL MENU INSTEAD
	"...",


//Unnamed male NPC #4	==> lewd tentacle, also increase 1 madness EVERY TIME YOU TALk TO THIS GUY. 
	"Ia Ia. Oh great brood-mother Shub-Niggurath, your apendages are so beautiful, my feeble mind kan barely komprehend it!...Ahhh... It feels so good... Zese godly kloud-like tentakle are so good... ",		
//Archibald
	"...",

	};


	// the folk who gives the sword
	public boolean swordGiven =false;

	
	//for hungry folks' quest
	public boolean foodQuestGiven =false;
	public boolean foodQuestComplete = false;

	
	public String QUEST_TXT_HUNGRY_FOLKS ="Oh, thank you, thank you! We haven't got a single dime here but we hope these scrolls can help you in your journey. Don't ask me, I don't know what they mean. Thank you alot, kind /s";
	


	// for Gascoigne's family quest
	public boolean acceptQuest = false;
	public boolean gooDefeated = false;
	public boolean metBeforeGoo = false; 
	public boolean potionGiven = false;


	public String[] QUEST_TXT_GASCOIGNE ={
//refuse
	"Oh, alright. Well, t-thanks mister for talking, at least. Please be careful out there.",

//refuse, female
	"Oh, alright. Well, t-thanks miss for talking, at least. Please be careful out there.",

// accept
	"Really? Oh, th-thank you! My grand dad is a hunter, too. He wear a very notable yellow buffcoat and carries a saw as a weapon, and he's somehow...very young, too. He looks even younger than my dad.",

//accepted quest, 2nd encounter
	"Have you found them, mister? My dad is very tall, he wears bandages over his eyes and he carries a very big axe. You won't miss it. Tonight is the night of the hunt, please be careful, mister /s.",

//acceptedquest, female 2nd encouter
	"Have you found them, miss? My dad is very tall, he wears bandages over his eyes and he carries a very big axe. You won't miss it. Tonight is the night of the hunt, please be careful, miss /s.",

//accepted, come back ater defeating Goo
	"Oh, you must be the adventurer my daughter talked to ealier. I'm her father. I was carried away by the hunt... Ha, ha. Fortunately my wife found me and brought me back. I don't reckon you from around here, a new adventurer, are ya?... Then, take this, as a gift to the new face... And don't ya dare wander to Loran... Tonight, there's something different in the air... Men leave as hunters, and return as beasts...Let there be no doubt. If it moves, you can be sure it's a beast. ...And even if it doesn't, well, don't take any chances! ",

//refused, come back after defeating Goo
	"Oh, you must be the adventurer my daughter talked to ealier. I'm her father. I was carried away by the hunt... Ha, ha. Fortunately my wife found me and brought me back. I don't reckon you from around here, a new adventurer, are ya? Tonight, there's something different in the air... Men leave as hunters, and return as beasts. ... ",

// didn't talk to the daughter before defeating Goo
	" Who are you? Hmm... A new adventurer, are ya? Well, we have no business with you. Off with you, now.",	

	};


	public void GascoigneFamilyQuest(){
		
		//if Goo is defeated, this quest immidiately ends.
		if(Quest.gooDefeated ){
			
			if(metBeforeGoo){
			// met before Goo
			if(acceptQuest){
			if(!potionGiven){
			tell(QUEST_TXT_GASCOIGNE[5]);
			//one free potion of might
				PotionOfMight potion_ = new PotionOfMight();
				if (potion_.doPickUp( Dungeon.hero )) {
												GLog.i( Messages.get(Dungeon.hero, "you_now_have", potion_.name()) );
											} else {
												Dungeon.level.drop( potion_, Dungeon.hero.pos ).sprite.drop();
											}
											potionGiven = true;
				}else{
				String k_ = TALK_TXT3[2] +Dungeon.hero.givenName();
				tell(k_);
				}
			
			}else{
				//met before Goo, refuse quest
				tell(QUEST_TXT_GASCOIGNE[6]);
			}
			}else{
				tell(QUEST_TXT_GASCOIGNE[7]);
			}
			
		}else{
		
			if(acceptQuest){
				
				if(Dungeon.hero.givenName() == "Huntress"){
				tell(QUEST_TXT_GASCOIGNE[4]);
				}else{
				tell(QUEST_TXT_GASCOIGNE[3]);
				}
				
			}else{
				GameScene.show(new WndQuestGascoigne(this));
				metBeforeGoo =true;
			}
		
		}

	}


	
	
	public void HUngryFamilyQuest(){
	
		if(!Quest.foodGiven){
			Food tokens = Dungeon.hero.belongings.getItem( Food.class );
			if(tokens!=null && (tokens.quantity() >= 3)){
				tell(QUEST_TXT_HUNGRY_FOLKS);
				Quest.foodGiven = true;
				ScrollOfUpgrade potion_ = new ScrollOfUpgrade();
				if (potion_.doPickUp( Dungeon.hero )) {
												GLog.i( Messages.get(Dungeon.hero, "you_now_have", potion_.name()) );
											} else {
												Dungeon.level.drop( potion_, Dungeon.hero.pos ).sprite.drop();
											}
				Item scroll_ = Generator.random( Generator.Category.SCROLL );
				if (scroll_.doPickUp( Dungeon.hero )) {
												GLog.i( Messages.get(Dungeon.hero, "you_now_have", scroll_.name()) );
											} else {
												Dungeon.level.drop( scroll_, Dungeon.hero.pos ).sprite.drop();
											}
			}else{
				tell(TALK_TXT2[0]);
			}
		}else{
			//complete mission
			tell("Thank you, good luck staying alive till dawn");

		}
	}

	
	//for deutch man quest
	public boolean scrollGiven =false;
	public boolean scrollQuestGiven =false;


	
	
	public int curHeroLevel =1;
	
	public String[] QUEST_TXT_DEUTCH_MAN ={
// 4rd encounter, gain 2 extra level to unlock this dialogue
	"Gut to see ya shtill alife. But it's not vise to valk around on zee nights of zee Jagd. I kan't open zis door for ya, but I'm villink to help, if there's anythink zat kan be done.",	

// talk about THE SCOURGE
	"Vee don't know how the plague of blut and biest made its vay to our city. It spreaded like vild feuer and soon zee Church recruited many to hunt biest. But sings got vorse and vorse after each jagd as hunters from the last hunt turned into beasts. Perhaps I vill mofe out of zis cursed city when I had zee chance.",

// talk about BLOOD HEALING
	"Oh, so you hafe heard of our micaculous blut. It heals everythink from burn to broken bone. And even Herpes. Magnificent, isn't it? Zee Church kontrol all aspects of blut heilung, so if you efer need one, go to one of zeir klinics.",

// rescue option 
	" Oh, you vant to help us? Zen, I'll graciously accept. Bring us 7 Scrolls of teleporations, vill ya? I hafe a aquaitainces at zee sewer system, she can help us from zere.",
// rescue quest in progress
	"Any luck, /s ?",
// rescue quest complete
	"Wow, das ist nett. Thank you, my friend. Thank you alot. Now vee vill leafe this accursed city. Take zese sings as our thanks, kind jager. Once again, thank you and gute Jagd.",

	};


	public void GermanManQuest(){


	}

	//for deceiver quest
	
	public boolean bloodGiven =false;
	
	public String[] QUEST_TXT_DECEIVER = {
// have no blood vials
	" Any luck finding the blood, brave /s?",

// have blood vials
	"What's that smell? Ummm... The sharp, sweet scent of blood... Yes, give it to me, hun.. Ah Heh, heh. Oh, the reek of blood. It sickens me... Ah... Oh... _ *More sensual moanings* _ ...  What? Let you in? Pfft... HAHAHA... What kind of follies would open their door on the nights of the hunt? Off with you now, ah ha haha.",	
// others
	" Go feed yourself to a beast!",

	};


	
	
	//public String[] MAD_TALK_TXT
	
	public int  number; // the number of this Yharnamite, max = 10, most of these NPCs are assign a number
	
	
	public int interactTime; // time PC has contacted this NPC.

	public  int number(){
		return  number;
	}

private static final String NUMBER = "number";
private static final String INTERACTTIME = "interactTime";
private static final String POTIONGIVEN = "potionGiven";
private static final String METBEFOREGOO = "metbeforeGoo";
private static final String ACCEPTEDQUEST = "acceptedQuest";



	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put(NUMBER, number);
		bundle.put(INTERACTTIME,interactTime);
		bundle.put(POTIONGIVEN,potionGiven);
		bundle.put(METBEFOREGOO,metBeforeGoo);
		bundle.put(ACCEPTEDQUEST,acceptQuest);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		number = bundle.getInt( NUMBER );
		interactTime =bundle.getInt( INTERACTTIME );
		//for Gascoigne's quest
		acceptQuest = bundle.getBoolean(ACCEPTEDQUEST);
		potionGiven = bundle.getBoolean(POTIONGIVEN);
		metBeforeGoo = bundle.getBoolean(METBEFOREGOO);

	}

	@Override
	protected boolean act() {
		
	
		
		throwItem();
		
		return super.act();
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return 10000;
	}
	
	@Override
	public void damage( int dmg, Object src ) {
	}
	
	@Override
	public void add( Buff buff ) {
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	


	
	@Override
	public boolean interact() {
		if(interactTime ==0){
		
		tell(TALK_TXT[number]);
		interactTime ++;
		
		}else{
			if(interactTime<5)
			interactTime ++;
			switch(number){
//Hungry folks
			case 0:{
			HUngryFamilyQuest();
			}break;

//Female NPC with no quest #1
			case 1:{ 
			//she tells nothing then
				if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				tell(TALK_TXT3[number]);
				}
				
			
			}break;

//Gascoigne's family
			case 2:{
				GascoigneFamilyQuest();
			}break;

//Rude unnamed NPC #1 ==> YELL==> Trigger Scroll of Challenge
			case 3:{
				//he alerts the mob at 3rd encounter
	
				if(interactTime ==1){
				tell(TALK_TXT2[number]);
				
				}else if (interactTime>1){
					CellEmitter.center( pos ).start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
				yell(TALK_TXT3[number]);
				for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
					mob.beckon( pos );
					if (Dungeon.level.heroFOV[mob.pos]) {
						Buff.prolong(mob, Amok.class, 5f);
					}
				}
				Sample.INSTANCE.play( Assets.SND_ALERT );
				}
			
			}break;

				//Female NPC with no quest #2
			case 4:{
		if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				tell(TALK_TXT3[number]);
				}
			}break;

				//Yharnamite wh*re, encounter as male
			case 5:{
		if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				tell(TALK_TXT3[number]);
				}
			}break;
				//Zealot
			case 6:{
if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				tell(TALK_TXT3[number]);
				}
// Unnamed male NPC #1
			}break;
			case 7:
				
				{
				if(interactTime <2){
					tell(TALK_TXT2[number]);
					if(Quest.swordGiven= false){
					Sword sw_ = new Sword();
					sw_.upgrade(3);
					if (sw_.doPickUp( Dungeon.hero )) {
												GLog.i( Messages.get(Dungeon.hero, "you_now_have", sw_.name()) );
					} else {
												Dungeon.level.drop( sw_, Dungeon.hero.pos ).sprite.drop();
					}
					Quest.swordGiven= true;
					}
					
				}else if (interactTime>=2){
					tell(TALK_TXT3[number]);
				}
			
			}break;
			//(8) Deceiver

				case 8:
				{
					if(interactTime <2){
					tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
					tell(TALK_TXT3[number]);
				}
			

			}break;
			
			case 9:{
			if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				tell(TALK_TXT3[number]);
				}

			}break;

			case 10:{
			if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				tell(TALK_TXT3[number]);
				}

			}break;

			case 11:{
			if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				tell(TALK_TXT3[number]);
				}

			}break;

			case 12:{
			if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				tell(TALK_TXT3[number]);
				}

			}break;

			case 13:{
			if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				tell(TALK_TXT3[number]);
				}

			}break;

			case 14:{
			if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				tell(TALK_TXT3[number]);
				}

			}break;

			case 15:{
			if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				Buff.affect( Dungeon.hero, Poison.class).set((7) );
                    Dart dart = new Dart();
                    Dungeon.level.drop(dart,Dungeon.hero.pos); 
				yell(TALK_TXT3[number]);
				}

			}break;

			case 16:{
			if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				tell(TALK_TXT3[number]);
				}

			}break;

			case 17:{
			if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				tell(TALK_TXT3[number]);
				}

			}break;

			case 18:{
				if(interactTime <2){
				tell(TALK_TXT2[number]);
				}else if (interactTime>=2){
				tell(TALK_TXT3[number]);
				}

			}break;

			}
		}
		
		
				
		

		return false;
	}
	
	public void PlayIdle2(){
		((YharnamiteSprite)sprite).Idle2();
	}
	
	public void tell( String text ) {
		GameScene.show(
			new WndQuest( this, text ));
	}
	
	public static class Quest{
	

	public static boolean gooDefeated = false;
	public static boolean swordGiven = false;
	public static boolean foodGiven = false;
	public static boolean bloodGiven = false;
	
	private static String NODE = "Yharnamite";
	private static String GOODEFEATED = "gooDefeated";
	private static String SWORDGIVEN = "swordGiven";
	private static String FOODGIVEN = "foodGiven";
	private static String BLOODGIVEN = "bloodGiven";
	
			public static void storeInBundle( Bundle bundle ) {
			
			Bundle node = new Bundle();
			
			node.put( GOODEFEATED, gooDefeated );
			node.put(SWORDGIVEN, swordGiven);
			node.put(FOODGIVEN, foodGiven);
			node.put(BLOODGIVEN, bloodGiven);
			

			
			bundle.put( NODE, node );
		}
		public static void Reset(){
			gooDefeated = false;
			swordGiven = false;
			foodGiven = false;
			bloodGiven = false;
		}



	}


}