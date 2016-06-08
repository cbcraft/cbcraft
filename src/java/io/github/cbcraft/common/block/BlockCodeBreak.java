package io.github.cbcraft.common.block;

import io.github.cbcraft.CBCraft;
import io.github.cbcraft.common.item.Items;
import io.github.cbcraft.common.tileentity.TileEntityCodeBreak;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockCodeBreak extends BlockCode {
	public BlockCodeBreak(String unlocalizedName) {
		super(unlocalizedName);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCodeBreak();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			TileEntityCodeBreak tileEntityCodeBreak = (TileEntityCodeBreak)worldIn.getTileEntity(pos);
			
			if(playerIn.inventory.getCurrentItem() == null) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted(CBCraft.MODID + ".blockCode.break.paramter.direction", tileEntityCodeBreak.getBlockParamterTranslate())));
			}
			else if(playerIn.inventory.getCurrentItem().getItem() == Items.itemWrench) {
				switch(tileEntityCodeBreak.getBlockParamter()) {
					case "front":
						tileEntityCodeBreak.setBlockParamter("up");
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.break.paramter.direction.up")));
						break;
					case "up":
						tileEntityCodeBreak.setBlockParamter("down");
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.break.paramter.direction.down")));
						break;
					case "down":
						tileEntityCodeBreak.setBlockParamter("front");
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.break.paramter.direction.front")));
						break;
				}
			}
		}
		
		return false;
	}
}
