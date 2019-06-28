/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Piranha;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class FishingSpear extends MissileWeapon {
	
	{
		image = ItemSpriteSheet.FISHING_SPEAR;
		
		tier = 2;
	}
	
	public static final String AC_FISH = "FISH";
	
	public static final float TIME_TO_FISH = 3;
	
	@Override
	public int proc(Char attacker, Char defender, int damage) {
		if (defender instanceof Piranha){
			damage = 5+ Math.max(damage, defender.HP/2);
		}
		return super.proc(attacker, defender, damage);
	}
	
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_FISH);

		return actions;
	}
	
	@Override
	public void execute(final Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_FISH)) {
			for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
				
				final int pos = hero.pos + PathFinder.NEIGHBOURS8[i];
				if (Dungeon.level.map[pos] == Terrain.HIGH_GRASS) {
				
					hero.spend( TIME_TO_MINE );
					hero.busy();
					
					hero.sprite.attack( pos, new Callback() {
						
						@Override
						public void call() {
							decrementDurability();
							CellEmitter.get( pos ).burst( LeafParticle.LEVEL_SPECIFIC, 15 );
							Sample.INSTANCE.play( Assets.SND_EVOKE );
							if(Random.Int(10)<=3){
								Item fish = new Fish();
								 if (fish.doPickUp( Dungeon.hero )) {
                                        GLog.i( Messages.get(Dungeon.hero, "you_now_have", fish.name()) );
                                    } else {
                                        Dungeon.level.drop( fish, hero.pos ).sprite.drop();
                                    }
							}else{
								
								 GLog.i("You catch nothing");
							}
							
							
						}
					}
				}
			}
		}
	}
	
}
