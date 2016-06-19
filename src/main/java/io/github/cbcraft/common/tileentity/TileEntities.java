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

package io.github.cbcraft.common.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntities {
	public static void init() {
		GameRegistry.registerTileEntity(TileEntityRobot.class, "robot");
		GameRegistry.registerTileEntity(TileEntityCodeStart.class, "code_start");
		GameRegistry.registerTileEntity(TileEntityCodeBreak.class, "code_break");
		GameRegistry.registerTileEntity(TileEntityCodeMove.class, "code_move");
		GameRegistry.registerTileEntity(TileEntityCodePlace.class, "code_place");
		GameRegistry.registerTileEntity(TileEntityCodeRotate.class, "code_rotate");
		GameRegistry.registerTileEntity(TileEntityCodeFor.class, "code_for");
		GameRegistry.registerTileEntity(TileEntityCodeForEnd.class, "code_for_end");
		GameRegistry.registerTileEntity(TileEntityCodeIf.class, "code_if");
		GameRegistry.registerTileEntity(TileEntityCodeIfElse.class, "code_if_else");
		GameRegistry.registerTileEntity(TileEntityCodeElse.class, "code_else");
		GameRegistry.registerTileEntity(TileEntityCode.class, "code_if_end");
		GameRegistry.registerTileEntity(TileEntityCode.class, "code_end");
	}
}
