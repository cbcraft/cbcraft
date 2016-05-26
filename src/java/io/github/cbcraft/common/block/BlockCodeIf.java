package io.github.cbcraft.common.block;

import org.lwjgl.input.Keyboard;

import io.github.cbcraft.common.item.Items;
import io.github.cbcraft.common.tileentity.TileEntityCodeIf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
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
		TileEntityCodeIf tileEntityCodeIf = (TileEntityCodeIf)worldIn.getTileEntity(pos);
		
		if(!worldIn.isRemote) {
			if(playerIn.inventory.getCurrentItem() == null) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("If Paramter: If condition is set to '" + tileEntityCodeIf.getBlockParamter("condition") + "'"));
				switch(tileEntityCodeIf.getBlockParamter("condition")) {
					case "block":
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("If Paramter: If direction is set to '" + tileEntityCodeIf.getBlockParamter("direction") + "'"));
						break;
					case "air":
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("If Paramter: If direction is set to '" + tileEntityCodeIf.getBlockParamter("direction") + "'"));
						break;
				}
			}
			else if(playerIn.inventory.getCurrentItem().getItem() == Items.itemWrench) {
				if(Keyboard.isKeyDown(Keyboard.KEY_M)) {
					switch(tileEntityCodeIf.getBlockParamter("condition")) {
						case "block":
							switch(tileEntityCodeIf.getBlockParamter("direction")) {
								case "front":
									tileEntityCodeIf.setBlockParamter("direction", "up");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("If Paramter: If direction has been set to 'up'"));
									break;
								case "up":
									tileEntityCodeIf.setBlockParamter("direction", "down");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("If Paramter: If direction has been set to 'down'"));
									break;
								case "down":
									tileEntityCodeIf.setBlockParamter("direction", "front");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("If Paramter: If direction has been set to 'front'"));
									break;
							}
							break;
						case "air":
							switch(tileEntityCodeIf.getBlockParamter("direction")) {
								case "front":
									tileEntityCodeIf.setBlockParamter("direction", "up");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("If Paramter: If direction has been set to 'up'"));
									break;
								case "up":
									tileEntityCodeIf.setBlockParamter("direction", "down");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("If Paramter: If direction has been set to 'down'"));
									break;
								case "down":
									tileEntityCodeIf.setBlockParamter("direction", "front");
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("If Paramter: If direction has been set to 'front'"));
									break;
							}
							break;
					}
				}
				else {
					switch(tileEntityCodeIf.getBlockParamter("condition")) {
						case "block":
							tileEntityCodeIf.setBlockParamter("condition", "air");
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("If Paramter: If condition has been set to 'air'"));
							break;
						case "air":
							tileEntityCodeIf.setBlockParamter("condition", "block");
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("If Paramter: If condition has been set to 'block'"));
							break;
					}
				}
			}
		}
		
		return true;
	}
}
