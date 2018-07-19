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

import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class Bestiary {
	
	public static ArrayList<Class<? extends Mob>> getMobRotation( int depth ){
		ArrayList<Class<? extends Mob>> mobs = standardMobRotation( depth );
		addRareMobs(depth, mobs);
		swapMobAlts(mobs);
		Random.shuffle(mobs);
		return mobs;
	}
	
	//returns a rotation of standard mobs, unshuffled.
	private static ArrayList<Class<? extends Mob>> standardMobRotation( int depth ){
		switch(depth){
			
			// Sewers
			case 1: default:
	//		return new ArrayList<>(Arrays.asList(WalkingMushroom.class,
	//					 WalkingMushroom.class,WalkingMushroom.class,WalkingMushroom2.class,WalkingMushroom2.class, WalkingMushroom2.class));


				//10x rat
				return new ArrayList<Class<? extends Mob>>(Arrays.asList(
					Rat.class, Rat.class, Rat.class, Rat.class, Rat.class,
    				Rat.class, Rat.class, Rat.class, BrownBat.class , BrownBat.class));
			case 2:
				//3x rat, 1x gnoll, 3x goblin
				return new ArrayList<>(Arrays.asList(Rat.class, Rat.class, Rat.class,
						Gnoll.class, Goblin.class, Goblin.class, Goblin.class,BrownBat.class));
			case 3:
				//2x rat, 2x gnoll, 1x crab, 1x swarm,  2x goblin
				return new ArrayList<>(Arrays.asList(Rat.class, Rat.class,
						Gnoll.class, Gnoll.class, Goblin.class, Gnoll.class,
						Crab.class, Swarm.class, Goblin.class,BrownBat.class));
			case 4:
				//1x rat, 2x gnoll, 3x crab, 1x swarm, 2x goblin,
				return new ArrayList<>(Arrays.asList(Rat.class,
						Gnoll.class, Gnoll.class,
						Crab.class, Crab.class, Crab.class,
						Swarm.class, Goblin.class, Goblin.class));
			case 5: // 3x fetid rat, 1x Great crab, 3x crab, 1x skeleton, 1x goblin

				return new ArrayList<>(Arrays.asList(FetidRat2.class,
						FetidRat2.class, FetidRat2.class,
						Crab.class, Crab.class, Crab.class,
						Skeleton.class, Goblin.class, GreatCrab2.class,BrownBat.class));


				
			// Prison
			case 7:
				//3x skeleton, 1x thief, 1x swarm, 1x Rat
				return new ArrayList<>(Arrays.asList(Skeleton.class, Skeleton.class, Skeleton.class,
						Thief.class,
						Swarm.class, Rat.class));
			case 8:
				//3x skeleton, 1x thief, 1x shaman, 1x guard, 1x fetid rat
				return new ArrayList<>(Arrays.asList(Skeleton.class, Skeleton.class, Skeleton.class,
						Thief.class, FetidRat2.class,
						Shaman.class,
						Guard.class));
			case 9:
				//3x skeleton, 1x thief, 2x shaman, 2x guard, 1x Rat
				return new ArrayList<>(Arrays.asList(Skeleton.class, Skeleton.class, Skeleton.class,
						Thief.class,
						Shaman.class, Shaman.class,
						Guard.class, Guard.class, Rat.class));
			case 10:
				//3x skeleton, 1x thief, 2x shaman, 3x guard
				return new ArrayList<>(Arrays.asList(Skeleton.class, Skeleton.class, Skeleton.class,
						Thief.class,
						Shaman.class, Shaman.class,
						Guard.class, Guard.class, Guard.class));
	
			case 11:
				//3x skeleton, 1x thief, 2x shaman, 3x guard
				return new ArrayList<>(Arrays.asList(Skeleton.class, Skeleton.class, Skeleton.class,
						Thief.class,
						Shaman.class, Shaman.class,
						Guard.class, Guard.class, Guard.class));
			//caves
				case 13:
				//5x bat, 1x brute, 1x Rat, 1x skull shaman
				return new ArrayList<>(Arrays.asList(
						Bat.class, Shaman.class, Bat.class,Bat.class,
						Brute.class, Rat.class,SkullShaman.class));
							case 14:
				//5x bat, 1x brute, 1x Rat, 1x skull shaman
				return new ArrayList<>(Arrays.asList(
						Bat.class,  Bat.class,  Bat.class,
						Brute.class, Rat.class,SkullShaman.class));
							case 15:
				//5x bat, 1x brute, 1x Rat, 1x skull shaman
				return new ArrayList<>(Arrays.asList(
						Bat.class, Bat.class, Bat.class,
						Brute.class, Rat.class,SkullShaman.class));
							case 16:
				//5x bat, 1x brute, 1x Rat, 1x skull shaman
				return new ArrayList<>(Arrays.asList(
						Bat.class, Bat.class, Bat.class,SkullShaman.class,
						Brute.class, Rat.class,SkullShaman.class));

			//Bloodborne
				case 17:
				//3x skeleton, 1x thief, 2x shaman, 3x guard
				return new ArrayList<>(Arrays.asList( Huntsman.class,  Huntsman.class, TorchHuntsman.class,
						TorchHuntsman.class,
						TorchHuntsman.class, TorchHuntsman.class,
						TorchHuntsman.class, ShieldHuntsman.class, Guard.class));
			case 18:
				//3x skeleton, 1x thief, 1x swarm, 1x Rat
				return new ArrayList<>(Arrays.asList(Huntsman.class, Huntsman.class, TorchHuntsman.class,
						TorchHuntsman.class,SwordHuntsman.class,
						ShieldHuntsman.class, FetidRat2.class));
			case 19:
				//3x skeleton, 1x thief, 1x shaman, 1x guard, 1x fetid rat
				return new ArrayList<>(Arrays.asList(Skeleton.class, Huntsman.class, Skeleton.class,
						TorchHuntsman.class, FetidRat2.class,SwordHuntsman.class,
						ShieldHuntsman.class,
						Guard.class));
			case 20:
				//3x skeleton, 1x thief, 2x shaman, 2x guard, 1x Rat
				return new ArrayList<>(Arrays.asList(Skeleton.class, Skeleton.class, Skeleton.class,
						Thief.class,
						Shaman.class, Shaman.class,
						ShieldHuntsman.class, Guard.class, Rat.class));


				
			// Caves with Mushrooms
			case 22:
				//5x bat, 1x brute, 1x Rat, 1x skull shaman
				return new ArrayList<>(Arrays.asList(
						Bat.class, WalkingMushroom.class, Bat.class, WalkingMushroom2.class, Bat.class,
						Brute.class, Rat.class,SkullShaman.class));
			case 23:
				//5x bat, 5x brute, 1x spinner, 1x Skull shaman
				return new ArrayList<>(Arrays.asList(
						Bat.class, WalkingMushroom.class, WalkingMushroom2.class, Bat.class, Bat.class,
						Brute.class, Brute.class, Brute.class, Brute.class, Brute.class,
						Spinner.class, SkullShaman.class));
			case 24:
				//1x bat, 3x brute, 1x shaman, 1x spinner, 1 x rat
				return new ArrayList<>(Arrays.asList(
						Bat.class,
						WalkingMushroom2.class, Brute.class, Brute.class,
						Shaman.class,
						Spinner.class, Rat.class, SkullShaman.class));
			case 25:
				//1x bat, 3x brute, 1x shaman, 4x spinner, 1x rat, 1x skull shaman
				return new ArrayList<>(Arrays.asList(
						WalkingMushroom.class,
						Brute.class, Brute.class, Brute.class,
						Shaman.class, Rat.class,
						WalkingMushroom2.class, Spinner.class, Spinner.class, Spinner.class,SkullShaman.class));
			case 26:
				//1x bat, 3x brute, 1x shaman, 4x spinner, 1x rat, 1x skull shaman
				return new ArrayList<>(Arrays.asList(
						WalkingMushroom.class,
						Brute.class, Brute.class, Brute.class,
						Shaman.class, Rat.class,
						WalkingMushroom2.class, Spinner.class, Spinner.class, Spinner.class,SkullShaman.class));
				
			// City
			case 28:
				//5x elemental, 5x warlock, 1x monk, 1 x bat
				return new ArrayList<>(Arrays.asList(
						Elemental.class, Elemental.class, Elemental.class, Elemental.class, Elemental.class,
						Warlock.class, Warlock.class, Warlock.class, Warlock.class, Warlock.class,
						Monk.class, Bat.class));
			case 29:
				//2x elemental, 2x warlock, 2x monk, 1x rat
				return new ArrayList<>(Arrays.asList(
						Elemental.class, Elemental.class,
						Warlock.class, Warlock.class,
						Monk.class, Monk.class, Rat.class));
			case 30:
				//1x elemental, 1x warlock, 2x monk, 1x golem
				return new ArrayList<>(Arrays.asList(
						Elemental.class,
						Warlock.class,
						Monk.class, Monk.class,
						Golem.class));
			case 31:
				//1x elemental, 1x warlock, 2x monk, 3x golem
				return new ArrayList<>(Arrays.asList(
						Elemental.class,
						Warlock.class,
						Monk.class, Monk.class,
						Golem.class, Golem.class, Golem.class));


			case 34:
				//1x succubus, 2x evil eye, 3x scorpio
				return new ArrayList<>(Arrays.asList(
						Succubus.class,
						Eye.class, Eye.class,
						Scorpio.class, Scorpio.class, Scorpio.class));
						case 35:
				//1x succubus, 2x evil eye, 3x scorpio
				return new ArrayList<>(Arrays.asList(
						Succubus.class,
						Eye.class, Eye.class,
						Scorpio.class, Scorpio.class, Scorpio.class));
						case 36:
				//1x succubus, 2x evil eye, 3x scorpio
				return new ArrayList<>(Arrays.asList(
						Succubus.class,
						Eye.class, Eye.class,
						Scorpio.class, Scorpio.class, Scorpio.class));
						case 37:
				//1x succubus, 2x evil eye, 3x scorpio
				return new ArrayList<>(Arrays.asList(
						Succubus.class,
						Eye.class, Eye.class,
						Scorpio.class, Scorpio.class, Scorpio.class));
						case 38:
				//1x succubus, 2x evil eye, 3x scorpio
				return new ArrayList<>(Arrays.asList(
						Succubus.class,
						Eye.class, Eye.class,
						Scorpio.class, Scorpio.class, Scorpio.class));

		}
		
	}
	
	//has a chance to add a rarely spawned mobs to the rotation
	public static void addRareMobs( int depth, ArrayList<Class<?extends Mob>> rotation ){
		
		switch (depth){
			
			// Sewers
			default:
				return;
			case 4:
				if (Random.Float() < 0.01f) rotation.add(Skeleton.class);
				if (Random.Float() < 0.01f) rotation.add(Thief.class);
				return;
				
			// Prison
			case 6:
				if (Random.Float() < 0.2f)  rotation.add(Shaman.class);
				return;
			case 8:
				if (Random.Float() < 0.02f) rotation.add(Bat.class);
				return;
			case 9:
				if (Random.Float() < 0.02f) rotation.add(Bat.class);
				if (Random.Float() < 0.01f) rotation.add(Brute.class);
				return;
				
			// Caves
			case 13:
				if (Random.Float() < 0.02f) rotation.add(Elemental.class);
				return;
			case 14:
				if (Random.Float() < 0.02f) rotation.add(Elemental.class);
				if (Random.Float() < 0.01f) rotation.add(Monk.class);
				return;
				
			// City
			case 19:
				if (Random.Float() < 0.02f) rotation.add(Succubus.class);
				if (Random.Float() < 0.02f) rotation.add(Eye.class);
				return;
		}
	}
	
	//switches out regular mobs for their alt versions when appropriate
	private static void swapMobAlts(ArrayList<Class<?extends Mob>> rotation){
		for (int i = 0; i < rotation.size(); i++){
			if (Random.Int( 50 ) == 0) {
				Class<? extends Mob> cl = rotation.get(i);
				if (cl == Rat.class) {
					cl = Albino.class;
				} else if (cl == Thief.class) {
					cl = Bandit.class;
				} else if (cl == Brute.class) {
					cl = Shielded.class;
				} else if (cl == Monk.class) {
					cl = Senior.class;
				} else if (cl == Scorpio.class) {
					cl = Acidic.class;
				}
				rotation.set(i, cl);
			}
		}
	}
}