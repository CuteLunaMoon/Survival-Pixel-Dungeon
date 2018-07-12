

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
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Golem;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Monk;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.DwarfToken;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.levels.CityLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ImpSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndGuardCaptain;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndQuest;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Imp extends NPC {

	{
		spriteClass = ImpSprite.class;

		properties.add(Property.IMMOVABLE);
	}
	
	private boolean seenBefore = false;
	private static final String TXT_QUEST_GIVEN = " Ahah! You must be a new adventurer. I'm...Sir Steadfast, friend to you adventurers."+
	" The way to the underground city Loran is now infested with goblins and the city will reward handsomely a florin for each goblin left ear. Whattaya say?";

	private static final String[] TXT_RANDOM = {
		"Two shillings for a goblin left ear. It's equal to diners for almost a week above ground. Do you think they pay too much, %s?",
		"There are many pilgrims who seek the city of Loran despite all the rumours these days... Help them if you can, good %s...", 
		"I heard that a plague of some sort is now ravaging Loran, and to stay alive there that you should always bring a silver sword along with you , %s. ",
		"Why are you still here, %s?Just go and kill some goblins. It's for your own good. You know, it's just what adventurers do! You'll get used to it... ",
		"A two bob bit per goblin left ear. No wonders why half of my men knocked off their work and ran off into the dungeon. If you ever find them, bring them back to me, %s... ",};


	
	@Override
	protected boolean act() {
		
		if (!Quest.given && Dungeon.level.heroFOV[pos]) {
			if (!seenBefore) {
				yell( Messages.get(this, "hey", Dungeon.hero.givenName() ) );
			}
			seenBefore = true;
		} else {
			seenBefore = false;
		}
		
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
		
		sprite.turnTo( pos, Dungeon.hero.pos );
		if (Quest.given) {
			
			GoblinEar tokens = Dungeon.hero.belongings.getItem( GoblinEar.class );
			if (tokens != null) {
				GameScene.show( new WndGuardCaptain( this, tokens ) );
			} else {
				tell( Quest.alternative ?
						Messages.get(this, "monks_2", Dungeon.hero.givenName())
						: Messages.get(this, "golems_2", Dungeon.hero.givenName()) );
			}
			
		} else {
			tell( Quest.alternative, Messages.get(this, "golems_1") );
			Quest.given = true;
			Quest.completed = false;
			
			Notes.add( Notes.Landmark.IMP );
		}

		return false;
	}
	
	private void tell( String text ) {
		GameScene.show(
			new WndQuest( this, text ));
	}
	

	public static class Quest {
		
		private static boolean alternative;
		
		private static boolean spawned;
		private static boolean given;
		private static boolean completed;
		
		public static Ring reward;
		
		public static void reset() {
			spawned = false;

			reward = null;
		}
		
		private static final String NODE		= "captain";
		
	//	private static final String ALTERNATIVE	= "alternative";
		private static final String SPAWNED		= "spawned";
		private static final String GIVEN		= "given";
		//private static final String COMPLETED	= "completed";
		//private static final String REWARD		= "reward";
		
		public static void storeInBundle( Bundle bundle ) {
			
			Bundle node = new Bundle();
			
			node.put( SPAWNED, spawned );
			
			if (spawned) {
				//node.put( ALTERNATIVE, alternative );
				
				node.put( GIVEN, given );
				//node.put( COMPLETED, completed );
				//node.put( REWARD, reward );
			}
			
			bundle.put( NODE, node );
		}
		
		public static void restoreFromBundle( Bundle bundle ) {

			Bundle node = bundle.getBundle( NODE );
			
			if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {
				//alternative	= node.getBoolean( ALTERNATIVE );
				
				given = node.getBoolean( GIVEN );
			}
		}
		
		public static void spawn( CityLevel level ) {
			if (!spawned && Dungeon.depth ==1) {
				
				Imp npc = new Imp();
				do {
					npc.pos = level.randomRespawnCell();
				} while (
						npc.pos == -1 ||
						level.heaps.get( npc.pos ) != null ||
						level.traps.get( npc.pos) != null ||
						level.findMob( npc.pos ) != null ||
						//The imp doesn't move, so he cannot obstruct a passageway
						!(level.passable[npc.pos + PathFinder.CIRCLE4[0]] && level.passable[npc.pos + PathFinder.CIRCLE4[2]]) ||
						!(level.passable[npc.pos + PathFinder.CIRCLE4[1]] && level.passable[npc.pos + PathFinder.CIRCLE4[3]]));
				level.mobs.add( npc );
				
				spawned = true;
				alternative = Random.Int( 2 ) == 0;
				
				given = false;
				
				
			}
		}
		
		public static void process( Mob mob ) {
			if (spawned && given && !completed) {
				if (( mob instanceof Goblin)  {
					
					Dungeon.level.drop( new GoblinEar(), mob.pos ).sprite.drop();
				}
			}
		}
				
		
	}
}
