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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ShopkeeperSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTradeItem;

public class HuntressNPC extends NPC {

	{
		spriteClass = Huntress.class;

		properties.add(Property.IMMOVABLE);
	}

	private boolean seenBefore = false;
	private static final String[] MESSAGES ={
		" Ah ha, good to see you safe!",
		" Wanna buy some fresh meat, pardner?",
		" May the good Blood guide your way",
		" Did you hear about the plague that ravages the underground city?",
		" Don't wander too deep, it's dangerous down there.",
		//" Do you hear the graveyard murmurs? Then it's almost time",
		" They said the Drawves dug up the way to Hell, but I don't know if it was true",
		//" Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn!",
		" Well, eat up already? Wanna buy some more?",
		" When I go hunting, I often pray to Shub-Niggurath, lord of the wood",
		//" Ia! Shub-Niggurath! The Black Goat of the Woods with a Thousand Young!",
		//" Ia! Ia! Ia! Cthulhu fhtagn!",
		" Whatever you do, don't mess with the Cult of blood, my friend.",
		//" Honestly, I would never descend to the caves below the underground trading post. Some says the Gnolls take up residence there.",
		" Beware of the goblins in the deeper floor, my friend",
		" I heard that Loran is falling apart.",
		" If you visit Loran, be sure to bring along a silver sword",
		" If you visit Loran, be sure to bring along silver bullets",
		" Some says there are werewolves in Loran, but I don't know if it was true",
		" Beware of the beasts of Loran.",
		" If you visit Loran, make sure that you have some torches",
		" If you want to get to Loran, the safest way is to go through the old, abandoned prison.",
	};
	@Override
	protected boolean act() {
		
		if (!Quest.given && Dungeon.level.heroFOV[pos]) {
			if (!seenBefore) {
				yell( "A new face, are ya? The road ahead is long and dangerous. Wanna buy some supply?" );
			}
			seenBefore = true;
			sprite.turnTo( pos, Dungeon.hero.pos );
		} else {
			seenBefore = false;
			sprite.turnTo( pos, Dungeon.hero.pos );
			
			
			String msg = MESSAGES[Random.Int ("Friendly Huntress:"+ MESSAGES.length)];
			GLog.i(msg):

		}
		
		throwItem();
		
		return super.act();
	}
	
	
	@Override
	public void damage( int dmg, Object src ) {
		
	}
	
	@Override
	public void add( Buff buff ) {
	}
	
	CellEmitter.get( pos ).burst( ElmoParticle.FACTORY, 6 );
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	
	public static WndHuntress buy() {
		return GameScene.selectItem( itemSelector, WndHuntress.Mode.FOR_SALE, Messages.get(HuntressNPC.class, "buy"));
	}
	
	private static WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				WndBag parentWnd = buy();
				GameScene.show( new WndTradeItem( item, parentWnd ) );
			}
		}
	};

	@Override
	public boolean interact() {
		buy();
		return false;
	}
}