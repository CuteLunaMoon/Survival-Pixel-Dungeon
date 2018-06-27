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

package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Bones;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM300;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.SkeletonKey;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ToxicTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTileSheet;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;

public class CavesBossLevel2 extends Level {
	
	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;

		viewDistance = Math.min(6, viewDistance);
	}

	private static final int WIDTH = 32;
	private static final int HEIGHT = 32;

	private static final int ROOM_LEFT		= WIDTH / 2 - 3;
	private static final int ROOM_RIGHT		= WIDTH / 2 + 1;
	private static final int ROOM_TOP		= HEIGHT / 2 - 2;
	private static final int ROOM_BOTTOM	= HEIGHT / 2 + 2;
	private static final int gardenTop = 21;
	private static final int gardenBottom = 24;

	private static final int gardenLeft = 20;

	private static final int gardenRight = 20;
	//private static final int secret door = 23;


	private int arenaDoor;
	private boolean enteredArena = false;
	private boolean keyDropped = false;
	
	@Override
	public String tilesTex() {
		return Assets.TILES_CAVES;
	}
	
	@Override
	public String waterTex() {
		return Assets.WATER_CAVES;
	}
	
	private static final String DOOR	= "door";
	private static final String ENTERED	= "entered";
	private static final String DROPPED	= "droppped";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( DOOR, arenaDoor );
		bundle.put( ENTERED, enteredArena );
		bundle.put( DROPPED, keyDropped );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		arenaDoor = bundle.getInt( DOOR );
		enteredArena = bundle.getBoolean( ENTERED );
		keyDropped = bundle.getBoolean( DROPPED );
	}
	
	@Override
	protected boolean build() {
		
		setSize(WIDTH, HEIGHT);

		//Rect space = new Rect();


		map = MAP_START.clone();

		Foliage light = (Foliage)level.blobs.get( Foliage.class );
		if (light == null) {
			light = new Foliage();
		}
		for (int i= gardenTop + 1; i < gardenBottom; i++) {
			for (int j= gardebLeft + 1; j < gardenRight; j++) {
				light.seed( level, j + level.width() * i, 1 );
			}
		}

		level.blobs.put( Foliage.class, light );
		
		return true;
	}


	private static final int W = Terrain.WALL;
	private static final int D = Terrain.DOOR;
	private static final int L = Terrain.LOCKED_DOOR;
	private static final int e = Terrain.EMPTY;

	private static final int T = Terrain.INACTIVE_TRAP;

	private static final int E = Terrain.ENTRANCE;
	private static final int X = Terrain.EXIT;

	private static final int M = Terrain.WALL_DECO;
	private static final int P = Terrain.PEDESTAL;
	private static final int B = Terrian.BOOKSHELF;
        private static final int G = Terrian.HIGH_GRASS;
	private static final int f = Terrian.WATER;



	// I store this static map this here
	private static final int[] MAP_START =
			{       	W, W, W, W, W, W, W, W, W, W, X, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
					W, W, f, f, f, e, e, e, T, T, T, T, T, e, e, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
					W, W, f, f, e, e, e, e, e, T, T, T, e, e, e, W, W, W, W, M, M, G, G, G, W, W, W, W, W, W, W, W,
					W, W, f, e, e, e, e, e, e, e, e, e, e, e, e, W, W, W, W, f, f, f, f, G, G, W, W, W, W, W, W, W,
					W, W, e, e, e, e, e, e, e, e, e, e, T, W, W, W, W, W, G, f, f, f, f, G, G, W, W, W, W, W, W, W,
					W, W, T, e, e, e, e, e, e, e, e, e, e, W, W, W, W, W, W, f, f, f, f, G, G, W, W, W, W, W, W, W,
					W, W, e, e, e, e, e, e, e, e, e, e, W, W, W, W, W, W, W, f, f, f, f, G, G, W, W, W, W, W, W, W,
					W, W, e, e, T, e, e, e, T, W, W, W, W, W, W, W, W, W, W, f, f, f, f, G, G, W, W, W, W, W, W, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W, W, W, W, f, f, f, f, W, W, W, W, W, W, W, W, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, e, T, W, W, W, W, f, f, f, f, W, W, W, W, W, W, W, W, W,
					W, e, e, e, e, e, T, e, e, e, e, e, e, T, e, W, W, W, W, f, G, W, W, W, W, W, W, W, W, W, W, W,
					W, W, e, T, e, e, W, W, W, W, W, G, e, e, e, e, W, W, W, f, W, W, W, W, W, W, W, W, W, W, W, W,
					W, f, e, e, e, e, W, e, e, E, W, G, e, e, e, T, W, W, W, f, W, W, W, W, W, W, W, W, W, W, W, W,
					W, f, e, e, e, T, W, e, e, e, W, G, e, e, e, e, W, W, W, f, W, W, W, W, W, W, W, W, W, W, W, W,
					W, f, e, e, e, T, D, e, e, e, W, G, e, e, e, e, W, W, W, f, G, W, W, W, W, W, W, W, W, W, W, W,
					W, f, e, e, e, T, W, W, W, W, W, G, e, e, T, W, W, W, W, f, W, W, W, W, W, W, W, W, W, W, W, W,
					W, f, T, e, e, e, e, e, e, e, G, e, e, e, e, W, W, W, W, f, W, W, W, W, W, W, W, W, W, W, W, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, e, W, W, W, W, W, f, W, W, W, W, W, W, W, W, W, W, W, W,
					W, e, e, e, W, e, e, e, e, T, e, e, e, W, W, W, W, W, W, f, W, W, W, W, W, W, W, W, W, W, W, W,
					W, T, e, e, e, e, e, e, e, e, e, e, e, W, W, W, W, W, W, f, W, W, W, W, W, W, W, W, W, W, W, W,
					W, f, f, e, e, e, e, e, e, e, e, e, e, W, W, W, W, W, W, f, f, f, f, f, W, W, W, W, W, W, W, W,
					W, f, f, f, e, e, e, e, e, T, e, W, W, W, W, W, W, W, W, G, G, G, G, f, W, W, W, W, W, W, W, W,
					W, f, f, f, f, f, e, e, W, W, W, W, W, W, W, B, B, B, W, G, G, G, G, f, W, W, W, W, W, W, W, W,
					W, W, W, W, W, L, W, W, W, W, W, W, W, W, W, B, e, e, e, G, G, G, G, f, W, W, W, W, W, W, W, W,
					W, W, W, W, W, T, W, W, W, W, W, W, W, W, W, e, e, B, W, G, G, G, G, f, f, f, f, f, W, W, W, W,
					W, W, W, W, W, e, W, W, W, W, W, W, W, W, W, D, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
					W, W, W, T, T, T, T, T, W, B, B, B, B, W, B, e, e, B, B, B, B, W, e, e, e, W, W, W, W, W, W, W,
					W, W, W, T, T, T, T, T, W, B, e, P, B, W, e, e, e, e, B, B, B, W, e, e, e, W, W, W, W, W, W, W,
					W, e, D, T, T, T, T, T, W, B, e, e, B, W, e, e, e, e, B, B, e, D, e, e, e, W, W, W, W, W, W, W,
					W, W, W, T, T, T, T, T, L, e, e, e, e, D, e, e, e, e, B, B, B, W, e, e, e, W, W, W, W, W, W, W,
					W, W, W, T, T, T, T, T, W, B, B, B, B, W, B, B, e, B, B, B, B, W, e, e, e, W, W, W, W, W, W, W,
					W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, M, W, W, W, W, W, W, W, W, W, W, W, W, W, W};

	
	@Override
	public Mob createMob() {
		return null;
	}
	
	@Override
	protected void createMobs() {

	}
	
	public Actor respawner() {
		return null;
	}
	
	@Override
	protected void createItems() {
		Item item = Bones.get();
		if (item != null) {
			int pos;
			do {
				pos = Random.IntRange( ROOM_LEFT, ROOM_RIGHT ) + Random.IntRange( ROOM_TOP + 1, ROOM_BOTTOM ) * width();
			} while (pos == entrance);
			drop( item, pos ).type = Heap.Type.REMAINS;
		}
	}
	
	@Override
	public int randomRespawnCell() {
		int cell = entrance + PathFinder.NEIGHBOURS8[Random.Int(8)];
		while (!passable[cell]){
			cell = entrance + PathFinder.NEIGHBOURS8[Random.Int(8)];
		}
		return cell;
	}
	
	@Override
	public void press( int cell, Char hero ) {
		
		super.press( cell, hero );
		
		if (!enteredArena && outsideEntraceRoom( cell ) && hero == Dungeon.hero) {
			
			enteredArena = true;
			seal();
			drop(new IronKey(1), 35);
			drop(new IronKey(1), 35);

			
			for (Mob m : mobs){
				//bring the first ally with you
				if (m.alignment == Char.Alignment.ALLY){
					m.pos = Dungeon.hero.pos + (Random.Int(2) == 0 ? +1 : -1);
					m.sprite.place(m.pos);
					break;
				}
			}
			
			DM300 boss = new DM300();
			boss.state = boss.WANDERING;
			do {
				boss.pos = Random.Int( length() );
			} while (
				!passable[boss.pos] ||
				!outsideEntraceRoom( boss.pos ) ||
				heroFOV[boss.pos]);
			GameScene.add( boss );
			
			set( arenaDoor, Terrain.WALL );
			GameScene.updateMap( arenaDoor );
			Dungeon.observe();
			
			CellEmitter.get( arenaDoor ).start( Speck.factory( Speck.ROCK ), 0.07f, 10 );
			Camera.main.shake( 3, 0.7f );
			Sample.INSTANCE.play( Assets.SND_ROCKS );
		}
	}
	
	@Override
	public Heap drop( Item item, int cell ) {
		
		if (!keyDropped && item instanceof SkeletonKey) {
			
			keyDropped = true;
			unseal();
			

			CellEmitter.get( arenaDoor ).start( Speck.factory( Speck.ROCK ), 0.07f, 10 );
			
			set( arenaDoor, Terrain.EMPTY_DECO );
			GameScene.updateMap( arenaDoor );
			Dungeon.observe();
		}
		
		return super.drop( item, cell );
	}
	
	private boolean outsideEntraceRoom( int cell ) {
		int cx = cell % width();
		int cy = cell / width();
		return cx < ROOM_LEFT-1 || cx > ROOM_RIGHT+1 || cy < ROOM_TOP-1 || cy > ROOM_BOTTOM+1;
	}
	
	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.GRASS:
				return Messages.get(CavesLevel.class, "grass_name");
			case Terrain.HIGH_GRASS:
				return Messages.get(CavesLevel.class, "high_grass_name");
			case Terrain.WATER:
				return Messages.get(CavesLevel.class, "water_name");
			default:
				return super.tileName( tile );
		}
	}
	
	@Override
	public String tileDesc( int tile ) {
		switch (tile) {
			case Terrain.ENTRANCE:
				return Messages.get(CavesLevel.class, "entrance_desc");
			case Terrain.EXIT:
				return Messages.get(CavesLevel.class, "exit_desc");
			case Terrain.HIGH_GRASS:
				return Messages.get(CavesLevel.class, "high_grass_desc");
			case Terrain.WALL_DECO:
				return Messages.get(CavesLevel.class, "wall_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(CavesLevel.class, " The shelve is full of strange books: necromancy, demonology, how to summon a succubus, how to pick up fair maiden. Even more strange, most of them are written in the common tongue, not Drawven. ");
			default:
				return super.tileDesc( tile );
		}
	}

	@Override
	public Group addVisuals() {
		super.addVisuals();
		CavesLevel.addCavesVisuals(this, visuals);
		return visuals;
	}
}