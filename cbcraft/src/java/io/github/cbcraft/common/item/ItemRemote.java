package io.github.cbcraft.common.item;

import java.util.List;

import io.github.cbcraft.common.block.BlockCode;
import io.github.cbcraft.common.tileentity.TileEntityCodeStart;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
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
		if(playerIn.isSneaking()) {
			final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
			final int NBT_TAG_ID = 10; // Values can be found on NBTBase.createNewByType()
			if(itemStackIn.hasTagCompound() && itemStackIn.getTagCompound().hasKey("linked", NBT_BOOLEAN_ID) && itemStackIn.getTagCompound().hasKey("pos", NBT_TAG_ID)) {
				if(itemStackIn.getTagCompound().getBoolean("linked")) {
					BlockPos blockCodeStartPos = null;
					
					final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
					NBTTagCompound blockPos = itemStackIn.getTagCompound().getCompoundTag("pos");
					if(blockPos.hasKey("x", NBT_INT_ID) && blockPos.hasKey("y", NBT_INT_ID) && blockPos.hasKey("z", NBT_INT_ID)) {
						blockCodeStartPos = new BlockPos(blockPos.getInteger("x"), blockPos.getInteger("y"), blockPos.getInteger("z"));
					}
					
					TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)worldIn.getTileEntity(blockCodeStartPos);
					if(tileEntityCodeStart != null) {
						if(tileEntityCodeStart.getBlockCodeRun()) {
							tileEntityCodeStart.setBlockCodeRun(false);
							BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
							
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Forced end"));
						}
						else {
							if(tileEntityCodeStart.getBlockCodeReady()) {
								if(tileEntityCodeStart.getBlockLinked()) {
									BlockCode.setBlockStatusRun(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
									
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Start"));
								}
								else {
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: There is no linked Robot block"));
								}
							}
						}
					}
					else {
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while attempting to execute the code"));
					}
				}
			}
		}
		
		/*if(Keyboard.isKeyDown(Keyboard.KEY_C)) {
			NBTTagCompound nbtTagCompound = new NBTTagCompound();
			itemStackIn.setTagCompound(nbtTagCompound);
		}*/
		
		return itemStackIn;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
		final int NBT_TAG_ID = 10; // Values can be found on NBTBase.createNewByType()
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("linked", NBT_BOOLEAN_ID) && stack.getTagCompound().hasKey("pos", NBT_TAG_ID)) {
			if(stack.getTagCompound().getBoolean("linked")) {
				tooltip.add(EnumChatFormatting.GREEN + "Linked");
			}
			else {
				tooltip.add(EnumChatFormatting.YELLOW + "Waiting Link");
				tooltip.add(EnumChatFormatting.UNDERLINE + "Right click on Code Start block to link");
			}
		}
		else {
			tooltip.add(EnumChatFormatting.RED + "Unlinked");
			tooltip.add(EnumChatFormatting.UNDERLINE + "Right click on Robot block to start link");
		}
		
		/*final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
		final int NBT_TAG_ID = 10; // Values can be found on NBTBase.createNewByType()
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("linked", NBT_BOOLEAN_ID) && stack.getTagCompound().hasKey("pos", NBT_TAG_ID)) {
			if(stack.getTagCompound().getBoolean("linked")) {
				BlockPos blockCodeStartPos = null;
				
				tooltip.add(EnumChatFormatting.GREEN + "Linked");
				
				if(GuiScreen.isShiftKeyDown()) {
					final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
					NBTTagCompound blockPos = stack.getTagCompound().getCompoundTag("pos");
					if(blockPos.hasKey("x", NBT_INT_ID) && blockPos.hasKey("y", NBT_INT_ID) && blockPos.hasKey("z", NBT_INT_ID)) {
						tooltip.add("X = " + blockPos.getInteger("x"));
						tooltip.add("Y = " + blockPos.getInteger("y"));
						tooltip.add("Z = " + blockPos.getInteger("z"));
						
						blockCodeStartPos = new BlockPos(blockPos.getInteger("x"), blockPos.getInteger("y"), blockPos.getInteger("z"));
					}
				}
				else {
					tooltip.add(EnumChatFormatting.UNDERLINE + "<<Press SHIFT For Link Coordinates>>");
				}
				
				TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)playerIn.getEntityWorld().getTileEntity(blockCodeStartPos);
				if(tileEntityCodeStart.getBlockCodeRun()) {
					tooltip.add(EnumChatFormatting.GREEN + "Running");
				}
				else {
					tooltip.add(EnumChatFormatting.RED + "Stopped");
				}
			}
			else {
				tooltip.add(EnumChatFormatting.YELLOW + "Ready to Link");
			}
		}
		else {
			tooltip.add(EnumChatFormatting.RED + "Unlinked");
		}*/
	}
}
