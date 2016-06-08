package io.github.cbcraft.common.block;

import io.github.cbcraft.CBCraft;
import io.github.cbcraft.common.item.Items;
import io.github.cbcraft.common.tileentity.TileEntityCodeRotate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
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
		if(!worldIn.isRemote) {
			TileEntityCodeRotate tileEntityCodeRotate = (TileEntityCodeRotate)worldIn.getTileEntity(pos);
			
			if(playerIn.inventory.getCurrentItem() == null) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted(CBCraft.MODID + ".blockCode.rotate.paramter.direction", tileEntityCodeRotate.getBlockParamterTranslate())));
			}
			else if(playerIn.inventory.getCurrentItem().getItem() == Items.itemWrench) {
				switch(tileEntityCodeRotate.getBlockParamter()) {
					case "right":
						tileEntityCodeRotate.setBlockParamter("left");
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.rotate.paramter.direction.left")));
						break;
					case "left":
						tileEntityCodeRotate.setBlockParamter("right");
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(CBCraft.MODID + ".blockCode.rotate.paramter.direction.right")));
						break;
				}
			}
		}
		
		return false;
	}
}
