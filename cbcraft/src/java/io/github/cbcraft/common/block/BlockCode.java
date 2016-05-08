package io.github.cbcraft.common.block;

import io.github.cbcraft.common.tileentity.TileEntityCode;
import io.github.cbcraft.common.tileentity.TileEntityCodeBreak;
import io.github.cbcraft.common.tileentity.TileEntityCodeFor;
import io.github.cbcraft.common.tileentity.TileEntityCodeForEnd;
import io.github.cbcraft.common.tileentity.TileEntityCodeMove;
import io.github.cbcraft.common.tileentity.TileEntityCodePlace;
import io.github.cbcraft.common.tileentity.TileEntityCodeRotate;
import io.github.cbcraft.common.tileentity.TileEntityCodeStart;
import io.github.cbcraft.common.tileentity.TileEntityRobot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCode extends BlockContainer {
	public BlockCode(String unlocalizedName) {
		super(Material.circuits);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(Blocks.tabBlocks);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setHardness(0.0F);
		this.setStepSound(soundTypeWood);
		this.disableStats();
	}
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyEnum<EnumStatus> STATUS = PropertyEnum.create("status", EnumStatus.class);
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		int statusbits = (meta & 0x0c) >> 2;
		
		EnumFacing facing = EnumFacing.getHorizontal(meta);
		EnumStatus status = EnumStatus.byMetadata(statusbits);
		
		return this.getDefaultState().withProperty(FACING, facing).withProperty(STATUS, status);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		EnumFacing facing = (EnumFacing)state.getValue(FACING);
		EnumStatus status = (EnumStatus)state.getValue(STATUS);
		
		int facingbits = facing.getHorizontalIndex();
		int statusbits = status.getMetadata() << 2;
		
		return facingbits | statusbits;
	}
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {FACING, STATUS});
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(STATUS, EnumStatus.byMetadata(meta));
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(!worldIn.isRemote) {
			BlockCode.findBlockCodeStart(worldIn, pos, state);
		}
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		Block block = state.getBlock();
		if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
			TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)worldIn.getTileEntity(pos);
			if(tileEntityCodeStart != null) {
				tileEntityCodeStart.setBlockCodeRun(false);
				tileEntityCodeStart.setBlockCodeReady(false);
			}
		}
		else {
			TileEntityCode tileEntityCode = (TileEntityCode)worldIn.getTileEntity(pos);
			if(tileEntityCode != null && tileEntityCode.hasBlockCodeStartPos()) {
				TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)worldIn.getTileEntity(tileEntityCode.getBlockCodeStartPos());
				if(tileEntityCodeStart != null) {
					tileEntityCodeStart.setBlockCodeRun(false);
					tileEntityCodeStart.setBlockCodeReady(false);
				}
			}
		}
		
		BlockCode.setBlockStatusDisabled(worldIn, pos, state);
		
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return World.doesBlockHaveSolidTopSurface(worldIn, pos.down()) ? super.canPlaceBlockAt(worldIn, pos) : false;
		
	}
	
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if(!World.doesBlockHaveSolidTopSurface(worldIn, pos.down())) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}
	
	public static void findBlockCodeStart(World worldIn, BlockPos pos, IBlockState state) {
		Block block = state.getBlock();
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
			BlockCode.checkBlockCode(worldIn, pos, state);
			
			return;
		}
		
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		BlockPos blockPosBehind = pos.offset(enumFacing.getOpposite());
		IBlockState blockStateBehind = worldIn.getBlockState(blockPosBehind);
		Block blockBehind = blockStateBehind.getBlock();
		
		if(blockBehind.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
			if((EnumFacing)blockStateBehind.getValue(FACING) != enumFacing) {
				return;
			}
			
			BlockCode.checkBlockCode(worldIn, blockPosBehind, blockStateBehind);
		}
		else if(blockBehind.getUnlocalizedName().contains(Blocks.blockCodeUnlocalizedNamePrefix)) {
			if((EnumFacing)blockStateBehind.getValue(FACING) != enumFacing) {
				return;
			}
			
			BlockCode.findBlockCodeStart(worldIn, blockPosBehind, blockStateBehind);
		}
	}
	
	public static void checkBlockCode(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		Block blockForward = blockStateForward.getBlock();
		
		/*if(blockForward.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
			if((EnumFacing)blockStateForward.getValue(FACING) == enumFacing) {
				BlockCode.setBlockStatusError(worldIn, pos, state);
			}
			
			return;
		}*/
		
		TileEntityCodeStart tileEntityCodeStart = null;
		TileEntityCode tileEntityCode = null;
		
		Block block = state.getBlock();
		if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
			tileEntityCodeStart = (TileEntityCodeStart)worldIn.getTileEntity(pos);
			if(tileEntityCodeStart == null) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while checking the code"));
				
				return;
			}
		}
		else {
			tileEntityCode = (TileEntityCode)worldIn.getTileEntity(pos);
			if(tileEntityCode == null || !tileEntityCode.hasBlockCodeStartPos()) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while checking the code"));
				
				return;
			}
			
			tileEntityCodeStart = (TileEntityCodeStart)worldIn.getTileEntity(tileEntityCode.getBlockCodeStartPos());
			if(tileEntityCodeStart == null) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while checking the code"));
				
				return;
			}
		}
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeFor.getUnlocalizedName())) {
			tileEntityCodeStart.addBlockCodeForList(pos);
		}
		else if(block.getUnlocalizedName().equals(Blocks.blockCodeForEnd.getUnlocalizedName())) {
			if(tileEntityCodeStart.getBlockCodeForListCount() == 0) {
				BlockCode.setBlockStatusError(worldIn, pos, state);
				
				return;
			}
			
			TileEntityCodeFor tileEntityCodeFor = (TileEntityCodeFor)worldIn.getTileEntity(tileEntityCodeStart.getBlockCodeForListLast());
			TileEntityCodeForEnd tileEntityCodeForEnd = (TileEntityCodeForEnd)worldIn.getTileEntity(pos);
			if(tileEntityCodeFor == null || tileEntityCodeForEnd == null) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while checking the code"));
				
				return;
			}
			
			tileEntityCodeFor.setBlockCodeForEndPos(pos);
			tileEntityCodeForEnd.setBlockCodeForPos(tileEntityCodeStart.getBlockCodeForListLast());
			
			tileEntityCodeStart.removeBlockCodeForListLast();
		}
		
		if(blockForward.getUnlocalizedName().equals(Blocks.blockCodeEnd.getUnlocalizedName())) {
			if((EnumFacing)blockStateForward.getValue(FACING) != enumFacing) {
				return;
			}
			
			if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
				return;
			}
			
			TileEntityCode tileEntityCodeForward = (TileEntityCode)worldIn.getTileEntity(blockPosForward);
			if(tileEntityCodeForward == null) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while checking the code"));
				
				return;
			}
			
			if(tileEntityCodeStart.getBlockCodeForListCount() != 0) {
				BlockCode.setBlockStatusError(worldIn, blockPosForward, blockStateForward);
				
				return;
			}
			
			tileEntityCodeForward.setBlockCodeStartPos(tileEntityCode.getBlockCodeStartPos());
			BlockCode.setBlockStatusReady(worldIn, tileEntityCode.getBlockCodeStartPos(), worldIn.getBlockState(tileEntityCode.getBlockCodeStartPos()));
		}
		else if(blockForward.getUnlocalizedName().contains(Blocks.blockCodeUnlocalizedNamePrefix)) {
			if((EnumFacing)blockStateForward.getValue(FACING) != enumFacing) {
				return;
			}
			
			TileEntityCode tileEntityCodeForward = (TileEntityCode)worldIn.getTileEntity(blockPosForward);
			if(tileEntityCodeForward == null) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while checking the code"));
				
				return;
			}
			
			if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
				tileEntityCodeStart.resetBlockCodeForList();
				
				tileEntityCodeForward.setBlockCodeStartPos(pos);
			}
			else {
				tileEntityCodeForward.setBlockCodeStartPos(tileEntityCode.getBlockCodeStartPos());
			}
			
			BlockCode.checkBlockCode(worldIn, blockPosForward, blockStateForward);
		}
	}
	
	public static void setBlockStatusReady(World worldIn, BlockPos pos, IBlockState state) {		
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		Block block = state.getBlock();
		
		TileEntityBackupData.saveData(worldIn, block, pos, state);
		
		IBlockState blockCodeState = block.getDefaultState().withProperty(FACING, enumFacing).withProperty(STATUS, EnumStatus.READY);
		worldIn.setBlockState(pos, blockCodeState);
		
		TileEntityBackupData.loadData(worldIn, block, pos, state);
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeEnd.getUnlocalizedName())) {
			TileEntityCode tileEntityCode = (TileEntityCode)worldIn.getTileEntity(pos);
			if(tileEntityCode != null && tileEntityCode.hasBlockCodeStartPos()) {
				TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)worldIn.getTileEntity(tileEntityCode.getBlockCodeStartPos());
				tileEntityCodeStart.setBlockCodeReady(true);
			}
			else {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while setting status code to ready"));
				
				return;
			}
			
			return;
		}
		
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		
		BlockCode.setBlockStatusReady(worldIn, blockPosForward, blockStateForward);
	}
	
	public static void setBlockStatusRun(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		Block block = state.getBlock();
		
		TileEntityBackupData.saveData(worldIn, block, pos, state);
		
		IBlockState blockCodeState = block.getDefaultState().withProperty(FACING, enumFacing).withProperty(STATUS, EnumStatus.RUN);
		worldIn.setBlockState(pos, blockCodeState);
		
		TileEntityBackupData.loadData(worldIn, block, pos, state);
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeEnd.getUnlocalizedName())) {
			TileEntityCode tileEntityCode = (TileEntityCode)worldIn.getTileEntity(pos);
			if(tileEntityCode != null && tileEntityCode.hasBlockCodeStartPos()) {
				BlockCode.prepareCodeToRun(worldIn, tileEntityCode.getBlockCodeStartPos(), worldIn.getBlockState(tileEntityCode.getBlockCodeStartPos()));
			}
			else {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while setting status code to running"));
				
				return;
			}
			
			return;
		}
		
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		
		BlockCode.setBlockStatusRun(worldIn, blockPosForward, blockStateForward);
	}
	
	public static void prepareCodeToRun(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		
		TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)worldIn.getTileEntity(pos);
		if(tileEntityCodeStart != null) {
			tileEntityCodeStart.setBlockCodeRunNextPos(blockPosForward);
			tileEntityCodeStart.setBlockCodeRunNextState(blockStateForward);
			tileEntityCodeStart.setBlockCodeRunTick(0);
			tileEntityCodeStart.setBlockCodeRun(true);
		}
		else {
			BlockCode.setBlockStatusReady(worldIn, pos, state);
			
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while attempting to run the code"));
		}
	}
	
	public static void setBlockStatusError(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		Block block = state.getBlock();
		
		TileEntityBackupData.saveData(worldIn, block, pos, state);
		
		IBlockState blockCodeState = block.getDefaultState().withProperty(FACING, enumFacing).withProperty(STATUS, EnumStatus.ERROR);
		worldIn.setBlockState(pos, blockCodeState);
		
		TileEntityBackupData.loadData(worldIn, block, pos, state);
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
			return;
		}
		
		BlockPos blockPosBehind = pos.offset(enumFacing.getOpposite());
		IBlockState blockStateBehind = worldIn.getBlockState(blockPosBehind);
		
		BlockCode.setBlockStatusError(worldIn, blockPosBehind, blockStateBehind);
	}
	
	public static void setBlockStatusDisabled(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		
		BlockPos blockPosBehind = pos.offset(enumFacing.getOpposite());
		IBlockState blockStateBehind = worldIn.getBlockState(blockPosBehind);
		Block blockBehind = blockStateBehind.getBlock();
		if(blockBehind.getUnlocalizedName().contains(Blocks.blockCodeUnlocalizedNamePrefix) && (EnumFacing)blockStateBehind.getValue(FACING) == enumFacing) {
			setBlockStatusDisabledBehind(worldIn, blockPosBehind, blockStateBehind);
		}
		
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		Block blockForward = blockStateForward.getBlock();
		if(blockForward.getUnlocalizedName().contains(Blocks.blockCodeUnlocalizedNamePrefix) && (EnumFacing)blockStateForward.getValue(FACING) == enumFacing) {
			setBlockStatusDisabledForward(worldIn, blockPosForward, blockStateForward);
		}
	}
	
	public static void setBlockStatusDisabledBehind(World worldIn, BlockPos pos, IBlockState state) {
		Block block = state.getBlock();
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeEnd.getUnlocalizedName())) {
			return;
		}
		
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		
		TileEntityBackupData.saveData(worldIn, block, pos, state);
		
		IBlockState blockCodeState = block.getDefaultState().withProperty(FACING, enumFacing).withProperty(STATUS, EnumStatus.DISABLED);
		worldIn.setBlockState(pos, blockCodeState);
		
		TileEntityBackupData.loadData(worldIn, block, pos, state);
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
			return;
		}
		else {
			TileEntityCode tileEntityCode = (TileEntityCode)worldIn.getTileEntity(pos);
			if(tileEntityCode != null) {
				tileEntityCode.clearBlockCodeStartPos();
			}
		}
		
		BlockPos blockPosBehind = pos.offset(enumFacing.getOpposite());
		IBlockState blockStateBehind = worldIn.getBlockState(blockPosBehind);
		Block blockBehind = blockStateBehind.getBlock();
		
		if(!blockBehind.getUnlocalizedName().contains(Blocks.blockCodeUnlocalizedNamePrefix) || (EnumFacing)blockStateBehind.getValue(FACING) != enumFacing) {
			return;
		}
		
		BlockCode.setBlockStatusDisabledBehind(worldIn, blockPosBehind, blockStateBehind);
	}
	
	public static void setBlockStatusDisabledForward(World worldIn, BlockPos pos, IBlockState state) {
		Block block = state.getBlock();
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
			return;
		}
		
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		
		TileEntityBackupData.saveData(worldIn, block, pos, state);
		
		IBlockState blockCodeState = block.getDefaultState().withProperty(FACING, enumFacing).withProperty(STATUS, EnumStatus.DISABLED);
		worldIn.setBlockState(pos, blockCodeState);
		
		TileEntityBackupData.loadData(worldIn, block, pos, state);
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeEnd.getUnlocalizedName())) {
			return;
		}
		else {
			TileEntityCode tileEntityCode = (TileEntityCode)worldIn.getTileEntity(pos);
			if(tileEntityCode != null) {
				tileEntityCode.clearBlockCodeStartPos();
			}
		}
		
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		Block blockForward = blockStateForward.getBlock();
		
		if(!blockForward.getUnlocalizedName().contains(Blocks.blockCodeUnlocalizedNamePrefix) || (EnumFacing)blockStateForward.getValue(FACING) != enumFacing) {
			return;
		}
		
		BlockCode.setBlockStatusDisabledForward(worldIn, blockPosForward, blockStateForward);
	}
	
	public static void execBlockCode(World worldIn, BlockPos pos, IBlockState state) {
		Block block = state.getBlock();
		
		BlockPos blockCodeStartPos = null;
		if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
			blockCodeStartPos = pos;
		}
		else {
			TileEntityCode tileEntityCode = (TileEntityCode)worldIn.getTileEntity(pos);
			if(tileEntityCode != null && tileEntityCode.hasBlockCodeStartPos()) {
				blockCodeStartPos = tileEntityCode.getBlockCodeStartPos();
			}
		}
		TileEntityCodeStart tileEntityCodeStart = (TileEntityCodeStart)worldIn.getTileEntity(blockCodeStartPos);
		if(tileEntityCodeStart == null || !tileEntityCodeStart.getBlockLinked()) {
			return;
		}
		
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		Block blockForward = blockStateForward.getBlock();
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeMove.getUnlocalizedName())) {
			IBlockState blockRobotState = worldIn.getBlockState(tileEntityCodeStart.getBlockRobotPos());
			Block blockRobot = blockRobotState.getBlock();
			EnumFacing enumRobotFacing = (EnumFacing)blockRobotState.getValue(FACING);
			
			TileEntityCodeMove tileEntityCodeMove = (TileEntityCodeMove)worldIn.getTileEntity(pos);
			if(tileEntityCodeMove == null) {
				tileEntityCodeStart.setBlockCodeRun(false);
				BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
				
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while executing the code"));
				
				return;
			}
			
			BlockPos blockNewRobotPos;
			switch(tileEntityCodeMove.getBlockParamter()) {
				case "front":
					blockNewRobotPos = tileEntityCodeStart.getBlockRobotPos().offset(enumRobotFacing);
					break;
				case "up":
					blockNewRobotPos = tileEntityCodeStart.getBlockRobotPos().up();
					break;
				case "down":
					blockNewRobotPos = tileEntityCodeStart.getBlockRobotPos().down();
					break;
				default:
					tileEntityCodeStart.setBlockCodeRun(false);
					BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
					
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Move - An error has occurred"));
					
					return;
			}
			
			if(!worldIn.isAirBlock(blockNewRobotPos)) {
				tileEntityCodeStart.setBlockCodeRun(false);
				BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
				
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Move - There is a block on the defined direction ('" + tileEntityCodeMove.getBlockParamter() + "')"));
				
				return;
			}
			
			TileEntityBackupData.saveData(worldIn, blockRobot, tileEntityCodeStart.getBlockRobotPos(), blockRobotState);
			
			worldIn.setBlockToAir(tileEntityCodeStart.getBlockRobotPos());
			IBlockState blockNewRobotState = blockRobot.getDefaultState().withProperty(FACING, enumRobotFacing);
			worldIn.setBlockState(blockNewRobotPos, blockNewRobotState);
			
			TileEntityBackupData.loadData(worldIn, blockNewRobotState.getBlock(), blockNewRobotPos, blockNewRobotState);
			
			tileEntityCodeStart.setBlockRobotPos(blockNewRobotPos);
		}
		else if(block.getUnlocalizedName().equals(Blocks.blockCodeRotate.getUnlocalizedName())) {
			IBlockState blockRobotState = worldIn.getBlockState(tileEntityCodeStart.getBlockRobotPos());
			Block blockRobot = blockRobotState.getBlock();
			EnumFacing enumRobotFacing = (EnumFacing)blockRobotState.getValue(FACING);
			
			TileEntityCodeRotate tileEntityCodeRotate = (TileEntityCodeRotate)worldIn.getTileEntity(pos);
			if(tileEntityCodeRotate == null) {
				tileEntityCodeStart.setBlockCodeRun(false);
				BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
				
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while executing the code"));
				
				return;
			}
			
			EnumFacing enumNewRobotFacing;
			switch(tileEntityCodeRotate.getBlockParamter()) {
				case "right":
					enumNewRobotFacing = enumRobotFacing.rotateY();
					break;
				case "left":
					enumNewRobotFacing = enumRobotFacing.rotateYCCW();
					break;
				default:
					tileEntityCodeStart.setBlockCodeRun(false);
					BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
					
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Rotate - An error has occurred"));
					
					return;
			}
			
			IBlockState blockNewRobotState = blockRobot.getDefaultState().withProperty(FACING, enumNewRobotFacing);
			worldIn.setBlockState(tileEntityCodeStart.getBlockRobotPos(), blockNewRobotState);
		}
		else if(block.getUnlocalizedName().equals(Blocks.blockCodeBreak.getUnlocalizedName())) {
			IBlockState blockRobotState = worldIn.getBlockState(tileEntityCodeStart.getBlockRobotPos());
			EnumFacing enumRobotFacing = (EnumFacing)blockRobotState.getValue(FACING);
			
			TileEntityCodeBreak tileEntityCodeBreak = (TileEntityCodeBreak)worldIn.getTileEntity(pos);
			if(tileEntityCodeBreak == null) {
				tileEntityCodeStart.setBlockCodeRun(false);
				BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
				
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while executing the code"));
				
				return;
			}
			
			BlockPos blockRobotPosBreak;
			switch(tileEntityCodeBreak.getBlockParamter()) {
				case "front":
					blockRobotPosBreak = tileEntityCodeStart.getBlockRobotPos().offset(enumRobotFacing);
					break;
				case "up":
					blockRobotPosBreak = tileEntityCodeStart.getBlockRobotPos().up();
					break;
				case "down":
					blockRobotPosBreak = tileEntityCodeStart.getBlockRobotPos().down();
					break;
				default:
					tileEntityCodeStart.setBlockCodeRun(false);
					BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
					
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCRaft Exec: Break - An error has occurred"));
					
					return;
			}
			
			if(worldIn.isAirBlock(blockRobotPosBreak)) {
				tileEntityCodeStart.setBlockCodeRun(false);
				BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
				
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Break - There is no block on the defined direction ('" + tileEntityCodeBreak.getBlockParamter() + "')"));
				
				return;
			}
			
			worldIn.destroyBlock(blockRobotPosBreak, true);
		}
		else if(block.getUnlocalizedName().equals(Blocks.blockCodePlace.getUnlocalizedName())) {
			TileEntityCodePlace tileEntityCodePlace = (TileEntityCodePlace)worldIn.getTileEntity(pos);
			if(tileEntityCodePlace == null) {
				tileEntityCodeStart.setBlockCodeRun(false);
				BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
				
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while executing the code"));
				
				return;
			}
			
			if(tileEntityCodePlace.getBlockParamter("block") == "minecraft:air") {
				tileEntityCodeStart.setBlockCodeRun(false);
				BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
				
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Place - No defined block to place"));
				
				return;
			}
			
			IBlockState blockRobotState = worldIn.getBlockState(tileEntityCodeStart.getBlockRobotPos());
			EnumFacing enumRobotFacing = (EnumFacing)blockRobotState.getValue(FACING);
			
			BlockPos blockRobotPosPlace;
			switch(tileEntityCodePlace.getBlockParamter("direction")) {
				case "front":
					blockRobotPosPlace = tileEntityCodeStart.getBlockRobotPos().offset(enumRobotFacing);
					break;
				case "up":
					blockRobotPosPlace = tileEntityCodeStart.getBlockRobotPos().up();
					break;
				case "down":
					blockRobotPosPlace = tileEntityCodeStart.getBlockRobotPos().down();
					break;
				default:
					tileEntityCodeStart.setBlockCodeRun(false);
					BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
					
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Place - An error has occurred"));
					
					return;
			}
			
			if(!worldIn.isAirBlock(blockRobotPosPlace)) {
				tileEntityCodeStart.setBlockCodeRun(false);
				BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
				
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: Place - There is already a block on the defined direction ('" + tileEntityCodePlace.getBlockParamter("direction") + "')"));
				
				return;
			}
			
			IBlockState blockPlaceState = Block.getBlockFromName(tileEntityCodePlace.getBlockParamter("block")).getDefaultState();
			worldIn.setBlockState(blockRobotPosPlace, blockPlaceState);
		}
		else if(block.getUnlocalizedName().equals(Blocks.blockCodeFor.getUnlocalizedName())) {
			TileEntityCodeFor tileEntityCodeFor = (TileEntityCodeFor)worldIn.getTileEntity(pos);
			if(tileEntityCodeFor == null) {
				tileEntityCodeStart.setBlockCodeRun(false);
				BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
				
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while executing the code"));
				
				return;
			}
			
			switch(tileEntityCodeFor.getBlockParamter()) {
				case 0:
					tileEntityCodeFor.setBlockCodeRunCheck(-1);
					break;
				default:
					tileEntityCodeFor.setBlockCodeRunCheck(tileEntityCodeFor.getBlockParamter() - 1);
			}
		}
		else if(block.getUnlocalizedName().equals(Blocks.blockCodeForEnd.getUnlocalizedName())) {
			TileEntityCodeForEnd tileEntityCodeForEnd = (TileEntityCodeForEnd)worldIn.getTileEntity(pos);
			if(tileEntityCodeForEnd == null) {
				tileEntityCodeStart.setBlockCodeRun(false);
				BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
				
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while executing the code"));
				
				return;
			}
			
			TileEntityCodeFor tileEntityCodeFor = (TileEntityCodeFor)worldIn.getTileEntity(tileEntityCodeForEnd.getBlockCodeForPos());
			if(tileEntityCodeFor == null) {
				tileEntityCodeStart.setBlockCodeRun(false);
				BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
				
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Error: An error has occurred while executing the code"));
				
				return;
			}
			
			if(tileEntityCodeFor.getBlockCodeRunCheck() == -1 || tileEntityCodeFor.getBlockCodeRunCheck() > 0) {
				BlockPos blockForPosForward = tileEntityCodeForEnd.getBlockCodeForPos().offset(enumFacing);
				IBlockState blockForStateForward = worldIn.getBlockState(blockForPosForward);
				
				if(tileEntityCodeFor.getBlockCodeRunCheck() > 0) {
					tileEntityCodeFor.setBlockCodeRunCheck(tileEntityCodeFor.getBlockCodeRunCheck() - 1);
				}
				
				tileEntityCodeStart.setBlockCodeRunNextPos(blockForPosForward);
				tileEntityCodeStart.setBlockCodeRunNextState(blockForStateForward);
				
				return ;
			}
		}
		
		if(blockForward.getUnlocalizedName().equals(Blocks.blockCodeEnd.getUnlocalizedName())) {
			tileEntityCodeStart.setBlockCodeRun(false);
			BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, worldIn.getBlockState(blockCodeStartPos));
			
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CBCraft Exec: End"));
			
			return;
		}
		
		tileEntityCodeStart.setBlockCodeRunNextPos(blockPosForward);
		tileEntityCodeStart.setBlockCodeRunNextState(blockStateForward);
	}
	
	public static enum EnumStatus implements IStringSerializable {
		DISABLED(0, "disabled"),
		ERROR(1, "error"),
		READY(2, "ready"),
		RUN(3, "run");
		
		private final int meta;
		private final String name;
		private static final EnumStatus[] META_LOOKUP = new EnumStatus[values().length];
		
		private EnumStatus(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
		
		public int getMetadata() {
			return this.meta;
		}
		
		public static EnumStatus byMetadata(int meta) {
			if(meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}
			
			return META_LOOKUP[meta];
		}
		
		static {
			for(EnumStatus colour : values()) {
				META_LOOKUP[colour.getMetadata()] = colour;
			}
		}
	}
	
	static class TileEntityBackupData {
		// Tile Entity Block Code Start Variables
		static int blockCodeRunTick = 0;
		static BlockPos blockCodeRunNextPos = null;
		static IBlockState blockCodeRunNextState = null;
		static boolean blockCodeReady = false;
		static boolean blockCodeRun = false;
		static boolean blockLinked = false;
		static BlockPos blockRobotPos = null;
		
		// Tile Entity Block Code For/For End Variables
		static int blockCodeRunCheck = 0;
		static boolean blockCodeFor = false;
		static BlockPos blockCodeForPos = null;
		
		// Tile Entity Block Code Variables
		static String blockParamterStr1 = null;
		static String blockParamterStr2 = null;
		static int blockParamterInt1 = 0;
		
		// Tile Entity Block Code and Robot Variables
		static boolean blockCodeStart = false;
		static BlockPos blockCodeStartPos = null;
		
		static void saveData(World worldIn, Block block, BlockPos pos, IBlockState state) {
			if(block.getUnlocalizedName().equals(Blocks.blockRobot.getUnlocalizedName())) {
				TileEntityRobot tileEntityRobot = (TileEntityRobot)worldIn.getTileEntity(pos);
				if(tileEntityRobot != null) {
					blockCodeStart = tileEntityRobot.hasBlockCodeStartPos();
					blockCodeStartPos = tileEntityRobot.getBlockCodeStartPos();
				}
			}
			else if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
				TileEntityCodeStart tileEntityCode = (TileEntityCodeStart)worldIn.getTileEntity(pos);
				if(tileEntityCode != null) {
					blockCodeRunTick = tileEntityCode.getBlockCodeRunTick();
					blockCodeRunNextPos = tileEntityCode.getBlockCodeRunNextPos();
					blockCodeRunNextState = tileEntityCode.getBlockCodeRunNextState();
					blockCodeReady = tileEntityCode.getBlockCodeReady();
					blockCodeRun = tileEntityCode.getBlockCodeRun();
					blockLinked = tileEntityCode.getBlockLinked();
					blockRobotPos = tileEntityCode.getBlockRobotPos();
				}
			}
			else {
				if(block.getUnlocalizedName().equals(Blocks.blockCodeBreak.getUnlocalizedName())) {
					TileEntityCodeBreak tileEntityCode = (TileEntityCodeBreak)worldIn.getTileEntity(pos);
					if(tileEntityCode != null) {
						blockParamterStr1 = tileEntityCode.getBlockParamter();
					}
				}
				else if(block.getUnlocalizedName().equals(Blocks.blockCodeMove.getUnlocalizedName())) {
					TileEntityCodeMove tileEntityCode = (TileEntityCodeMove)worldIn.getTileEntity(pos);
					if(tileEntityCode != null) {
						blockParamterStr1 = tileEntityCode.getBlockParamter();
					}
				}
				else if(block.getUnlocalizedName().equals(Blocks.blockCodePlace.getUnlocalizedName())) {
					TileEntityCodePlace tileEntityCode = (TileEntityCodePlace)worldIn.getTileEntity(pos);
					if(tileEntityCode != null) {
						blockParamterStr1 = tileEntityCode.getBlockParamter("direction");
						blockParamterStr2 = tileEntityCode.getBlockParamter("block");
					}
				}
				else if(block.getUnlocalizedName().equals(Blocks.blockCodeRotate.getUnlocalizedName())) {
					TileEntityCodeRotate tileEntityCode = (TileEntityCodeRotate)worldIn.getTileEntity(pos);
					if(tileEntityCode != null) {
						blockParamterStr1 = tileEntityCode.getBlockParamter();
					}
				}
				else if(block.getUnlocalizedName().equals(Blocks.blockCodeFor.getUnlocalizedName())) {
					TileEntityCodeFor tileEntityCodeFor = (TileEntityCodeFor)worldIn.getTileEntity(pos);
					if(tileEntityCodeFor != null) {
						blockParamterInt1 = tileEntityCodeFor.getBlockParamter();
						blockCodeRunCheck = tileEntityCodeFor.getBlockCodeRunCheck();
						blockCodeFor = tileEntityCodeFor.hasBlockCodeForEndPos();
						blockCodeForPos = tileEntityCodeFor.getBlockCodeForEndPos();
					}
				}
				else if(block.getUnlocalizedName().equals(Blocks.blockCodeForEnd.getUnlocalizedName())) {
					TileEntityCodeForEnd tileEntityCodeForEnd = (TileEntityCodeForEnd)worldIn.getTileEntity(pos);
					if(tileEntityCodeForEnd != null) {
						blockCodeFor = tileEntityCodeForEnd.hasBlockCodeForPos();
						blockCodeForPos = tileEntityCodeForEnd.getBlockCodeForPos();
					}
				}
				
				TileEntityCode tileEntityCode = (TileEntityCode)worldIn.getTileEntity(pos);
				if(tileEntityCode != null) {
					blockCodeStart = tileEntityCode.hasBlockCodeStartPos();
					blockCodeStartPos = tileEntityCode.getBlockCodeStartPos();
				}
			}
		}
		
		static void loadData(World worldIn, Block block, BlockPos pos, IBlockState state) {
			if(block.getUnlocalizedName().equals(Blocks.blockRobot.getUnlocalizedName())) {
				TileEntityRobot tileEntityRobot = (TileEntityRobot)worldIn.getTileEntity(pos);
				if(tileEntityRobot != null) {
					if(blockCodeStart) {
						tileEntityRobot.setBlockCodeStartPos(blockCodeStartPos);
					}
				}
			}
			else if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
				TileEntityCodeStart tileEntityCode = (TileEntityCodeStart)worldIn.getTileEntity(pos);
				if(tileEntityCode != null) {
					tileEntityCode.setBlockCodeRunTick(blockCodeRunTick);
					tileEntityCode.setBlockCodeRunNextPos(blockCodeRunNextPos);
					tileEntityCode.setBlockCodeRunNextState(blockCodeRunNextState);
					tileEntityCode.setBlockCodeReady(blockCodeReady);
					tileEntityCode.setBlockCodeRun(blockCodeRun);
					tileEntityCode.setBlockLinked(blockLinked);
					tileEntityCode.setBlockRobotPos(blockRobotPos);
				}
			}
			else {
				if(block.getUnlocalizedName().equals(Blocks.blockCodeBreak.getUnlocalizedName())) {
					TileEntityCodeBreak tileEntityCode = (TileEntityCodeBreak)worldIn.getTileEntity(pos);
					if(tileEntityCode != null) {
						tileEntityCode.setBlockParamter(blockParamterStr1);
					}
				}
				else if(block.getUnlocalizedName().equals(Blocks.blockCodeMove.getUnlocalizedName())) {
					TileEntityCodeMove tileEntityCode = (TileEntityCodeMove)worldIn.getTileEntity(pos);
					if(tileEntityCode != null) {
						tileEntityCode.setBlockParamter(blockParamterStr1);
					}
				}
				else if(block.getUnlocalizedName().equals(Blocks.blockCodePlace.getUnlocalizedName())) {
					TileEntityCodePlace tileEntityCode = (TileEntityCodePlace)worldIn.getTileEntity(pos);
					if(tileEntityCode != null) {
						tileEntityCode.setBlockParamter("direction", blockParamterStr1);
						tileEntityCode.setBlockParamter("block", blockParamterStr1);
					}
				}
				else if(block.getUnlocalizedName().equals(Blocks.blockCodeRotate.getUnlocalizedName())) {
					TileEntityCodeRotate tileEntityCode = (TileEntityCodeRotate)worldIn.getTileEntity(pos);
					if(tileEntityCode != null) {
						tileEntityCode.setBlockParamter(blockParamterStr1);
					}
				}
				else if(block.getUnlocalizedName().equals(Blocks.blockCodeFor.getUnlocalizedName())) {
					TileEntityCodeFor tileEntityCodeFor = (TileEntityCodeFor)worldIn.getTileEntity(pos);
					if(tileEntityCodeFor != null) {
						tileEntityCodeFor.setBlockParamter(blockParamterInt1);
						tileEntityCodeFor.setBlockCodeRunCheck(blockCodeRunCheck);
						if(blockCodeFor) {
							tileEntityCodeFor.setBlockCodeForEndPos(blockCodeForPos);
						}
					}
				}
				else if(block.getUnlocalizedName().equals(Blocks.blockCodeForEnd.getUnlocalizedName())) {
					TileEntityCodeForEnd tileEntityCodeForEnd = (TileEntityCodeForEnd)worldIn.getTileEntity(pos);
					if(tileEntityCodeForEnd != null) {
						if(blockCodeFor) {
							tileEntityCodeForEnd.setBlockCodeForPos(blockCodeForPos);
						}
					}
				}
				
				TileEntityCode tileEntityCode = (TileEntityCode)worldIn.getTileEntity(pos);
				if(tileEntityCode != null) {
					if(blockCodeStart) {
						tileEntityCode.setBlockCodeStartPos(blockCodeStartPos);
					}
				}
			}
		}
	}
}
