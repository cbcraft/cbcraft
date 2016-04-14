package io.github.cbcraft.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCodePlace extends TileEntityCode {
	private String blockParamterDirection = "front";
	private String blockParamterBlock = "minecraft:air";
	
	public void setBlockParamter(String type, String paramter) {
		switch(type) {
			case "direction":
				blockParamterDirection = paramter;
				break;
			case "block":
				blockParamterBlock = paramter;
				break;
		}
	}
	
	public String getBlockParamter(String type) {
		String stringReturn = null;
		switch(type) {
			case "direction":
				stringReturn = blockParamterDirection;
				break;
			case "block":
				stringReturn = blockParamterBlock;
				break;
		}
		return stringReturn;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		NBTTagCompound paramter = new NBTTagCompound();
		paramter.setString("direction", blockParamterDirection);
		paramter.setString("block", blockParamterBlock);
		compound.setTag("paramter", paramter);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int NBT_STRING_ID = 8; // Values can be found on NBTBase.createNewByType()
		NBTTagCompound paramterNBT = compound.getCompoundTag("paramter");
		if(paramterNBT.hasKey("direction", NBT_STRING_ID) && paramterNBT.hasKey("block", NBT_STRING_ID)) {
			blockParamterDirection = paramterNBT.getString("direction");
			blockParamterBlock = paramterNBT.getString("block");
		}
	}
}
