package io.github.cbcraft.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;

public class TileEntityCodeForEnd extends TileEntityCode {
	private boolean blockCodeFor = false;
	private BlockPos blockCodeForPos = new BlockPos(0, 0, 0);
	
	public void setBlockCodeForPos(BlockPos pos) {
		blockCodeFor = true;
		blockCodeForPos = pos;
	}
	
	public BlockPos getBlockCodeForPos() {
		return blockCodeForPos;
	}
	
	public boolean hasBlockCodeForPos() {
		return blockCodeFor;
	}
	
	public void clearBlockCodeForPos() {
		blockCodeFor = false;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setBoolean("linked", blockCodeFor);
		
		NBTTagCompound blockPos = new NBTTagCompound();
		blockPos.setInteger("x", blockCodeForPos.getX());
		blockPos.setInteger("y", blockCodeForPos.getY());
		blockPos.setInteger("z", blockCodeForPos.getZ());
		compound.setTag("pos", blockPos);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
		if(compound.hasKey("linked", NBT_BOOLEAN_ID)) {
			blockCodeFor = compound.getBoolean("linked");
		}
		
		final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
		NBTTagCompound blockPos = compound.getCompoundTag("pos");
		if(blockPos.hasKey("x", NBT_INT_ID) && blockPos.hasKey("y", NBT_INT_ID) && blockPos.hasKey("z", NBT_INT_ID)) {
			blockCodeForPos = new BlockPos(blockPos.getInteger("x"), blockPos.getInteger("y"), blockPos.getInteger("z"));
		}
	}
}
