package io.github.cbcraft.common.block;

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
		BlockCodeStart.isCodeRun = false;
		
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
			BlockCode.checkBlockCode(worldIn, pos, state, pos, state);
			
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
			
			BlockCode.checkBlockCode(worldIn, blockPosBehind, blockStateBehind, blockPosBehind, blockStateBehind);
		}
		else if(blockBehind.getUnlocalizedName().contains(Blocks.blockCodeUnlocalizedNamePrefix)) {
			if((EnumFacing)blockStateBehind.getValue(FACING) != enumFacing) {
				return;
			}
			
			BlockCode.findBlockCodeStart(worldIn, blockPosBehind, blockStateBehind);
		}
	}
	
	public static void checkBlockCode(World worldIn, BlockPos pos, IBlockState state, BlockPos blockCodeStartPos, IBlockState blockCodeStartState) {
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		Block blockForward = blockStateForward.getBlock();
		
		if(blockForward.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
			BlockCode.setBlockStatusError(worldIn, pos, state);
			
			return;
		}
		if(blockForward.getUnlocalizedName().equals(Blocks.blockCodeEnd.getUnlocalizedName())) {
			if((EnumFacing)blockStateForward.getValue(FACING) != enumFacing) {
				return;
			}
			
			Block block = state.getBlock();
			if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
				return;
			}
			
			BlockCode.setBlockStatusReady(worldIn, blockCodeStartPos, blockCodeStartState, blockCodeStartPos, blockCodeStartState, true);
		}
		else if(blockForward.getUnlocalizedName().contains(Blocks.blockCodeUnlocalizedNamePrefix)) {
			if((EnumFacing)blockStateForward.getValue(FACING) != enumFacing) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Check: More than one start found"));
				
				return;
			}
			
			BlockCode.checkBlockCode(worldIn, blockPosForward, blockStateForward, blockCodeStartPos, blockCodeStartState);
		}
	}
	
	public static void setBlockStatusReady(World worldIn, BlockPos pos, IBlockState state, BlockPos blockCodeStartPos, IBlockState blockCodeStartState, boolean runCode) {
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		Block block = state.getBlock();
		
		IBlockState blockCodeState = block.getDefaultState().withProperty(FACING, enumFacing).withProperty(STATUS, EnumStatus.READY);
		worldIn.setBlockState(pos, blockCodeState);
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeEnd.getUnlocalizedName())) {
			if(runCode == true) {
				BlockCode.setBlockStatusRun(worldIn, blockCodeStartPos, blockCodeStartState, blockCodeStartPos, blockCodeStartState);
			}
			
			return;
		}
		
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		
		BlockCode.setBlockStatusReady(worldIn, blockPosForward, blockStateForward, blockCodeStartPos, blockCodeStartState, runCode);
	}
	
	public static void setBlockStatusRun(World worldIn, BlockPos pos, IBlockState state, BlockPos blockCodeStartPos, IBlockState blockCodeStartState) {
		if(BlockRobot.robotPos == null) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Exec: There is no Robot block"));
			
			BlockCodeStart.isCodeRun = false;
			BlockCode.setBlockStatusReady(worldIn, BlockCodeStart.codeRunBlockStartPos, BlockCodeStart.codeRunBlockStartState, BlockCodeStart.codeRunBlockStartPos, BlockCodeStart.codeRunBlockStartState, false);
			
			return;
		}
		
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		Block block = state.getBlock();
		
		IBlockState blockCodeState = block.getDefaultState().withProperty(FACING, enumFacing).withProperty(STATUS, EnumStatus.RUN);
		worldIn.setBlockState(pos, blockCodeState);
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeEnd.getUnlocalizedName())) {
			BlockCode.prepareCodeCodeRun(worldIn, blockCodeStartPos, blockCodeStartState);
			
			return;
		}
		
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		
		BlockCode.setBlockStatusRun(worldIn, blockPosForward, blockStateForward, blockCodeStartPos, blockCodeStartState);
	}
	
	public static void prepareCodeCodeRun(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		
		BlockCodeStart.codeRunTick = 0;
		BlockCodeStart.codeRunBlockPos = blockPosForward;
		BlockCodeStart.codeRunBlockState = blockStateForward;
		BlockCodeStart.codeRunBlockStartPos = pos;
		BlockCodeStart.codeRunBlockStartState = state;
		BlockCodeStart.isCodeRun = true;
	}
	
	public static void setBlockStatusError(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		Block block = state.getBlock();
		
		IBlockState blockCodeState = block.getDefaultState().withProperty(FACING, enumFacing).withProperty(STATUS, EnumStatus.ERROR);
		worldIn.setBlockState(pos, blockCodeState);
		
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
		setBlockStatusDisabledBehind(worldIn, blockPosBehind, blockStateBehind);
		
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		setBlockStatusDisabledForward(worldIn, blockPosForward, blockStateForward);
	}
	
	public static void setBlockStatusDisabledBehind(World worldIn, BlockPos pos, IBlockState state) {
		Block block = state.getBlock();
		
		if(!block.getUnlocalizedName().contains(Blocks.blockCodeUnlocalizedNamePrefix)) {
			return;
		}
		
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		
		IBlockState blockCodeState = block.getDefaultState().withProperty(FACING, enumFacing).withProperty(STATUS, EnumStatus.DISABLED);
		worldIn.setBlockState(pos, blockCodeState);
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
			return;
		}
		
		BlockPos blockPosBehind = pos.offset(enumFacing.getOpposite());
		IBlockState blockStateBehind = worldIn.getBlockState(blockPosBehind);
		
		BlockCode.setBlockStatusDisabledBehind(worldIn, blockPosBehind, blockStateBehind);
	}
	
	public static void setBlockStatusDisabledForward(World worldIn, BlockPos pos, IBlockState state) {
		Block block = state.getBlock();
		
		if(!block.getUnlocalizedName().contains(Blocks.blockCodeUnlocalizedNamePrefix)) {
			return;
		}
		else if(block.getUnlocalizedName().equals(Blocks.blockCodeStart.getUnlocalizedName())) {
			return;
		}
		
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		
		IBlockState blockCodeState = block.getDefaultState().withProperty(FACING, enumFacing).withProperty(STATUS, EnumStatus.DISABLED);
		worldIn.setBlockState(pos, blockCodeState);
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeEnd.getUnlocalizedName())) {
			return;
		}
		
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		
		BlockCode.setBlockStatusDisabledForward(worldIn, blockPosForward, blockStateForward);
	}
	
	public static void execBlockCode(World worldIn, BlockPos pos, IBlockState state) {
		if(BlockRobot.robotPos == null) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Exec: There is no Robot block"));
			
			BlockCodeStart.isCodeRun = false;
			BlockCode.setBlockStatusReady(worldIn, BlockCodeStart.codeRunBlockStartPos, BlockCodeStart.codeRunBlockStartState, BlockCodeStart.codeRunBlockStartPos, BlockCodeStart.codeRunBlockStartState, false);
			
			return;
		}
		
		Block block = state.getBlock();
		
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		BlockPos blockPosForward = pos.offset(enumFacing);
		IBlockState blockStateForward = worldIn.getBlockState(blockPosForward);
		Block blockForward = blockStateForward.getBlock();
		
		if(block.getUnlocalizedName().equals(Blocks.blockCodeMove.getUnlocalizedName())) {
			IBlockState blockRobotState = worldIn.getBlockState(BlockRobot.robotPos);
			Block blockRobot = blockRobotState.getBlock();
			EnumFacing enumRobotFacing = (EnumFacing)blockRobotState.getValue(FACING);
			BlockPos blockRobotPosForward = BlockRobot.robotPos.offset(enumRobotFacing);
			//BlockPos blockRobotPosForward = BlockRobot.robotPos.up();
			//BlockPos blockRobotPosForward = BlockRobot.robotPos.down();
			
			if(!worldIn.isAirBlock(blockRobotPosForward)) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Exec: Move - There is a block in front"));
				
				BlockCodeStart.isCodeRun = false;
				BlockCode.setBlockStatusReady(worldIn, BlockCodeStart.codeRunBlockStartPos, BlockCodeStart.codeRunBlockStartState, BlockCodeStart.codeRunBlockStartPos, BlockCodeStart.codeRunBlockStartState, false);
				
				return;
			}
			
			worldIn.setBlockToAir(BlockRobot.robotPos);
			IBlockState blockNewRobotState = blockRobot.getDefaultState().withProperty(FACING, enumRobotFacing);
			worldIn.setBlockState(blockRobotPosForward, blockNewRobotState);
			BlockRobot.robotPos = blockRobotPosForward;
		}
		else if(block.getUnlocalizedName().equals(Blocks.blockCodeRotate.getUnlocalizedName())) {
			IBlockState blockRobotState = worldIn.getBlockState(BlockRobot.robotPos);
			Block blockRobot = worldIn.getBlockState(BlockRobot.robotPos).getBlock();
			EnumFacing enumRobotFacing = (EnumFacing)blockRobotState.getValue(FACING);
			
			EnumFacing enumNewRobotFacing = enumRobotFacing.rotateY();
			//EnumFacing enumNewRobotFacing = enumRobotFacing.rotateYCCW();
			IBlockState blockNewRobotState = blockRobot.getDefaultState().withProperty(FACING, enumNewRobotFacing);
			worldIn.setBlockState(BlockRobot.robotPos, blockNewRobotState);
		}
		else if(block.getUnlocalizedName().equals(Blocks.blockCodeBreak.getUnlocalizedName())) {
			IBlockState blockRobotState = worldIn.getBlockState(BlockRobot.robotPos);
			EnumFacing enumRobotFacing = (EnumFacing)blockRobotState.getValue(FACING);
			BlockPos blockRobotPosForward = BlockRobot.robotPos.offset(enumRobotFacing);
			
			if(worldIn.isAirBlock(blockRobotPosForward)) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Exec: Break - There is no block to break in front"));
				
				BlockCodeStart.isCodeRun = false;
				BlockCode.setBlockStatusReady(worldIn, BlockCodeStart.codeRunBlockStartPos, BlockCodeStart.codeRunBlockStartState, BlockCodeStart.codeRunBlockStartPos, BlockCodeStart.codeRunBlockStartState, false);
				
				return;
			}
			
			worldIn.destroyBlock(blockRobotPosForward, true);
		}
		
		if(blockForward.getUnlocalizedName().equals(Blocks.blockCodeEnd.getUnlocalizedName())) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Exec: End"));
			
			BlockCodeStart.isCodeRun = false;
			BlockCode.setBlockStatusReady(worldIn, BlockCodeStart.codeRunBlockStartPos, BlockCodeStart.codeRunBlockStartState, BlockCodeStart.codeRunBlockStartPos, BlockCodeStart.codeRunBlockStartState, false);
			
			return;
		}
		
		BlockCodeStart.codeRunBlockPos = blockPosForward;
		BlockCodeStart.codeRunBlockState = blockStateForward;
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
}
