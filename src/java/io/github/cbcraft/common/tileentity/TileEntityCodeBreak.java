package io.github.cbcraft.common.tileentity;

import io.github.cbcraft.CBCraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class TileEntityCodeBreak extends TileEntityCode {
	private String blockParamter = "front";
	
	public void setBlockParamter(String paramter) {
		blockParamter = paramter;
	}
	
	public String getBlockParamter() {
		return blockParamter;
	}
	
	public String getBlockParamterTranslate() {
		switch(blockParamter) {
			case "front":
				return StatCollector.translateToLocal(CBCraft.MODID + ".paramter.direction.front");
			case "up":
				return StatCollector.translateToLocal(CBCraft.MODID + ".paramter.direction.up");
			case "down":
				return StatCollector.translateToLocal(CBCraft.MODID + ".paramter.direction.down");
			default:
				return blockParamter;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setString("paramter", blockParamter);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int NBT_STRING_ID = 8; // Values can be found on NBTBase.createNewByType()
		if(compound.hasKey("paramter", NBT_STRING_ID)) {
			blockParamter = compound.getString("paramter");
		}
	}
}
