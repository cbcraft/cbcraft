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

import io.github.cbcraft.CBCraft;
import io.github.cbcraft.common.item.Items;
import io.github.cbcraft.common.tileentity.TileEntityCodeStart;
import io.github.cbcraft.common.tileentity.TileEntityRobot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockCodeStart extends BlockCode {  
	public BlockCodeStart(String unlocalizedName) {
		super(unlocalizedName);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCodeStart();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(playerIn.inventory.getCurrentItem() != null) {
			if(playerIn.inventory.getCurrentItem().getItem() == Items.itemRemote) {
				NBTTagCompound nbtTagCompound = playerIn.inventory.getCurrentItem().getTagCompound();
				
				final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
				final int NBT_TAG_ID = 10; // Values can be found on NBTBase.createNewByType()
				if(nbtTagCompound != null && nbtTagCompound.hasKey("linked", NBT_BOOLEAN_ID) && nbtTagCompound.hasKey("pos", NBT_TAG_ID)) {
					if(!nbtTagCompound.getBoolean("linked")) {
						final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
						NBTTagCompound blockPos = nbtTagCompound.getCompoundTag("pos");
						if(blockPos.hasKey("x", NBT_INT_ID) && blockPos.hasKey("y", NBT_INT_ID) && blockPos.hasKey("z", NBT_INT_ID)) {
							TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)worldIn.getTileEntity(pos);
							if(tileEntityCodeStart != null) {
								BlockPos blockRobotPos = new BlockPos(blockPos.getInteger("x"), blockPos.getInteger("y"), blockPos.getInteger("z"));
								
								TileEntityRobot tileEntityRobot = (TileEntityRobot)worldIn.getTileEntity(blockRobotPos);
								if(tileEntityRobot != null) {
									tileEntityCodeStart.setBlockRobotPos(blockRobotPos);
									tileEntityCodeStart.setBlockLinked(true);
									
									tileEntityRobot.setBlockCodeStartPos(pos);
									
									nbtTagCompound.setBoolean("linked", true);
									
									blockPos = new NBTTagCompound();
									blockPos.setInteger("x", pos.getX());
									blockPos.setInteger("y", pos.getY());
									blockPos.setInteger("z", pos.getZ());
									nbtTagCompound.setTag("pos", blockPos);
									
									if(!worldIn.isRemote) {
										Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".remote.link.complete")));
									}
								}
							}
						}
					}
				}
			}
		}
		
		return false;
	}
}
