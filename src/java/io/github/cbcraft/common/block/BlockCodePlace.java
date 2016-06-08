package io.github.cbcraft.common.block;

import io.github.cbcraft.CBCraft;
import io.github.cbcraft.common.item.Items;
import io.github.cbcraft.common.tileentity.TileEntityCodePlace;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockCodePlace extends BlockCode {
	public BlockCodePlace(String unlocalizedName) {
		super(unlocalizedName);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCodePlace();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			TileEntityCodePlace tileEntityCodePlace = (TileEntityCodePlace)worldIn.getTileEntity(pos);
			
			if(playerIn.inventory.getCurrentItem() == null) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted(CBCraft.MODID + ".blockCode.place.paramter.direction", tileEntityCodePlace.getBlockParamterDirectionTranslate())));
				if(tileEntityCodePlace.getBlockParamter("block").equals("minecraft:air")) {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.place.paramter.block.unset")));
				}
				else {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted(CBCraft.MODID + ".blockCode.place.paramter.block", Block.getBlockFromName(tileEntityCodePlace.getBlockParamter("block")).getLocalizedName())));
				}
			}
			else if(playerIn.inventory.getCurrentItem().getItem() == Items.itemWrench) {
				switch(tileEntityCodePlace.getBlockParamter("direction")) {
					case "front":
						tileEntityCodePlace.setBlockParamter("direction", "up");
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.place.paramter.direction.up")));
						break;
					case "up":
						tileEntityCodePlace.setBlockParamter("direction", "down");
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.place.paramter.direction.down")));
						break;
					case "down":
						tileEntityCodePlace.setBlockParamter("direction", "front");
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.place.paramter.direction.front")));
						break;
				}
			}
			else if(playerIn.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
				tileEntityCodePlace.setBlockParamter("block", Block.getBlockFromItem(playerIn.inventory.getCurrentItem().getItem()).getRegistryName());
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted(CBCraft.MODID + ".blockCode.place.paramter.block.set", Block.getBlockFromName(tileEntityCodePlace.getBlockParamter("block")).getLocalizedName())));
			}
		}
		
		return true;
	}
}
