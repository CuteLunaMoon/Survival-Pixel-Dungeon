/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.funnygame.pixeldungeon.sprites;

import com.funnygame.noosa.Game;
import com.funnygame.noosa.Image;
import com.funnygame.noosa.TextureFilm;
import com.funnygame.noosa.tweeners.AlphaTweener;
import com.funnygame.noosa.tweeners.PosTweener;
import com.funnygame.noosa.tweeners.Tweener;
import com.funnygame.pixeldungeon.Assets;
import com.funnygame.pixeldungeon.Dungeon;
import com.funnygame.pixeldungeon.DungeonTilemap;
import com.funnygame.pixeldungeon.levels.Level;
import com.funnygame.pixeldungeon.objects.LevelObject;
import com.funnygame.pixeldungeon.plants.Plant;
import com.funnygame.pixeldungeon.scenes.GameScene;
import com.funnygame.utils.Callback;
import com.funnygame.utils.PointF;

public class ObjectSprite extends Image implements Tweener.Listener {

	private static TextureFilm frames;

	public LevelObject bound;

	private static final float MOVE_INTERVAL	= 0.1f;

	private int pos = -1;

	public ObjectSprite() {
		super( Assets.OBJECTS );

		if (frames == null) {
			frames = new TextureFilm( texture, 16, 16 );
		}

		origin.set( 8, 12 );
	}

	public ObjectSprite( int image ) {
		this();
		reset( image );
	}

	public void move( int to, Callback cb ) {

		Tweener motion = new PosTweener( this, worldToCamera( to ), MOVE_INTERVAL );
		motion.listener = this;
		parent.add( motion );
		if (cb != null){
			cb.call();
		}
	}

	public void move(int to, float time, Callback cb) {

		Tweener motion = new PosTweener( this, worldToCamera( to ), time );
		motion.listener = this;
		motion.callback = cb;
		parent.add( motion );
	}

	public void fade( float alpha, float time, Callback cb ){
		Tweener alphaTweener = new AlphaTweener( this, alpha, time );
		alphaTweener.listener = this;
		alphaTweener.callback = cb;
		parent.add( alphaTweener );
	}

	public PointF worldToCamera(int cell ) {

		final int csize = DungeonTilemap.SIZE;

		return new PointF(
				((cell % Level.WIDTH) + 0.5f) * csize - width * 0.5f,
				((cell / Level.WIDTH) + 1.0f) * csize - height
		);
	}

	@Override
	public void onComplete(Tweener tweener){
	}
	
	public void reset( LevelObject obj ) {
		
		revive();
		
		reset( obj.image );
		alpha( 1f );
		
		pos = obj.pos;
		x = (pos % Level.WIDTH) * DungeonTilemap.SIZE;
		y = (pos / Level.WIDTH) * DungeonTilemap.SIZE;
	}
	
	public void reset( int image ) {
		frame( frames.get( image ) );
	}
	
	@Override
	public void update() {
		super.update();
	}
}