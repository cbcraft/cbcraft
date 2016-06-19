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

package io.github.cbcraft.common.block;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Blocks {
	public static String blockCodeUnlocalizedNamePrefix = "code_";
	
	public static Block blockRobot;
	public static Block blockCodeStart;
	public static Block blockCodeMove;
	public static Block blockCodeRotate;
	public static Block blockCodeIf;
	public static Block blockCodeIfElse;
	public static Block blockCodeElse;
	public static Block blockCodeIfEnd;
	public static Block blockCodeFor;
	public static Block blockCodeForEnd;
	public static Block blockCodePlace;
	public static Block blockCodeBreak;
	public static Block blockCodeEnd;
	
	public static void init() {
		GameRegistry.registerBlock(blockRobot = new BlockRobot("robot"), blockRobot.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeStart = new BlockCodeStart(blockCodeUnlocalizedNamePrefix + "start"), blockCodeStart.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeMove = new BlockCodeMove(blockCodeUnlocalizedNamePrefix + "move"), blockCodeMove.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeRotate = new BlockCodeRotate(blockCodeUnlocalizedNamePrefix + "rotate"), blockCodeRotate.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeIf = new BlockCodeIf(blockCodeUnlocalizedNamePrefix + "if"), blockCodeIf.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeIfElse = new BlockCodeIfElse(blockCodeUnlocalizedNamePrefix + "if_else"), blockCodeIfElse.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeElse = new BlockCodeElse(blockCodeUnlocalizedNamePrefix + "else"), blockCodeElse.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeIfEnd = new BlockCodeIfEnd(blockCodeUnlocalizedNamePrefix + "if_end"), blockCodeIfEnd.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeFor = new BlockCodeFor(blockCodeUnlocalizedNamePrefix + "for"), blockCodeFor.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeForEnd = new BlockCodeForEnd(blockCodeUnlocalizedNamePrefix + "for_end"), blockCodeForEnd.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodePlace = new BlockCodePlace(blockCodeUnlocalizedNamePrefix + "place"), blockCodePlace.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeBreak = new BlockCodeBreak(blockCodeUnlocalizedNamePrefix + "break"), blockCodeBreak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(blockCodeEnd = new BlockCodeEnd(blockCodeUnlocalizedNamePrefix + "end"), blockCodeEnd.getUnlocalizedName().substring(5));
	}
	
	public static final CreativeTabs tabBlocks = new CreativeTabs("blocks") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(blockCodeStart);
		}
	};
}
