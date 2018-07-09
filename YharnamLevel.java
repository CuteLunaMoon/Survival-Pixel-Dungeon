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

public class YharnamLevel extends Level {
	
	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;

		viewDistance = Math.min(6, viewDistance);
	}


	private int arenaDoor = 13*32+7;
	private boolean enteredArena = false;
	private boolean keyDropped = false;
	
	@Override
	public String tilesTex() {
		return Assets.TILES_SEWERS;
	}
	
	@Override
	public String waterTex() {
		return Assets.WATER_SEWERS;
	}
	
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		
		
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
				
	}
	
	@Override
	protected boolean build() {
		
		setSize(32, 64);

		


		map = MAP_START.clone();

	
		
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
	private static final int B = Terrain.BOOKSHELF;
        private static final int G = Terrain.HIGH_GRASS;
	private static final int f = Terrain.WATER;
	private static final int H = Terrain.HIDDEN_DOOR; 
	private static final int C = Terrain.CHASM;
	 

	// I store this static map this here
	private static final int[] MAP_START =
	private static final int[] MAP_START =
			{       	W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, M, M, M, W,
					W, E, e, W, e, e, e, e, W, e, e, e, e, e, e, D, e, e, e, e, e, e, e, W, W, W, W, f, f, f, f, W,
					W, e, e, D, e, e, e, e, W, e, e, e, e, e, e, W, W, W, W, M, M, G, G, G, W, W, W, f, f, f, f, W,
					W, e, e, W, e, e, e, e, D, e, e, e, e, e, e, W, W, W, W, f, f, f, f, G, G, W, W, f, f, f, f, W,
					W, e, e, W, e, e, e, e, W, e, e, e, e, e, e, H, e, W, e, f, f, f, f, G, G, W, W, f, f, f, f, W,
					W, e, e, W, e, e, e, e, W, e, e, e, e, e, e, W, e, W, e, f, f, f, f, G, G, W, W, W, W, W, f, W,
					W, e, e, W, e, e, e, e, W, e, e, e, e, e, e, W, e, W, e, f, f, f, f, G, G, W, W, W, W, W, f, W,
					W, W, W, W, e, e, e, e, W, W, W, W, W, W, W, W, e, W, W, f, f, f, f, G, G, W, W, W, W, W, f, W,
					W, e, e, e, e, e, e, e, W, e, e, e, e, e, e, W, e, W, e, f, f, f, f, W, W, W, W, W, W, W, f, W,
					W, W, W, W, e, e, e, e, D, e, e, e, e, e, e, W, e, W, e, f, f, f, f, W, W, W, e, e, e, W, f, W,
					W, e, e, W, e, e, e, e, W, e, e, e, e, e, e, W, e, W, e, f, G, W, W, W, W, W, e, e, e, W, f, W,
					W, e, e, W, e, e, e, e, W, e, e, e, e, e, e, W, e, W, W, f, W, W, e, e, e, W, e, e, e, W, f, W,
					W, e, e, D, e, e, e, e, W, W, W, W, W, W, W, W, e, H, e, f, W, W, e, e, e, W, e, e, e, W, f, W,
					W, e, e, W, e, e, W, W, W, e, W, e, e, e, e, W, W, W, e, f, W, W, e, e, e, W, e, e, e, W, f, W,
					W, e, e, W, e, e, W, e, e, e, W, e, e, e, e, W, W, W, e, f, G, W, e, e, e, W, e, e, e, D, f, W,
					W, W, W, W, e, e, W, e, e, e, W, e, e, e, e, W, W, W, W, D, W, W, W, D, W, W, e, e, e, W, f, W,
					W, e, e, e, e, e, W, W, D, W, W, W, D, W, W, W, W, W, e, f, W, e, e, e, W, W, e, e, e, W, f, W,
					W, W, W, W, e, e, W, e, e, e, W, e, e, e, W, W, W, W, e, f, W, e, e, e, W, W, e, e, e, W, f, W,
					W, e, e, W, W, e, D, e, e, e, W, e, e, W, W, W, W, W, e, f, W, e, e, e, W, W, H, W, W, W, f, W,
					W, e, e, D, e, e, W, e, e, e, e, e, e, W, W, W, W, W, W, f, W, D, W, W, W, W, e, W, W, W, f, W,
					W, e, e, W, e, e, W, e, e, e, e, e, e, W, W, W, W, W, W, f, f, f, f, f, W, W, e, W, W, W, f, W,
					W, e, e, W, e, e, W, e, e, e, e, W, W, W, W, W, W, W, W, G, G, G, G, f, W, W, e, W, W, W, f, W,
					W, e, f, W, f, f, W, e, e, e, W, W, W, W, W, B, B, B, W, G, G, G, G, f, W, W, e, W, W, W, f, W,
					W, e, e, W, W, L, W, W, W, W, W, W, W, W, W, B, e, e, D, G, G, G, G, f, W, W, H, W, W, W, f, W,
					W, W, W, W, W, T, e, e, e, W, W, W, W, W, W, e, e, B, W, G, G, G, G, f, f, f, f, f, f, f, f, W,
					W, W, W, W, W, e, W, W, W, W, W, W, W, W, W, H, W, W, W, G, G, G, G, W, W, W, W, W, W, f, e, W,
					W, W, W, T, T, T, T, T, W, B, B, B, B, W, B, e, e, B, W, G, G, G, G, W, e, W, W, W, W, f, e, W,
					W, W, W, T, T, T, T, T, W, B, e, P, B, W, e, e, e, e, W, W, W, W, W, W, e, W, W, W, W, f, W, W,
					W, e, H, T, T, T, T, T, W, B, e, e, B, W, e, e, e, e, B, B, e, H, e, e, e, W, W, W, W, f, e, W,
					W, W, W, T, T, T, T, T, L, e, e, e, e, H, e, e, e, e, B, B, B, W, e, e, e, W, W, W, W, f, e, W,
					W, W, W, T, T, T, T, T, W, B, B, B, B, W, B, B, e, B, B, B, B, W, e, e, e, W, W, W, W, f, e, W,
					W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, M, W, W, W, W, W, W, W, W, W, W, W, f, W, W,
					W, W, W, W, W, W, W, W, W, W, X, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, f, e, W,
					W, W, f, f, f, e, e, e, T, T, T, T, T, e, e, W, W, W, W, W, W, W, W, W, W, W, W, W, W, f, e, W,
					W, W, f, f, e, e, e, e, e, T, T, T, e, e, e, W, W, W, W, M, M, G, G, G, W, W, W, W, W, f, e, W,
					W, W, f, e, e, e, e, e, e, e, e, e, e, e, e, W, W, W, W, e, e, e, f, G, G, W, W, W, W, f, W, W,
					W, W, e, W, e, e, e, e, e, e, e, e, T, e, 3, W, W, W, G, f, f, f, f, G, G, W, W, W, W, f, e, W,
					W, W, T, W, e, e, e, e, e, e, e, e, e, e, W, W, W, W, W, f, f, f, f, G, G, W, W, W, W, f, e, W,
					W, W, e, W, e, e, e, e, e, e, e, e, e, e, W, W, W, W, W, f, f, f, f, G, G, W, W, W, W, f, e, W,
					W, W, W, W, W, T, e, T, W, W, W, W, W, W, W, W, W, W, W, f, f, f, f, G, G, W, W, W, W, f, f, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W, W, W, W, f, f, f, f, W, W, W, W, W, W, W, f, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, e, T, W, W, W, W, f, f, f, f, W, W, W, W, W, W, W, f, W,
					W, e, e, e, e, e, T, e, e, e, e, e, e, T, e, W, W, W, W, f, G, W, W, W, W, W, W, W, W, W, f, W,
					W, W, e, T, e, e, W, W, W, W, W, G, e, e, e, e, W, W, W, f, W, W, W, W, W, W, W, W, W, W, f, W,
					W, f, e, e, e, e, W, e, e, E, W, G, e, e, e, T, W, W, W, f, W, W, W, W, W, W, W, W, W, W, f, W,
					W, f, e, e, e, T, W, e, e, e, W, G, e, e, e, e, W, W, W, f, W, W, W, W, W, W, W, W, W, W, f, W,
					W, f, e, e, e, T, D, e, e, e, W, G, e, e, e, e, W, W, W, f, G, W, W, W, W, W, W, W, W, W, f, W,
					W, f, e, e, e, T, W, W, W, W, W, G, e, e, T, W, W, W, W, D, W, W, W, W, W, W, W, W, W, W, f, W,
					W, f, T, e, e, e, e, e, e, e, G, e, e, e, e, W, W, W, W, f, W, W, W, W, W, W, W, W, W, W, f, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, e, W, W, W, W, W, f, W, W, W, W, W, W, W, W, W, W, f, W,
					W, e, e, e, W, e, e, e, e, T, e, e, e, W, W, W, W, W, W, f, W, W, W, W, W, W, W, W, W, W, f, W,
					W, T, e, e, e, e, e, e, e, e, e, e, e, W, W, W, W, W, W, f, W, W, W, W, W, W, W, W, W, W, f, W,
					W, f, f, e, e, e, e, e, e, e, e, e, e, W, W, W, W, W, W, f, f, f, f, f, W, W, W, W, W, W, C, W,
					W, f, f, f, e, e, e, e, e, T, e, W, W, W, W, W, W, W, W, G, G, G, G, f, W, W, W, W, W, W, W, W,
					W, f, f, f, f, f, e, e, W, W, W, W, W, W, W, B, B, B, W, G, G, G, G, f, W, W, W, W, W, W, W, W,
					W, W, W, W, W, L, W, W, W, W, W, W, W, W, W, B, e, e, H, G, G, G, G, f, W, W, W, e, e, e, e, W,
					W, W, W, W, W, T, W, W, W, W, W, W, W, W, W, e, e, B, W, G, G, G, G, f, f, f, D, e, e, e, e, W,
					W, W, W, W, W, e, W, W, W, W, W, W, W, W, W, H, W, W, W, W, W, W, W, W, W, W, W, W, W, D, W, W,
					W, W, W, T, T, T, T, T, W, B, B, B, B, W, B, e, e, B, B, B, B, W, e, e, e, W, W, W, e, e, e, W,
					W, W, W, T, T, T, T, T, W, B, e, P, B, W, e, e, e, e, B, B, B, W, e, e, e, W, W, W, e, e, e, W,
					W, e, H, T, T, T, T, T, W, B, e, e, B, W, e, e, e, e, B, B, e, H, e, e, e, W, W, W, e, e, e, W,
					W, W, W, T, T, T, T, T, L, e, e, e, e, H, e, e, e, e, B, B, B, W, e, e, e, W, W, W, e, e, e, W,
					W, W, W, T, T, T, T, T, W, B, B, B, B, W, B, B, e, B, B, B, B, W, e, e, e, W, W, W, e, e, X, W,
					W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, M, W, W, W, W, W, W, W, W, W, W, W, W, W, W


};

	
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
	


}