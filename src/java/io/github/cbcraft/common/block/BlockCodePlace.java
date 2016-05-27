package io.github.cbcraft.common.block;

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
		TileEntityCodePlace tileEntityCodePlace = (TileEntityCodePlace)worldIn.getTileEntity(pos);
		
		if(!worldIn.isRemote) {
			if(playerIn.inventory.getCurrentItem() == null) {
				//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Place Block Paramter: Place direction is set to '" + tileEntityCodePlace.getBlockParamter("direction") + "'"));
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Colocar Bloco Parametro: Colocar direcao esta definida para '" + tileEntityCodePlace.getBlockParamter("direction") + "'"));
				if(tileEntityCodePlace.getBlockParamter("block") == "minecraft:air") {
					//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Place Block Paramter: Place block is not set"));
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Colocar Bloco Parametro: Colocar bloco nao esta definido"));
				}
				else {
					//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Place Paramter: Place block is set to '" + Block.getBlockFromName(tileEntityCodePlace.getBlockParamter("block")).getLocalizedName() + "'"));
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Colocar Bloco Parametro: Colocar bloco esta definido para '" + Block.getBlockFromName(tileEntityCodePlace.getBlockParamter("block")).getLocalizedName() + "'"));
				}
			}
			else if(playerIn.inventory.getCurrentItem().getItem() == Items.itemWrench) {
				switch(tileEntityCodePlace.getBlockParamter("direction")) {
					case "front":
						tileEntityCodePlace.setBlockParamter("direction", "up");
						//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Place Paramter: Place direction has been set to 'up'"));
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Colocar Bloco Parametro: Colocar direcao foi definida para 'up'"));
						break;
					case "up":
						tileEntityCodePlace.setBlockParamter("direction", "down");
						//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Place Paramter: Place direction has been set to 'down'"));
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Colocar Bloco Parametro: Colocar direcao foi definida para 'down'"));
						break;
					case "down":
						tileEntityCodePlace.setBlockParamter("direction", "front");
						//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Place Paramter: Place direction has been set to 'front'"));
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Colocar Bloco Parametro: Colocar direcao foi definida para 'front'"));
						break;
				}
			}
			else if(playerIn.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
				tileEntityCodePlace.setBlockParamter("block", Block.getBlockFromItem(playerIn.inventory.getCurrentItem().getItem()).getRegistryName());
				//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Place Paramter: Place block has been set to '" + Block.getBlockFromName(tileEntityCodePlace.getBlockParamter("block")).getLocalizedName() + "'"));
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Colocar Bloco Parametro: Colocar bloco foi definido para '" + Block.getBlockFromName(tileEntityCodePlace.getBlockParamter("block")).getLocalizedName() + "'"));
			}
		}
		
		return true;
	}
}
