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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GnollTricksterSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class MolotovHuntsmen extends Enemy{

	{
		spriteClass = GnollTricksterSprite.class;

		HP = HT = 50;
		defenseSkill = 7;

		EXP = 5;

		state = WANDERING;

		//at half quantity, see createLoot()
		loot = Generator.Category.MISSILE;
		lootChance = 0.5f;

		//properties.add(Property.MINIBOSS);
	}

	private int combo = 0;

	@Override
	public int attackSkill( Char target ) {
		return 16;
	}

	@Override
	protected boolean canAttack( Char enemy ) {
		Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
		return !Dungeon.level.adjacent(pos, enemy.pos) && attack.collisionPos == enemy.pos;
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		//The gnoll's attacks get more severe the more the player lets it hit them
		combo++;
		final int color = ItemSprite.pick( image, 8, 10 );
		Splash.at( enemy.pos, color, 5 );
		for (int offset : PathFinder.NEIGHBOURS9){
			if (!Dungeon.level.solid[enemy.pos+offset]) {

				GameScene.add(Blob.seed(enemy.pos + offset, 2, Fire.class));

			}
		}

		
		return damage;
	}

	@Override
	protected boolean getCloser( int target ) {
		combo = 0; //if he's moving, he isn't attacking, reset combo.
		if (state == HUNTING) {
			return enemySeen && getFurther( target );
		} else {
			return super.getCloser( target );
		}
	}
	
	@Override
	protected Item createLoot() {
		MissileWeapon drop = (MissileWeapon)super.createLoot();
		//half quantity, rounded up
		drop.quantity((drop.quantity()+1)/2);
		return drop;
	}
	
	@Override
	public void die( Object cause ) {
		super.die( cause );

	}

	private static final String COMBO = "combo";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put(COMBO, combo);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		combo = bundle.getInt( COMBO );
	}

}