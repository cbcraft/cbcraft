package io.github.cbcraft.common.block;

import io.github.cbcraft.common.item.Items;
import io.github.cbcraft.common.tileentity.TileEntityCodeRotate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockCodeRotate extends BlockCode {
	public BlockCodeRotate(String unlocalizedName) {
		super(unlocalizedName);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCodeRotate();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityCodeRotate tileEntityCodeRotate = (TileEntityCodeRotate)worldIn.getTileEntity(pos);
		
		if(!worldIn.isRemote) {
			if(playerIn.inventory.getCurrentItem() == null) {
				//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Rotate Paramter: Rotate direction is set to '" + tileEntityCodeRotate.getBlockParamter() + "'"));
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Rodar Parâmetro: Rodar direção está definida para '" + tileEntityCodeRotate.getBlockParamter() + "'"));
			}
			else if(playerIn.inventory.getCurrentItem().getItem() == Items.itemWrench) {
				switch(tileEntityCodeRotate.getBlockParamter()) {
					case "right":
						tileEntityCodeRotate.setBlockParamter("left");
						//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Rotate Paramter: Rotate direction has been set to 'left'"));
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Rodar Parâmetro: Rodar direção foi definida para 'left'"));
						break;
					case "left":
						tileEntityCodeRotate.setBlockParamter("right");
						//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Rotate Paramter: Rotate direction has been set to 'right'"));
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Rodar Parâmetro: Rodar direção foi definida para 'right'"));
						break;
				}
			}
		}
		
		return false;
	}
}
