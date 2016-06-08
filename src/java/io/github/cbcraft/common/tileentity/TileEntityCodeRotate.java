package io.github.cbcraft.common.tileentity;

import io.github.cbcraft.CBCraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class TileEntityCodeRotate extends TileEntityCode {
	private String blockParamter = "right";
	
	public void setBlockParamter(String paramter) {
		blockParamter = paramter;
	}
	
	public String getBlockParamter() {
		return blockParamter;
	}
	
	public String getBlockParamterTranslate() {
		switch(blockParamter) {
			case "right":
				return StatCollector.translateToLocal(CBCraft.MODID + ".paramter.direction.right");
			case "left":
				return StatCollector.translateToLocal(CBCraft.MODID + ".paramter.direction.left");
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
