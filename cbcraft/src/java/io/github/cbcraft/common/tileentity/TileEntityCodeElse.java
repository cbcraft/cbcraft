package io.github.cbcraft.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;

public class TileEntityCodeElse extends TileEntityCode {
	private boolean blockCodeIfEnd = false;
	private BlockPos blockCodeIfEndPos = new BlockPos(0, 0, 0);
	
	public void setBlockCodeIfEndPos(BlockPos pos) {
		blockCodeIfEnd = true;
		blockCodeIfEndPos = pos;
	}
	
	public BlockPos getBlockCodeIfEndPos() {
		return blockCodeIfEndPos;
	}
	
	public boolean hasBlockCodeIfEndPos() {
		return blockCodeIfEnd;
	}
	
	public void clearBlockCodeIfEndPos() {
		blockCodeIfEnd = false;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setBoolean("linked", blockCodeIfEnd);
		
		NBTTagCompound blockPos = new NBTTagCompound();
		blockPos.setInteger("x", blockCodeIfEndPos.getX());
		blockPos.setInteger("y", blockCodeIfEndPos.getY());
		blockPos.setInteger("z", blockCodeIfEndPos.getZ());
		compound.setTag("pos", blockPos);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
		if(compound.hasKey("linked", NBT_BOOLEAN_ID)) {
			blockCodeIfEnd = compound.getBoolean("linked");
		}
		
		final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
		NBTTagCompound blockPos = compound.getCompoundTag("pos");
		if(blockPos.hasKey("x", NBT_INT_ID) && blockPos.hasKey("y", NBT_INT_ID) && blockPos.hasKey("z", NBT_INT_ID)) {
			blockCodeIfEndPos = new BlockPos(blockPos.getInteger("x"), blockPos.getInteger("y"), blockPos.getInteger("z"));
		}
	}
}
