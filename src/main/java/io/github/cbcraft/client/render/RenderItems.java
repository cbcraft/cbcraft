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
import io.github.cbcraft.common.item.Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class RenderItems {
	public static void init() {
		register(Items.itemWrench);
		register(Items.itemRemote);
	}
	
	public static void register(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(CBCraft.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
