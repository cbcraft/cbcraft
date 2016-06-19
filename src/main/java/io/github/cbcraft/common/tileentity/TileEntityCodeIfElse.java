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
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;

public class TileEntityCodeIfElse extends TileEntityCode {
	private boolean blockCodeIfElseEnd = false;
	private BlockPos blockCodeIfElseEndPos = new BlockPos(0, 0, 0);
	private String blockParamterCondition = "block";
	private String blockParamterDirection = "front";
	
	public void setBlockCodeIfElseEndPos(BlockPos pos) {
		blockCodeIfElseEnd = true;
		blockCodeIfElseEndPos = pos;
	}
	
	public BlockPos getBlockCodeIfElseEndPos() {
		return blockCodeIfElseEndPos;
	}
	
	public boolean hasBlockCodeIfElseEndPos() {
		return blockCodeIfElseEnd;
	}
	
	public void clearBlockCodeIfElseEndPos() {
		blockCodeIfElseEnd = false;
	}
	
	public void setBlockParamter(String type, String paramter) {
		switch(type) {
			case "condition":
				blockParamterCondition = paramter;
				break;
			case "direction":
				blockParamterDirection = paramter;
				break;
		}
	}
	
	public String getBlockParamter(String type) {
		String stringReturn = null;
		switch(type) {
			case "condition":
				stringReturn = blockParamterCondition;
				break;
			case "direction":
				stringReturn = blockParamterDirection;
				break;
		}
		return stringReturn;
	}
	
	public String getBlockParamterConditionTranslate() {
		switch(blockParamterCondition) {
			case "block":
				return StatCollector.translateToLocal(CBCraft.MODID + ".paramter.condition.block");
			case "air":
				return StatCollector.translateToLocal(CBCraft.MODID + ".paramter.condition.air");
			default:
				return blockParamterCondition;
		}
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
		
		compound.setBoolean("linked", blockCodeIfElseEnd);
		
		NBTTagCompound blockPos = new NBTTagCompound();
		blockPos.setInteger("x", blockCodeIfElseEndPos.getX());
		blockPos.setInteger("y", blockCodeIfElseEndPos.getY());
		blockPos.setInteger("z", blockCodeIfElseEndPos.getZ());
		compound.setTag("pos", blockPos);
		
		NBTTagCompound paramter = new NBTTagCompound();
		paramter.setString("condition", blockParamterCondition);
		paramter.setString("direction", blockParamterDirection);
		compound.setTag("paramter", paramter);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
		if(compound.hasKey("linked", NBT_BOOLEAN_ID)) {
			blockCodeIfElseEnd = compound.getBoolean("linked");
		}
		
		final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
		NBTTagCompound blockPos = compound.getCompoundTag("pos");
		if(blockPos.hasKey("x", NBT_INT_ID) && blockPos.hasKey("y", NBT_INT_ID) && blockPos.hasKey("z", NBT_INT_ID)) {
			blockCodeIfElseEndPos = new BlockPos(blockPos.getInteger("x"), blockPos.getInteger("y"), blockPos.getInteger("z"));
		}
		
		final int NBT_STRING_ID = 8; // Values can be found on NBTBase.createNewByType()
		NBTTagCompound paramterNBT = compound.getCompoundTag("paramter");
		if(paramterNBT.hasKey("direction", NBT_STRING_ID) && paramterNBT.hasKey("condition", NBT_STRING_ID)) {
			blockParamterCondition = paramterNBT.getString("condition");
			blockParamterDirection = paramterNBT.getString("direction");
		}
	}
}
