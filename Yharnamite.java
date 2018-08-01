
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

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;

import com.shatteredpixel.shatteredpixeldungeon.items.quest.GoblinEar;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.YharnamiteSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndCaptain;

import com.shatteredpixel.shatteredpixeldungeon.windows.WndCultist;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndQuest;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Yharnamite extends NPC {

	{
		spriteClass = YharnamiteSprite.class;

		properties.add(Property.IMMOVABLE);
	}

	public String[] TALK_TXT ={
	"Oh, are you an adventurer? May I ask you a favor? My family is running out of food. And we dare not go out on a night like this. So could you bring us some blood-red berries? Thank you in advance, kind adventurer.",
	"Not from around here, are ya? An outsider, on the night of the hunt? You must be sick, mate. Get away from me now, go.",
	"Es ist niemand zu Hause... Entschuldige, outseiter!", 
	"Ohh, I'm terribly sorry... There's no one home... I'm really, really sorry... So sorry for ya...",
	"Lousy offcomer. I'll have no business with anyone while the hunt's on. Good luck staying alive till morning",
	" Wretched outsider. I hope they have your head before the morning",
	" What's that? ...I...I can't help you, apologies. Good night and good luck",
	 "Niemand ist hier krank. Uns geht es gut... Gute Jagd.",
	"What is it? I smell that... You must be a hunter. And not one of ours, either. Well, get lost, and don't come back!",
	" Sick creature, don't pretend you are normal. Away with you, now!",
	"Get lost! Oh, help me, gods...",
	"What's zat? Oh, we're fine here mate. All's in tip top shape, not even a sniff of cold. Gute Jagd."};
	public String[] TALK_TXT2 ={
		"...",
		"You again? Get out of my sight, you sicko!",
		"...",
		" I said there was nobody home. And we can't help you... Please, get away from us...",
	" Tsk... You again? What do you want this time? ",
	" Wretched outsider. Go feed yourself to a beast!",
	" Poor you. Stuck outside on the night of the hunt... I... I can't open this door. Forgive me. Good night and good luck", 
	"Niemand ist hier krank. Uns geht es gut... Gute Jagd.",
	" Stay away from my door step, hunter. We are armed and don't want to be disturbed",
	" Trying to fool me to open this door? Ha, typical outsiders... Go before I shoot you.",
	"Oh gods please help me, I must live. Ia Ia Cthulhu fhtagn!",
	"Ia Ia. Shub-Nigurath, the black goat of the wood and her thousand youngs..."};
	
	
	
	
	//public String[] MAD_TALK_TXT
	
	public int  number; // the number of this Yharnamite, max = 10, most of these NPCs are assign a number
	//TODO: questlines for these shut-in NPCs
	
	public int interactTime; // time PC has contacted this NPC.

	public  int number(){
		return  number;
	}

private static final String NUMBER = "number";
private static final String INTERACTTIME = "interactTime";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put(NUMBER, number);
		bundle.put(INTERACTTIME,interactTime);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		number = bundle.getInt( NUMBER );
		interactTime =bundle.getInt( INTERACTTIME );
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
	
	public boolean q1Given;
	public void HungryFamilyQuest(){
		if(!q1Given){
			tell(TALK_TXT[number]);
			q1Given = true;
		}else{
			tell(TALK_TXT2[number]);
		}
	}
	
	@Override
	public boolean interact() {
		
		tell(TALK_TXT[number]);
				
		

		return false;
	}
	
	private void tell( String text ) {
		GameScene.show(
			new WndQuest( this, text ));
	}
				

}