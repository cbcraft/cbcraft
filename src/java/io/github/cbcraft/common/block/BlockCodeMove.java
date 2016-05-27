package io.github.cbcraft.common.block;

import io.github.cbcraft.common.item.Items;
import io.github.cbcraft.common.tileentity.TileEntityCodeMove;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockCodeMove extends BlockCode {
	public BlockCodeMove(String unlocalizedName) {
		super(unlocalizedName);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCodeMove();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityCodeMove tileEntityCodeMove = (TileEntityCodeMove)worldIn.getTileEntity(pos);
		
		if(!worldIn.isRemote) {
			if(playerIn.inventory.getCurrentItem() == null) {
				//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Move Paramter: Move direction is set to '" + tileEntityCodeMove.getBlockParamter() + "'"));
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Mover Parametro: Mover direcao esta definida para '" + tileEntityCodeMove.getBlockParamter() + "'"));
			}
			else if(playerIn.inventory.getCurrentItem().getItem() == Items.itemWrench) {
				switch(tileEntityCodeMove.getBlockParamter()) {
					case "front":
						tileEntityCodeMove.setBlockParamter("up");
						//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Move Paramter: Move direction has been set to 'up'"));
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Mover Parametro: Mover direcao foi definida para 'up'"));
						break;
					case "up":
						tileEntityCodeMove.setBlockParamter("down");
						//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Move Paramter: Move direction has been set to 'down'"));
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Mover Parametro: Mover direcao foi definida para 'down'"));
						break;
					case "down":
						tileEntityCodeMove.setBlockParamter("front");
						//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Move Paramter: Move direction has been set to 'front'"));
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Mover Parametro: Mover direcao foi definida para 'front'"));
						break;
				}
			}
		}
		
		return false;
	}
}
