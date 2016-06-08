package io.github.cbcraft.common.tileentity;

import java.util.ArrayList;
import java.util.List;

import io.github.cbcraft.common.block.BlockCode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;

public class TileEntityCodeStart extends TileEntity implements ITickable {
	private int blockCodeRunTick = 0;
	private BlockPos blockCodeRunNextPos;
	private IBlockState blockCodeRunNextState;
	private boolean blockCodeReady = false;
	private boolean blockCodeRun = false;
	
	private int blockCodeIfExecCount;
	private List<Boolean> blockCodeIfExecEnd = new ArrayList<Boolean>();
	
	private List<BlockPos> blockCodeForList = new ArrayList<BlockPos>();
	private List<BlockPos> blockCodeIfList = new ArrayList<BlockPos>();
	private List<List<BlockPos>> blockCodeIfElseList = new ArrayList<List<BlockPos>>();
	private List<List<BlockPos>> blockCodeElseList = new ArrayList<List<BlockPos>>();
	
	private boolean blockLinked = false;
	private BlockPos blockRobotPos = new BlockPos(0, 0, 0);
	
	public void setBlockCodeRunTick(int tick) {
		blockCodeRunTick = tick;
	}
	
	public int getBlockCodeRunTick() {
		return blockCodeRunTick;
	}
	
	public void setBlockCodeRunNextPos(BlockPos pos) {
		blockCodeRunNextPos = pos;
	}
	
	public BlockPos getBlockCodeRunNextPos() {
		return blockCodeRunNextPos;
	}
	
	public void setBlockCodeRunNextState(IBlockState state) {
		blockCodeRunNextState = state;
	}
	
	public IBlockState getBlockCodeRunNextState() {
		return blockCodeRunNextState;
	}
	
	public void setBlockCodeReady(boolean ready) {
		blockCodeReady = ready;
	}
	
	public boolean getBlockCodeReady() {
		return blockCodeReady;
	}
	
	public void setBlockLinked(boolean linked) {
		blockLinked = linked;
	}
	
	public boolean getBlockLinked() {
		return blockLinked;
	}
	
	public void setBlockRobotPos(BlockPos pos) {
		blockRobotPos = pos;
	}
	
	public BlockPos getBlockRobotPos() {
		return blockRobotPos;
	}
	
	public void setBlockCodeRun(boolean running) {
		blockCodeRun = running;
	}
	
	public boolean getBlockCodeRun() {
		return blockCodeRun;
	}
	
	public void resetBlockCodeForList() {
		blockCodeForList.clear();
	}
	
	public void addBlockCodeForList(BlockPos pos) {
		blockCodeForList.add(pos);
	}
	
	public void removeBlockCodeForListLast() {
		blockCodeForList.remove(blockCodeForList.size() - 1);
	}
	
	public BlockPos getBlockCodeForListLast() {
		return blockCodeForList.get(blockCodeForList.size() - 1);
	}
	
	public int getBlockCodeForListCount() {
		return blockCodeForList.size();
	}
	
	public void resetBlockCodeIfList() {
		blockCodeIfList.clear();
	}
	
	public void addBlockCodeIfList(BlockPos pos) {
		blockCodeIfList.add(pos);
	}
	
	public void removeBlockCodeIfListLast() {
		blockCodeIfList.remove(blockCodeIfList.size() - 1);
	}
	
	public BlockPos getBlockCodeIfListLast() {
		return blockCodeIfList.get(blockCodeIfList.size() - 1);
	}
	
	public int getBlockCodeIfListCount() {
		return blockCodeIfList.size();
	}
	
	public void resetBlockCodeIfElseList() {
		blockCodeIfElseList.clear();
	}
	
	public void addBlockCodeIfElseList(int index, BlockPos pos) {
		List<BlockPos> temp = new ArrayList<BlockPos>();
		temp.add(pos);
		
		while(blockCodeIfElseList.size() <= index) {
			blockCodeIfElseList.add(null);
		}
		
		blockCodeIfElseList.set(index, temp);
	}
	
	public BlockPos getBlockCodeIfElseListLast(int index) {
		return blockCodeIfElseList.get(index).get(blockCodeIfElseList.get(index).size() - 1);
	}
	
