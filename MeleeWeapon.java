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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Random;

public enum itemQuality{
	none, Well_worn, Used, Vintage, Sturdy, High_Quality, Well_Crafted;

}

public class MeleeWeapon extends Weapon {
	
	public int tier;
	protected String prefix ="";
	protected String[] prefixes = {"none","Well-worn","Used","vintage","Sturdy","high-quality","Well-crafted"};
	protected itemQuality quality =itemQuality.none;

	@Override
	public int min(int lvl) {
		int k = 0;
		switch (quality){
		case itemQuality.High_Quality:
		{
			k = 1;
		}break;
		case itemQuality.Well_Crafted:
			{
			k = 2;
			}break;
		default:{
			k=0;
			}break;
		
		}
		return  k+ tier +  //base
				lvl;    //level scaling
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


		return k+ 5*(tier+1) +    //base
				lvl*(tier+1);   //level scaling
	}

	public int STRReq(int lvl){
		lvl = Math.max(0, lvl);
		//strength req decreases at +1,+3,+6,+10,etc.
		return (8 + tier * 2) - (int)(Math.sqrt(8 * lvl + 1) - 1)/2;
	}
	
	@Override
	public int damageRoll(Char owner) {
		int damage = augment.damageFactor(super.damageRoll( owner ));

		if (owner instanceof Hero) {
			int exStr = ((Hero)owner).STR() - STRReq();
			if (exStr > 0) {
				damage += Random.IntRange( 0, exStr );
			}
		}
		
		return damage;
	}
	
	@Override
	public String info() {

		String info = desc();

		if (levelKnown) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known", tier, augment.damageFactor(min()), augment.damageFactor(max()), STRReq());
			if (STRReq() > Dungeon.hero.STR()) {
				info += " " + Messages.get(Weapon.class, "too_heavy");
			} else if (Dungeon.hero.STR() > STRReq()){
				info += " " + Messages.get(Weapon.class, "excess_str", Dungeon.hero.STR() - STRReq());
			}
		} else {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_unknown", tier, min(0), max(0), STRReq(0));
			if (STRReq(0) > Dungeon.hero.STR()) {
				info += " " + Messages.get(MeleeWeapon.class, "probably_too_heavy");
			}
		}

		String stats_desc = Messages.get(this, "stats_desc");
		if (!stats_desc.equals("")) info+= "\n\n" + stats_desc;

		switch (augment) {
			case SPEED:
				info += "\n\n" + Messages.get(Weapon.class, "faster");
				break;
			case DAMAGE:
				info += "\n\n" + Messages.get(Weapon.class, "stronger");
				break;
			case NONE:
		}

		if (enchantment != null && (cursedKnown || !enchantment.curse())){
			info += "\n\n" + Messages.get(Weapon.class, "enchanted", enchantment.name());
			info += " " + Messages.get(enchantment, "desc");
		}

		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed");
		}
		
		return info;
	}

	@Override
	public String name() {
		String k = quality.ToString() +" "+ name;
		return quality!=itemQuality.none ? k: name;
	}

	public void RandomQuality(){
		int k = Random.Range(0,7);
		switch(k){
		case 0:{
		quality = itemQuality.Well_worn;
		}break;		
		case 1:{
		quality = itemQuality.Used;
		}break;
		case 3:{
		quality = itemQuality.vintage;
		}break;
		case 4:{
		quality = itemQuality.Sturdy;
		}break;
		case 5:{
		quality = itemQuality.High_QUality;
		}break;
		case 6:{
		quality = itemQuality.Well_Crafted;
		}break;
		default:
		case 7:{
		quality = itemQuality.none;
		}break;	

		}
	}
	
	@Override
	public int price() {
		int price = 20 * tier;
		switch (quality){
		case itemQuality.Well_worn:
		{
			price = -5;
		}break;
		case itemQuality.Vintage:
		{
			k = 1;
		}break;
		case itemQuality.Sturdy:
			{
			price+=5;
			}break;

		case itemQuality.High_Quality:
		{
			price+= 10;
		}break;
		case itemQuality.Well_Crafted:
			{
			price+= 15;
			}break;
		
		}
			
		if (hasGoodEnchant()) {
			price *= 1.5;
		}
		if (cursedKnown && (cursed || hasCurseEnchant())) {
			price /= 2;
		}
		if (levelKnown && level() > 0) {
			price *= (level() + 1);
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}

}