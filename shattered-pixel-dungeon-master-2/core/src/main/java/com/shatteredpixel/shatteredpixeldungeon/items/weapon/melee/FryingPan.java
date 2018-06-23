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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.food.DeadRat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ChargrilledMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.TailedCutlet;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Tinder;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;


import java.util.ArrayList;

public class FryingPan extends Weapon {
	
	public static final String AC_COOK	= "COOK";
	
	public static final float TIME_TO_COOK = 2;
	
	protected WndBag.Mode mode = WndBag.Mode.FOOD;
	protected String inventoryTitle = "Select a piece of food";
	
	
	{
		image = ItemSpriteSheet.FRYING_PAN;
		
		unique = true;
		bones = false;
		
		defaultAction = AC_COOK;

	}
	


	@Override
	public int min(int lvl) {
		return 1;   //tier 2
	}

	@Override
	public int max(int lvl) {
		return 2;  //tier 1
	}

	@Override
	public int STRReq(int lvl) {
		return 9;  //tier 1
	}

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_COOK );
		return actions;
	}
	
	@Override
	public void execute( final Hero hero, String action ) {

		super.execute( hero, action );
		
		if (action.equals(AC_COOK)) {
			
			GameScene.selectItem(itemSelector, mode, inventoryTitle);
			
		}
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public int proc( Char attacker, Char defender, int damage ) {

		return damage;
	}
	

	
	

	
	protected static WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null && item instanceof Food ) {

				if( item.canBeCook == true) {
				
				Tinder tinders = Dungeon.hero.belongings.getItem( Tinder.class );
				WandOfFireblast wand = Dungeon.hero.belongings.getItem( WandOfFireblast.class );
				
				if (tinders != null ){
					GLog.p("you fried the food");
					Hero hero = Dungeon.hero;
					hero.sprite.operate(hero.pos);
					hero.busy();
					hero.spend(TIME_TO_COOK);
                    tinders.detach(hero.belongings.backpack);

					if (item instanceof MysteryMeat) {
						// DeadRat is inharitaged from MysteryMeat
						if( item instanceof DeadRat){
							item.detach(hero.belongings.backpack);
							Dungeon.level.drop(new TailedCutlet(), hero.pos).sprite.drop();
						}else {
							item.detach(hero.belongings.backpack);
							Dungeon.level.drop(new ChargrilledMeat(), hero.pos).sprite.drop();
						}
					}

					}else if(wand != null){
						if(wand.curCharges >0){
							wand.curCharges-=1;
							wand.updateQuickslot();
								GLog.p("you fried the food");
							Hero hero = Dungeon.hero;
							hero.sprite.operate(hero.pos);
						hero.busy();
						hero.spend(TIME_TO_COOK);


							if (item instanceof MysteryMeat) {
								// DeadRat is inharitaged from MysteryMeat
									if( item instanceof DeadRat){
										item.detach(hero.belongings.backpack);
										Dungeon.level.drop(new TailedCutlet(), hero.pos).sprite.drop();
										}else {
										item.detach(hero.belongings.backpack);
										Dungeon.level.drop(new ChargrilledMeat(), hero.pos).sprite.drop();
										}
							}
						}else{
						 GLog.p("You need some tinders to start a fire");
						}
					}else{
					 GLog.p("You need some tinders to start a fire");
					}
				}else {
					GLog.p("This food doesn't need more heating");
				}
			}
		}
	};

}
