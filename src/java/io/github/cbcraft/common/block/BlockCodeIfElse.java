package io.github.cbcraft.common.block;

import org.lwjgl.input.Keyboard;

import io.github.cbcraft.common.item.Items;
import io.github.cbcraft.common.tileentity.TileEntityCodeIfElse;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockCodeIfElse extends BlockCode {
	public BlockCodeIfElse(String unlocalizedName) {
		super(unlocalizedName);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCodeIfElse();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityCodeIfElse tileEntityCodeIfElse = (TileEntityCodeIfElse)worldIn.getTileEntity(pos);
		
		if(!worldIn.isRemote) {
			if(playerIn.inventory.getCurrentItem() == null) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("IfElse Paramter: IfElse condition is set to '" + tileEntityCodeIfElse.getBlockParamter("condition") + "'"));
				switch(tileEntityCodeIfElse.getBlockParamter("condition")) {
					case "block":
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("IfElse Paramter: IfElse direction is set to '" + tileEntityCodeIfElse.getBlockParamter("direction") + "'"));
						break;
				}
			}
			else if(playerIn.inventory.getCurrentItem().getItem() == Items.itemWrench) {
				if(Keyboard.isKeyDown(Keyboard.KEY_M)) {
					switch(tileEntityCodeIfElse.getBlockParamter("condition")) {
						case "block":
							switch(tileEntityCodeIfElse.getBlockParamter("direction")) {
								case "front":
									tileEntityCodeIfElse.setBlockParamter("direction", "up");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("IfElse Paramter: IfElse direction has been set to 'up'"));
									break;
								case "up":
									tileEntityCodeIfElse.setBlockParamter("direction", "down");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("IfElse Paramter: IfElse direction has been set to 'down'"));
									break;
								case "down":
									tileEntityCodeIfElse.setBlockParamter("direction", "front");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("IfElse Paramter: IfElse direction has been set to 'front'"));
									break;
							}
							break;
					}
				}
				else {
					switch(tileEntityCodeIfElse.getBlockParamter("condition")) {
						case "block":
							tileEntityCodeIfElse.setBlockParamter("condition", "block");
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("IfElse Paramter: IfElse condition has been set to 'block'"));
							break;
					}
				}
			}
		}
		
		return true;
	}
}
