/*
 * This file is part of Code, Block and Craft.
 * Copyright (c) 2016, GoldSpy98 and bernas-antunes97, All rights reserved.
 *
 * Code, Block and Craft 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Code, Block and Craft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Code, Block and Craft.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package io.github.cbcraft.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Items {
	public static Item itemWrench;
	public static Item itemRemote;
	
	public static void init() {
		GameRegistry.registerItem(itemWrench = new ItemWrench("wrench"), itemWrench.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(itemRemote = new ItemRemote("remote"), itemRemote.getUnlocalizedName().substring(5));
	}
	
	public static final CreativeTabs tabItems = new CreativeTabs("items") {
		@Override
		public Item getTabIconItem() {
			return itemRemote;
		}
	};
}
