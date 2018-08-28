package com.shatteredpixel.shatteredpixeldungeon.objects;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroAction;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ObjectSprite;
import com.watabou.ErrorLog;
import com.watabou.utils.GLog;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

/**
 * This file is created by Funny Game and it's
 * a part of Perfect Pixel Dungeon.
 * Creation Date: 11.12.2017
 */

public class LevelObject implements Bundlable {

    public String name;

    public int image;
    public int pos;

    public boolean initialised = false;
    public boolean breakable;
    public Class breakItem;

    public Interaction interactionType = Interaction.HANDLE;

    public int flags = Terrain.PASSABLE;

    public ObjectSprite sprite;

    {
        name = "„S„u„ƒ„„";
        image = 0;
        breakable = true;
        breakItem = null;
    }

    private static final String NAME       = "name";
    private static final String IMAGE      = "image";
    private static final String POS        = "pos";
    private static final String BREAKABLE  = "breakable";
    private static final String BREAK_ITEM = "breakItem";
    private static final String FLAGS      = "flags";

    public void init(int pos){
        this.pos = pos;

        setFlags();

        initialised = true;
    }

    public void setFlags(){
        Dungeon.level.passable[pos] = (flags & Terrain.PASSABLE) != 0;
         Dungeon.level.losBlocking[pos] = (flags & Terrain.LOS_BLOCKING) != 0;
         Dungeon.level.flamable[pos] = (flags & Terrain.FLAMABLE) != 0;
         Dungeon.level.solid[pos] = (flags & Terrain.SOLID) != 0;
         Dungeon.level.avoid[pos] = (flags & Terrain.AVOID) != 0;
        Dungeon.level.pit[pos] = (flags & Terrain.PIT) != 0;
    }

    public void restoreFlags(){
        int flagz = Terrain.flags[Dungeon.level.map[pos]];
        Dungeon.level.passable[pos]		= (flagz & Terrain.PASSABLE) != 0;
        Dungeon.level.losBlocking[pos]	= (flagz & Terrain.LOS_BLOCKING) != 0;
         Dungeon.level.flamable[pos]		= (flagz & Terrain.FLAMABLE) != 0;
         Dungeon.level.secret[pos]		= (flagz & Terrain.SECRET) != 0;
         Dungeon.level.solid[pos]		= (flagz & Terrain.SOLID) != 0;
         Dungeon.level.avoid[pos]		= (flagz & Terrain.AVOID) != 0;
         Dungeon.level.water[pos]		= (flagz & Terrain.LIQUID) != 0;
         Dungeon.level.pit[pos]			= (flagz & Terrain.PIT) != 0;
    }

    public void storeInBundle(Bundle bundle){
        bundle.put(NAME, name);
        bundle.put(IMAGE, image);
        bundle.put(POS, pos);
        bundle.put(BREAKABLE, breakable);
        if (breakItem != null) {
            bundle.put(BREAK_ITEM, breakItem.getCanonicalName());
        } else {
            bundle.put(BREAK_ITEM, "");
        }
        bundle.put(FLAGS, flags);
    }

    public void restoreFromBundle(Bundle bundle){
        name = bundle.getString(NAME);
        image = bundle.getInt(IMAGE);
        pos = bundle.getInt(POS);
        breakable = bundle.getBoolean(BREAKABLE);
        try {
            if (bundle.getString(BREAK_ITEM) != "") {
                breakItem = Class.forName(bundle.getString(BREAK_ITEM));
            } else {
                breakItem = null;
            }
        } catch (Exception e){
            ErrorLog.log(e);
        }
        flags = bundle.getInt(FLAGS);

        setFlags();
    }

    public String desc(){
        return "";
    }

    public static LevelObject find (int cell){
        return Dungeon.level.getTopObject(cell);
    }

    public void interact( Char ch ){
    }

    public void onPlace() {
    }

    public void onDestroy() {
    }

    public void everyTurn() {
    }

    public void destroy(){
        Dungeon.level.deleteObject(pos, false);
    }

    public enum Interaction {
        HANDLE,
        PRESS,
        NONE
    }
}