package io.github.cbcraft.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public class TileEntityRobot extends TileEntity  {
	private boolean blockCodeStart = false;
	private BlockPos blockCodeStartPos = new BlockPos(0, 0, 0);
	
	public void setBlockCodeStartPos(BlockPos pos) {
		blockCodeStart = true;
		blockCodeStartPos = pos;
	}
	
	public BlockPos getBlockCodeStartPos() {
		return blockCodeStartPos;
	}
	
	public boolean hasBlockCodeStartPos() {
		return blockCodeStart;
	}
	
	public void clearBlockCodeStartPos() {
		blockCodeStart = false;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setBoolean("linked", blockCodeStart);
		
		NBTTagCompound blockPos = new NBTTagCompound();
		blockPos.setInteger("x", blockCodeStartPos.getX());
		blockPos.setInteger("y", blockCodeStartPos.getY());
		blockPos.setInteger("z", blockCodeStartPos.getZ());
		compound.setTag("pos", blockPos);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
		if(compound.hasKey("linked", NBT_BOOLEAN_ID)) {
			blockCodeStart = compound.getBoolean("linked");
		}
		
		final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
		NBTTagCompound blockPos = compound.getCompoundTag("pos");
		if(blockPos.hasKey("x", NBT_INT_ID) && blockPos.hasKey("y", NBT_INT_ID) && blockPos.hasKey("z", NBT_INT_ID)) {
			blockCodeStartPos = new BlockPos(blockPos.getInteger("x"), blockPos.getInteger("y"), blockPos.getInteger("z"));
		}
	}
}
