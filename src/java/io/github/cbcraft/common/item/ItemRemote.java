package io.github.cbcraft.common.item;

import java.util.List;

import io.github.cbcraft.common.block.BlockCode;
import io.github.cbcraft.common.tileentity.TileEntityCodeStart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
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
					final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
					NBTTagCompound blockPos = itemStackIn.getTagCompound().getCompoundTag("pos");
					if(!blockPos.hasKey("x", NBT_INT_ID) || !blockPos.hasKey("y", NBT_INT_ID) || !blockPos.hasKey("z", NBT_INT_ID)) {
						//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while attempting to execute the code"));
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Erro: Ocorreu um erro ao tentar executar o codigo"));
						
						return itemStackIn;
					}
					
					BlockPos blockCodeStartPos = new BlockPos(blockPos.getInteger("x"), blockPos.getInteger("y"), blockPos.getInteger("z"));
					
					TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)worldIn.getTileEntity(blockCodeStartPos);
					if(tileEntityCodeStart != null) {
						if(tileEntityCodeStart.getBlockCodeRun()) {
							tileEntityCodeStart.setBlockCodeRun(false);
							BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
							
							//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Forced end"));
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Finalizacao forcada"));
						}
						else {
							if(tileEntityCodeStart.getBlockCodeReady()) {
								if(tileEntityCodeStart.getBlockLinked()) {
									BlockCode.setBlockStatusRun(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
									
									//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Start"));
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Iniciado"));
								}
								else {
									//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: There is no linked Robot block"));
									Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Nao esta conetado nenhum bloco de robo"));
								}
							}
						}
					}
					else {
						if(!worldIn.isRemote) {
							//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: There is no linked Robot block"));
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Nao esta conetado nenhum bloco de robo"));
						}
					}
				}
			}
		}
		
		return itemStackIn;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
		final int NBT_TAG_ID = 10; // Values can be found on NBTBase.createNewByType()
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("linked", NBT_BOOLEAN_ID) && stack.getTagCompound().hasKey("pos", NBT_TAG_ID)) {
			if(stack.getTagCompound().getBoolean("linked")) {
				final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
				NBTTagCompound blockPos = stack.getTagCompound().getCompoundTag("pos");
				if(!blockPos.hasKey("x", NBT_INT_ID) || !blockPos.hasKey("y", NBT_INT_ID) || !blockPos.hasKey("z", NBT_INT_ID)) {
					//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while displaying remote info"));
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Erro: Ocorreu um erro ao mostrar informacao do controle remoto"));
					
					return;
				}
				
				BlockPos blockCodeStartPos = new BlockPos(blockPos.getInteger("x"), blockPos.getInteger("y"), blockPos.getInteger("z"));
				
				TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)playerIn.getEntityWorld().getTileEntity(blockCodeStartPos);
				if(tileEntityCodeStart == null) {
					//tooltip.add(EnumChatFormatting.RED + "Unlinked");
					//tooltip.add(EnumChatFormatting.UNDERLINE + "Right click on Robot block to start link");
					tooltip.add(EnumChatFormatting.RED + "Desconetado");
					tooltip.add(EnumChatFormatting.UNDERLINE + "Clique com o botao direito no bloco do Robo para iniciar a conexao");
					
					return;
				}
				/*else if(!tileEntityCodeStart.getBlockLinked()) {
					tooltip.add(EnumChatFormatting.RED + "Unlinked");
					tooltip.add(EnumChatFormatting.UNDERLINE + "Right click on Robot block to start link");
					
					return;
				}*/
				
				//tooltip.add(EnumChatFormatting.GREEN + "Linked");
				tooltip.add(EnumChatFormatting.GREEN + "Conetado");
				
				if(GuiScreen.isShiftKeyDown()) {
					//tooltip.add("Link Coordinates:");
					tooltip.add("Conexao Coordenadas:");
					tooltip.add("  X = " + blockCodeStartPos.getX());
					tooltip.add("  Y = " + blockCodeStartPos.getY());
					tooltip.add("  Z = " + blockCodeStartPos.getZ());
				}
				else {
					//tooltip.add(EnumChatFormatting.UNDERLINE + "<<Press SHIFT For Link Coordinates>>");
					tooltip.add(EnumChatFormatting.UNDERLINE + "<<Pressione SHIFT para ver as coordenadas>>");
				}
				
				/*if(tileEntityCodeStart.getBlockCodeRun()) {
					tooltip.add(EnumChatFormatting.GREEN + "Running");
				}
				else {
					tooltip.add(EnumChatFormatting.RED + "Stopped");
				}*/
			}
			else {
				//tooltip.add(EnumChatFormatting.YELLOW + "Waiting Link");
				//tooltip.add(EnumChatFormatting.UNDERLINE + "Right click on Code Start block to link");
				tooltip.add(EnumChatFormatting.YELLOW + "Aguardando Conexao");
				tooltip.add(EnumChatFormatting.UNDERLINE + "Clique com o botao direito no bloco de codigo de Inicio para conetar");
			}
		}
		else {
			//tooltip.add(EnumChatFormatting.RED + "Unlinked");
			//tooltip.add(EnumChatFormatting.UNDERLINE + "Right click on Robot block to start link");
			tooltip.add(EnumChatFormatting.RED + "Desconetado");
			tooltip.add(EnumChatFormatting.UNDERLINE + "Clique com o botao direito no bloco do Robo para iniciar a conexao");
		}
	}
}
