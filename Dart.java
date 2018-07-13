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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Projecting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Crossbow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Dart extends MissileWeapon {

	{
		image = ItemSpriteSheet.DART;
	}

	@Override
	public int min(int lvl) {
	int k = 0;
		if( bow != null){
			
		switch (bow.quality)

		case itemQuality.High_Quality:
		{
			k = 1;
		}break;
		case itemQuality.Well_Crafted:
			{
			k = 2;
			}break;
		
		}
			
			if(bow.heavyCrossbow == true){
		if( bow != null){
			
		switch (bow.quality)

		case itemQuality.High_Quality:
		{
			k = 2;
		}break;
		case itemQuality.Well_Crafted:
			{
			k = 3;
			}break;
		
		}
			
			return   (k+15 + 2*bow.level());
			}else{
			`return   (k+7 + bow.level());
			}
		}else{
		return 1;
		}
		
	}

	@Override
	public int max(int lvl) {
		int k = 0;
			switch (quality){
			case itemQuality.Well_worn:
			{
			k = -2;
			}break;
			case itemQuality.Used:
			{
			k = -1;
			}break;
			case itemQuality.Vintage:
			{
			k = 1;
			}break;
			case itemQuality.Sturdy:
			{
			k = 2;
			}break;

			case itemQuality.High_Quality:
			{
				k = 3;
			}break;
			case itemQuality.Well_Crafted:
			{
			k = 4;
			}break;
		
			}
		if( bow != null){
			
			
			// Well-creafted Arbalest deal huge amount of damage
			
			if(bow.heavyCrossbow == true){
			
			k*=2;
				return   (k+24 + 4*bow.level());
			}else{
				return   (k+10 + 3*bow.level());
			}
		}else{
			return 2;
		}
	}

	@Override
	public int STRReq(int lvl) {
		return 12;
	}
	
	@Override
	protected float durabilityPerUse() {
		return 0;
	}
	
	private static Crossbow bow;
	
	private void updateCrossbow(){
		if (Dungeon.hero.belongings.weapon instanceof Crossbow){
			bow = (Crossbow) Dungeon.hero.belongings.weapon;
			
			
		} else {
			bow = null;
		}
	}
	
	@Override
	public int throwPos(Hero user, int dst) {
		if (bow != null && bow.hasEnchant(Projecting.class)
				&& !Dungeon.level.solid[dst] && Dungeon.level.distance(user.pos, dst) <= 4){
			return dst;
		} else {
			return super.throwPos(user, dst);
		}
	}

	@Override
	public float castDelay(Char user, int dst) {
		if( bow != null){
			
			if(bow.heavyCrossbow == true){
					float delay = 2*speedFactor( user );
		
					Char enemy = Actor.findChar(dst);
			
				if (enemy != null) {
				SnipersMark mark = user.buff( SnipersMark.class );
					if (mark != null) {
					if (mark.object == enemy.id()) {
					delay *= 0.5f;
					}
			}
		}
				
			}else{
					float delay = speedFactor( user );
		
					Char enemy = Actor.findChar(dst);
		
					if (enemy != null) {
				SnipersMark mark = user.buff( SnipersMark.class );
			if (mark != null) {
				if (mark.object == enemy.id()) {
					delay *= 0.5f;
					}
				}
				}
			
			}
		}else{
			float delay = speedFactor( user );
		
			Char enemy = Actor.findChar(dst);
		
			if (enemy != null) {
			SnipersMark mark = user.buff( SnipersMark.class );
			if (mark != null) {
				if (mark.object == enemy.id()) {
					delay *= 0.5f;
				}
			}
			}
		}
		
		return delay;
	}
	
	@Override
	public int proc(Char attacker, Char defender, int damage) {
		if (bow != null && bow.enchantment != null){
			damage = bow.enchantment.proc(bow, attacker, defender, damage);
		}
		return super.proc(attacker, defender, damage);
	}
	
	@Override
	protected void onThrow(int cell) {
		updateCrossbow();
		super.onThrow(cell);
	}
	
	@Override
	public String info() {
		updateCrossbow();
		return super.info();
	}
	
	@Override
	public int price() {
		return 4 * quantity;
	}
}