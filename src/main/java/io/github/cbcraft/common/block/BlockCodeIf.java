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

import org.lwjgl.input.Keyboard;

import io.github.cbcraft.CBCraft;
import io.github.cbcraft.client.ClientKeyBindings;
import io.github.cbcraft.common.item.Items;
import io.github.cbcraft.common.tileentity.TileEntityCodeIf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockCodeIf extends BlockCode {
	public BlockCodeIf(String unlocalizedName) {
		super(unlocalizedName);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCodeIf();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			TileEntityCodeIf tileEntityCodeIf = (TileEntityCodeIf)worldIn.getTileEntity(pos);
			
			if(playerIn.inventory.getCurrentItem() == null) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted(CBCraft.MODID + ".blockCode.if.paramter.condition", tileEntityCodeIf.getBlockParamterConditionTranslate())));
				switch(tileEntityCodeIf.getBlockParamter("condition")) {
					case "block":
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted(CBCraft.MODID + ".blockCode.if.paramter.direction", tileEntityCodeIf.getBlockParamterDirectionTranslate())));
						break;
					case "air":
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted(CBCraft.MODID + ".blockCode.if.paramter.direction", tileEntityCodeIf.getBlockParamterDirectionTranslate())));
						break;
				}
			}
			else if(playerIn.inventory.getCurrentItem().getItem() == Items.itemWrench) {
				if(Keyboard.isKeyDown(ClientKeyBindings.secondParamter.getKeyCode())) {
					switch(tileEntityCodeIf.getBlockParamter("condition")) {
						case "block":
							switch(tileEntityCodeIf.getBlockParamter("direction")) {
								case "front":
									tileEntityCodeIf.setBlockParamter("direction", "up");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.if.paramter.direction.up")));
									break;
								case "up":
									tileEntityCodeIf.setBlockParamter("direction", "down");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.if.paramter.direction.down")));
									break;
								case "down":
									tileEntityCodeIf.setBlockParamter("direction", "front");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.if.paramter.direction.front")));
									break;
							}
							break;
						case "air":
							switch(tileEntityCodeIf.getBlockParamter("direction")) {
								case "front":
									tileEntityCodeIf.setBlockParamter("direction", "up");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.if.paramter.direction.up")));
									break;
								case "up":
									tileEntityCodeIf.setBlockParamter("direction", "down");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.if.paramter.direction.down")));
									break;
								case "down":
									tileEntityCodeIf.setBlockParamter("direction", "front");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.if.paramter.direction.front")));
									break;
							}
							break;
					}
				}
				else {
					switch(tileEntityCodeIf.getBlockParamter("condition")) {
						case "block":
							tileEntityCodeIf.setBlockParamter("condition", "air");
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.if.paramter.condition.air")));
							break;
						case "air":
							tileEntityCodeIf.setBlockParamter("condition", "block");
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.if.paramter.condition.block")));
							break;
					}
				}
			}
		}
		
		return false;
	}
}
