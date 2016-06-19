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

import io.github.cbcraft.CBCraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class TileEntityCodeRotate extends TileEntityCode {
	private String blockParamter = "right";
	
	public void setBlockParamter(String paramter) {
		blockParamter = paramter;
	}
	
	public String getBlockParamter() {
		return blockParamter;
	}
	
	public String getBlockParamterTranslate() {
		switch(blockParamter) {
			case "right":
				return StatCollector.translateToLocal(CBCraft.MODID + ".paramter.direction.right");
			case "left":
				return StatCollector.translateToLocal(CBCraft.MODID + ".paramter.direction.left");
			default:
				return blockParamter;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setString("paramter", blockParamter);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int NBT_STRING_ID = 8; // Values can be found on NBTBase.createNewByType()
		if(compound.hasKey("paramter", NBT_STRING_ID)) {
			blockParamter = compound.getString("paramter");
		}
	}
}
