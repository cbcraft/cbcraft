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

package io.github.cbcraft.client.render;

import io.github.cbcraft.CBCraft;
import io.github.cbcraft.common.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class RenderBlocks {
	public static void init() {
		register(Blocks.blockRobot);
		register(Blocks.blockCodeStart);
		register(Blocks.blockCodeMove);
		register(Blocks.blockCodeRotate);
		register(Blocks.blockCodeIf);
		register(Blocks.blockCodeIfElse);
		register(Blocks.blockCodeElse);
		register(Blocks.blockCodeIfEnd);
		register(Blocks.blockCodeFor);
		register(Blocks.blockCodeForEnd);
		register(Blocks.blockCodePlace);
		register(Blocks.blockCodeBreak);
		register(Blocks.blockCodeEnd);
	}
	
	public static void register(Block block){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(CBCraft.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
}
