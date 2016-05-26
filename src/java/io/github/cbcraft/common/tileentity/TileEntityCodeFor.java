package io.github.cbcraft.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;

public class TileEntityCodeFor extends TileEntityCode {
	private int blockCodeRunCheck = 0;
	
	private boolean blockCodeForEnd = false;
	private BlockPos blockCodeForEndPos = new BlockPos(0, 0, 0);
	private int blockParamter = 0;
	
	public void setBlockCodeForEndPos(BlockPos pos) {
		blockCodeForEnd = true;
		blockCodeForEndPos = pos;
	}
	
	public BlockPos getBlockCodeForEndPos() {
		return blockCodeForEndPos;
	}
	
	public boolean hasBlockCodeForEndPos() {
		return blockCodeForEnd;
	}
	
	public void clearBlockCodeForEndPos() {
		blockCodeForEnd = false;
	}
	
	public void setBlockParamter(int paramter) {
		blockParamter = paramter;
	}
	
	public int getBlockParamter() {
		return blockParamter;
	}
	
	public void setBlockCodeRunCheck(int paramter) {
		blockCodeRunCheck = paramter;
	}
	
	public int getBlockCodeRunCheck() {
		return blockCodeRunCheck;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setBoolean("linked", blockCodeForEnd);
		
		NBTTagCompound blockPos = new NBTTagCompound();
		blockPos.setInteger("x", blockCodeForEndPos.getX());
		blockPos.setInteger("y", blockCodeForEndPos.getY());
		blockPos.setInteger("z", blockCodeForEndPos.getZ());
		compound.setTag("pos", blockPos);
		
		compound.setInteger("paramter", blockParamter);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
		if(compound.hasKey("linked", NBT_BOOLEAN_ID)) {
			blockCodeForEnd = compound.getBoolean("linked");
		}
		
		final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
		NBTTagCompound blockPos = compound.getCompoundTag("pos");
		if(blockPos.hasKey("x", NBT_INT_ID) && blockPos.hasKey("y", NBT_INT_ID) && blockPos.hasKey("z", NBT_INT_ID)) {
			blockCodeForEndPos = new BlockPos(blockPos.getInteger("x"), blockPos.getInteger("y"), blockPos.getInteger("z"));
		}
		
		if(compound.hasKey("paramter", NBT_INT_ID)) {
			blockParamter = compound.getInteger("paramter");
		}
	}
}
