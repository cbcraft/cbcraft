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

import java.util.List;

import org.lwjgl.input.Keyboard;

import io.github.cbcraft.CBCraft;
import io.github.cbcraft.common.block.BlockCode;
import io.github.cbcraft.common.tileentity.TileEntityCodeStart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRemote extends Item {
	public ItemRemote(String unlocalizedName) {
		super();
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(Items.tabItems);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote) {
			if(playerIn.isSneaking()) {
				final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
				final int NBT_TAG_ID = 10; // Values can be found on NBTBase.createNewByType()
				if(itemStackIn.hasTagCompound() && itemStackIn.getTagCompound().hasKey("linked", NBT_BOOLEAN_ID) && itemStackIn.getTagCompound().hasKey("pos", NBT_TAG_ID)) {
					if(itemStackIn.getTagCompound().getBoolean("linked")) {
						final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
						NBTTagCompound blockPos = itemStackIn.getTagCompound().getCompoundTag("pos");
						if(!blockPos.hasKey("x", NBT_INT_ID) || !blockPos.hasKey("y", NBT_INT_ID) || !blockPos.hasKey("z", NBT_INT_ID)) {
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".error.attemptExecCode")));
							
							return itemStackIn;
						}
						
						BlockPos blockCodeStartPos = new BlockPos(blockPos.getInteger("x"), blockPos.getInteger("y"), blockPos.getInteger("z"));
						
						TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)worldIn.getTileEntity(blockCodeStartPos);
						if(tileEntityCodeStart != null) {
							if(tileEntityCodeStart.getBlockCodeRun()) {
								tileEntityCodeStart.setBlockCodeRun(false);
								BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
								
								Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".exec.forcedEnd")));
							}
							else {
								BlockCode.checkBlockCode(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
								
								if(tileEntityCodeStart.getBlockCodeReady()) {
									if(tileEntityCodeStart.getBlockLinked()) {
										BlockCode.setBlockStatusRun(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
										
										Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".exec.start")));
									}
									else {
										Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".exec.missingLink")));
									}
								}
							}
						}
						else {
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".exec.missingLink")));
						}
					}
				}
			}
		}
				
		return itemStackIn;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
		final int NBT_TAG_ID = 10; // Values can be found on NBTBase.createNewByType()
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("linked", NBT_BOOLEAN_ID) && stack.getTagCompound().hasKey("pos", NBT_TAG_ID)) {
			if(stack.getTagCompound().getBoolean("linked")) {
				final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
				NBTTagCompound blockPos = stack.getTagCompound().getCompoundTag("pos");
				if(!blockPos.hasKey("x", NBT_INT_ID) || !blockPos.hasKey("y", NBT_INT_ID) || !blockPos.hasKey("z", NBT_INT_ID)) {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".error.remoteTooltip")));
					
					return;
				}
				
				BlockPos blockCodeStartPos = new BlockPos(blockPos.getInteger("x"), blockPos.getInteger("y"), blockPos.getInteger("z"));
				
				TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)playerIn.getEntityWorld().getTileEntity(blockCodeStartPos);
				if(tileEntityCodeStart == null) {
					tooltip.add(EnumChatFormatting.RED + StatCollector.translateToLocal(CBCraft.MODID + ".remote.tooltip.unlinked"));
					tooltip.add(EnumChatFormatting.UNDERLINE + StatCollector.translateToLocal(CBCraft.MODID + ".remote.tooltip.startLinkHelp"));
					
					return;
				}
				
				if(!tileEntityCodeStart.getBlockLinked()) {
					tooltip.add(EnumChatFormatting.RED + StatCollector.translateToLocal(CBCraft.MODID + ".remote.tooltip.unlinked"));
					tooltip.add(EnumChatFormatting.UNDERLINE + StatCollector.translateToLocal(CBCraft.MODID + ".remote.tooltip.startLinkHelp"));
					
					return;
				}
				
				tooltip.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal(CBCraft.MODID + ".remote.tooltip.linked"));
				
				if(GuiScreen.isShiftKeyDown()) {
					tooltip.add(StatCollector.translateToLocal(CBCraft.MODID + ".remote.tooltip.linkCoordinates"));
					tooltip.add("  X = " + blockCodeStartPos.getX());
					tooltip.add("  Y = " + blockCodeStartPos.getY());
					tooltip.add("  Z = " + blockCodeStartPos.getZ());
				}
				else {
					tooltip.add(EnumChatFormatting.UNDERLINE + StatCollector.translateToLocal(CBCraft.MODID + ".remote.tooltip.linkCoordinatesHelp").replaceAll("%key%", EnumChatFormatting.ITALIC + Keyboard.getKeyName(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode()) + EnumChatFormatting.UNDERLINE));
				}
				
				if(tileEntityCodeStart.getBlockCodeRun()) {
					tooltip.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal(CBCraft.MODID + ".remote.tooltip.running"));
				}
				else {
					tooltip.add(EnumChatFormatting.RED + StatCollector.translateToLocal(CBCraft.MODID + ".remote.tooltip.stopped"));
				}
			}
			else {
				tooltip.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal(CBCraft.MODID + ".remote.tooltip.waitingLink"));
				tooltip.add(EnumChatFormatting.UNDERLINE + StatCollector.translateToLocal(CBCraft.MODID + ".remote.tooltip.finishLinkHelp"));
			}
		}
		else {
			tooltip.add(EnumChatFormatting.RED + StatCollector.translateToLocal(CBCraft.MODID + ".remote.tooltip.unlinked"));
			tooltip.add(EnumChatFormatting.UNDERLINE + StatCollector.translateToLocal(CBCraft.MODID + ".remote.tooltip.startLinkHelp"));
		}
	}
}
