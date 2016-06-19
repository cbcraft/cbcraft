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

public class TileEntityCodePlace extends TileEntityCode {
	private String blockParamterDirection = "front";
	private String blockParamterBlock = "minecraft:air";
	
	public void setBlockParamter(String type, String paramter) {
		switch(type) {
			case "direction":
				blockParamterDirection = paramter;
				break;
			case "block":
				blockParamterBlock = paramter;
				break;
		}
	}
	
	public String getBlockParamter(String type) {
		String stringReturn = null;
		switch(type) {
			case "direction":
				stringReturn = blockParamterDirection;
				break;
			case "block":
				stringReturn = blockParamterBlock;
				break;
		}
		return stringReturn;
	}
	
	public String getBlockParamterDirectionTranslate() {
		switch(blockParamterDirection) {
			case "front":
				return StatCollector.translateToLocal(CBCraft.MODID + ".paramter.direction.front");
			case "up":
				return StatCollector.translateToLocal(CBCraft.MODID + ".paramter.direction.up");
			case "down":
				return StatCollector.translateToLocal(CBCraft.MODID + ".paramter.direction.down");
			default:
				return blockParamterDirection;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		NBTTagCompound paramter = new NBTTagCompound();
		paramter.setString("direction", blockParamterDirection);
		paramter.setString("block", blockParamterBlock);
		compound.setTag("paramter", paramter);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int NBT_STRING_ID = 8; // Values can be found on NBTBase.createNewByType()
		NBTTagCompound paramterNBT = compound.getCompoundTag("paramter");
		if(paramterNBT.hasKey("direction", NBT_STRING_ID) && paramterNBT.hasKey("block", NBT_STRING_ID)) {
			blockParamterDirection = paramterNBT.getString("direction");
			blockParamterBlock = paramterNBT.getString("block");
		}
	}
}
