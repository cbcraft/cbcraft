package io.github.cbcraft.common.block;
import org.lwjgl.input.Keyboard;

import io.github.cbcraft.common.item.Items;
import io.github.cbcraft.common.tileentity.TileEntityCodeFor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockCodeFor extends BlockCode {
	public BlockCodeFor(String unlocalizedName) {
		super(unlocalizedName);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCodeFor();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityCodeFor tileEntityCodeFor = (TileEntityCodeFor)worldIn.getTileEntity(pos);
		
		if(!worldIn.isRemote) {
			if(playerIn.inventory.getCurrentItem() == null) {
				if(tileEntityCodeFor.getBlockParamter() == 0) {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("For Paramter: For loop is set to 'infinite'"));
				}
				else {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("For Paramter: For loop is set to '" + tileEntityCodeFor.getBlockParamter() + "'"));
				}
			}
			else if(playerIn.inventory.getCurrentItem().getItem() == Items.itemWrench) {
				if(Keyboard.isKeyDown(Keyboard.KEY_ADD) || Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
					tileEntityCodeFor.setBlockParamter(tileEntityCodeFor.getBlockParamter() + 1);
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("For Paramter: For loop has been set to '" + tileEntityCodeFor.getBlockParamter() + "'"));
				}
				else if(Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT) || Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
					if(tileEntityCodeFor.getBlockParamter() > 1) {
						tileEntityCodeFor.setBlockParamter(tileEntityCodeFor.getBlockParamter() - 1);
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("For Paramter: For loop has been set to '" + tileEntityCodeFor.getBlockParamter() + "'"));
					}
					else {
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("For Paramter: For loop can not be set under '1'"));
					}
				}
				else {
					tileEntityCodeFor.setBlockParamter(0);
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("For Paramter: For loop has been set to 'infinite'"));
				}
			}
		}
		
		return false;
	}
}
