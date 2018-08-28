package com.shatteredpixel.shatteredpixeldungeon.items.placeables;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.objects.Beartrap;
import com.shatteredpixel.shatteredpixeldungeon.objects.LevelObject;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

/**
 * This file was created by Funny Game and it was
 * a part of Perfect Pixel Dungeon.
 * Creation Date: 11.12.2017
 */

public class TileboundItem extends Item {
    public static final String TXT_OUT_OF_RANGE = "Out of range";
    public static final String TXT_IN_THE_WALL  = "You can't place it here.";
    public static final String TXT_OVERLAPS     = "There's already something here";

    public static TileboundItem current;

    public LevelObject tile;

    public static String AC_PLACE = "ÑQÑ@ÑHÑMÑEÑRÑSÑIÑSÑ]";

    {
        tile = new LevelObject();

        image = 0;
        imageFile = Assets.OBJECTS;

        defaultAction = AC_PLACE;

        stackable = true;
    }

    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_PLACE );
        return actions;
    }

    private static final String TILE = "tile";

    @Override
    public void storeInBundle(Bundle bundle){
        super.storeInBundle(bundle);
        bundle.put(TILE, tile);
    }

    @Override
    public void restoreFromBundle(Bundle bundle){
        super.restoreFromBundle(bundle);
        tile = (LevelObject)bundle.get(TILE);
    }

    @Override
    public void execute(Hero hero, String action){
        if (action == AC_PLACE){
            current = this;
            curUser = hero;
            GameScene.selectCell( placer );
        } else {
            super.execute(hero, action);
        }
    }

    protected static CellSelector.Listener placer = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer cell ) {
            if (cell != null) {
                if (Dungeon.visible[cell]) {
                    if (Dungeon.level.passable[cell]) {
                        if (Dungeon.level.getTopObject(cell) != null){
                            GLog.w(TXT_OVERLAPS);
                        } else {
                            if (Actor.findChar(cell) == null) {
                                Dungeon.level.placeObject(current.tile, cell, true);
                                current.detach(Dungeon.hero.belongings.backpack);
                                curUser.spend(1);
                            }
                        }
                    } else {
                        GLog.w(TXT_IN_THE_WALL);
                    }
                } else {
                    GLog.w(TXT_OUT_OF_RANGE);
                }
            }
        }
        @Override
        public String prompt() {
            return "ÑBÑçÑqÑuÑÇÑyÑÑÑu Ñ}ÑuÑÉÑÑÑÄ ÑtÑ|Ñë ÑÖÑÉÑÑÑpÑ~ÑÄÑrÑ{Ñy";
        }
    };

    @Override
    public boolean isIdentified(){
        return true;
    }
}