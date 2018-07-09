/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
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
package com.github.dachhack.sprout.actors.mobs.npcs;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.items.DewVial;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.items.Mushroom;
import com.github.dachhack.sprout.scenes.GameScene;
import com.github.dachhack.sprout.sprites.TinkererSprite;
import com.github.dachhack.sprout.utils.Utils;
import com.github.dachhack.sprout.windows.WndQuest;
import com.github.dachhack.sprout.windows.WndTinkerer;

public class Tinkerer1 extends NPC {

	{
		name = "tinkerer";
		spriteClass = TinkererSprite.class;
	}

	private static final String TXT_DUNGEON = "Ah-hah, You must be the new adventurer. I'm... Sir Steadfast, the guard captain of this city and friend to you adventurers."
			+ "The way to underground city Loran is now infested with Goblins and all sort of unsavory forks. "
			+ "The city would gladly pay you a florin per Goblin left ear, whattaya say?";
	
	

//	private static final String TXT_DUNGEON2 = "Why are you still here, %s?Just go and kill some goblins. It's for your own good. You know, it's just what adventurers do! You'll get used to it...";
	
	private static final String[] TXT_RANDOM = {
		"Two shillings for a goblin left ear. It's equal to diners for almost a week above ground. Do you think they pay too much, %s?",
		"I heard that a plague of some sort is now ravaging Loran, and to stay alive there that you should always bring a silver sword along with you , %s. ",
		"Why are you still here, %s?Just go and kill some goblins. It's for your own good. You know, it's just what adventurers do! You'll get used to it... ",
		"A two bob bit per goblin left ear. No wonders why half of my men knocked off their work and ran off into the dungeon. If you ever find them, bring them back to me, %s... ",};

	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}

	@Override
	public int defenseSkill(Char enemy) {
		return 10000;
	}

	

	@Override
	public void damage(int dmg, Object src) {
	}

	@Override
	public void add(Buff buff) {
	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	public void interact() {

		sprite.turnTo(pos, Dungeon.hero.pos);
		Item item = Dungeon.hero.belongings.getItem(GoblinsEar.class);
			if (item != null && seenBefore()) {
				GameScene.show(new WndCaptain(this, item));
			} else if (item == null && !seenBefore()) {
				tell(TXT_DUNGEON);
			} else {
				int k = Random.Int(TXT_RANDOM.length);
				tell(TXT_RANDOM[k]);
			}
		
	}

	private void tell(String format, Object... args) {
		GameScene.show(new WndQuest(this, Utils.format(format, args)));
	}

	@Override
	public String description() {
		return "Sir Steadfast, Captain of the city guard. " 
				+ "Also a common customer of the adventurer guild."

				+ "He is protected by a magical shield. ";
	}

}