	public int getBlockCodeIfElseListCount(int index) {
		if(blockCodeIfElseList.size() > index) {
			return blockCodeIfElseList.get(index).size();
		}
		
		return 0;
	}
	
	public void resetBlockCodeElseList() {
		blockCodeElseList.clear();
	}
	
	public void addBlockCodeElseList(int index, BlockPos pos) {
		List<BlockPos> temp = new ArrayList<BlockPos>();
		temp.add(pos);
		
		while(blockCodeElseList.size() <= index) {
			blockCodeElseList.add(null);
		}
		
		blockCodeElseList.set(index, temp);
	}
	
	public BlockPos getBlockCodeElseListLast(int index) {
		return blockCodeElseList.get(index).get(blockCodeElseList.get(index).size() - 1);
	}
	
	public int getBlockCodeElseListCount(int index) {
		if(blockCodeElseList.size() > index) {
			return blockCodeElseList.get(index).size();
		}
		
		return 0;
	}
	
	public void clearBlockCodeIfExecEnd() {
		blockCodeIfExecEnd.clear();
	}
	
	public void setBlockCodeIfExecEnd(int index) {
		while(blockCodeIfExecEnd.size() <= index) {
			blockCodeIfExecEnd.add(null);
		}
		
		blockCodeIfExecEnd.set(index, true);
	}
	
	public boolean getBlockCodeIfExecEnd(int index) {
		if(blockCodeIfExecEnd.size() > index) {
			return blockCodeIfExecEnd.get(index);
		}
		
		return false;
	}
	
	public void clearBlockCodeIfExecCount() {
		blockCodeIfExecCount = 0;
	}
	
	public void incrementBlockCodeIfExecCount() {
		++blockCodeIfExecCount;
	}
	
	public void decrementBlockCodeIfExecCount() {
		--blockCodeIfExecCount;
	}
	
	public int getBlockCodeIfExecCount() {
		return blockCodeIfExecCount;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setBoolean("linked", blockLinked);
		
		NBTTagCompound blockPos = new NBTTagCompound();
		blockPos.setInteger("x", blockRobotPos.getX());
		blockPos.setInteger("y", blockRobotPos.getY());
		blockPos.setInteger("z", blockRobotPos.getZ());
		compound.setTag("robotPos", blockPos);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
		if(compound.hasKey("linked", NBT_BOOLEAN_ID)) {
			blockLinked = compound.getBoolean("linked");
		}
		
		final int NBT_INT_ID = 3; // Values can be found on NBTBase.createNewByType()
		NBTTagCompound robotPosNBT = compound.getCompoundTag("robotPos");
		if(robotPosNBT.hasKey("x", NBT_INT_ID) && robotPosNBT.hasKey("y", NBT_INT_ID) && robotPosNBT.hasKey("z", NBT_INT_ID)) {
			blockRobotPos = new BlockPos(robotPosNBT.getInteger("x"), robotPosNBT.getInteger("y"), robotPosNBT.getInteger("z"));
		}
	}
	
	@Override
	public Packet<?> getDescriptionPacket() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setBoolean("syncRun", blockCodeRun);
		compound.setBoolean("syncLinked", blockLinked);
		return new S35PacketUpdateTileEntity(this.pos, 1, compound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		NBTTagCompound compound = pkt.getNbtCompound();
		
		final int NBT_BOOLEAN_ID = 1; // Values can be found on NBTBase.createNewByType()
		if(compound.hasKey("syncRun", NBT_BOOLEAN_ID)) {
			this.blockCodeRun = compound.getBoolean("syncRun");
		}
		if(compound.hasKey("syncLinked", NBT_BOOLEAN_ID)) {
			this.blockLinked = compound.getBoolean("syncLinked");
		}
	}
	
	@Override
	public void update() {
		if(!this.worldObj.isRemote) {
			if(blockCodeRun) {
				if(blockCodeRunTick == 0) {
					BlockCode.execBlockCode(this.worldObj, blockCodeRunNextPos, blockCodeRunNextState);
					
					blockCodeRunTick = 10;
				}
				else {
					--blockCodeRunTick;
				}
			}
		}
	}
}
