package lach_01298.nuclear_engineering.tile.multiblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockComponentDirectional;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockItemHatch;
import lach_01298.nuclear_engineering.fluid.NEFluids;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityTurbine extends TileEntityMultiblockController implements ITickable
{

	
	
	private BlockPos fluidInput;
	private BlockPos fluidOutput;
	private BlockPos powerOutput;
	
	private final int steamToPowerRatio = 1000;

	private int water = 0;
	private final int waterUsagePerTick = 100;
	


	
	@Override
	public void update()
	{
		if(this.isMasterTile() && this.isMultiBlockFormed()&& !this.getWorld().isRemote)
		{	
			run();
		}
	}



	@Override
	public void checkMultiBlock(EntityPlayer player)
	{
		this.isMultiblockFormed = this.isValidMultiBlock(player);
		syncData();

	}


	private void run()
	{

		TileEntityFluidHatch input = (TileEntityFluidHatch) this.getWorld().getTileEntity(fluidInput);
		TileEntityFluidHatch output = (TileEntityFluidHatch) this.getWorld().getTileEntity(fluidOutput);
		TileEntityPowerHatch power = (TileEntityPowerHatch) this.getWorld().getTileEntity(powerOutput);
		//System.out.println(input);
		if(input.getTank(0).getFluid() != null)
		{
			
			if(input.getTank(0).getFluid().getFluid() == NEFluids.steam)
			{
				FluidStack w = input.getTank(0).drainInternal(waterUsagePerTick, true);

				if(water == 0)
				{
					if(w != null)
					{
						output.getTank(0).fillInternal(new FluidStack(FluidRegistry.WATER, waterUsagePerTick), true);
						power.energy.generateEnergy(waterUsagePerTick*steamToPowerRatio);
					}

				}
			}
		}
		else if(water > 0)
		{
			water = output.getTank(0).fillInternal(new FluidStack(FluidRegistry.WATER, waterUsagePerTick), true);
			power.energy.generateEnergy((waterUsagePerTick-water)*steamToPowerRatio);
		}
	}
	
	
	public boolean isValidMultiBlock(EntityPlayer player)
	{

		World world = this.getWorld();
		BlockPos pos = this.getPos();
		IBlockState core = RegisterBlocks.turbine.getDefaultState();
		IBlockState mCase = RegisterBlocks.machineCase.getDefaultState();
		IBlockState radiator = RegisterBlocks.radiator.getDefaultState();
		IBlockState motor = RegisterBlocks.motor.getDefaultState();

		IBlockState inFluid = RegisterBlocks.fluidHatch.getStateFromMeta(BlockItemHatch.HatchType.INPUT.ordinal());
		IBlockState outFluid = RegisterBlocks.fluidHatch.getStateFromMeta(BlockItemHatch.HatchType.OUTPUT.ordinal());
		IBlockState power = RegisterBlocks.powerHatch.getStateFromMeta(BlockItemHatch.HatchType.OUTPUT.ordinal());
		List<IBlockState> mCaseList = new ArrayList();
		mCaseList.add(inFluid);
		mCaseList.add(outFluid);

		int coreHight = 1;
		int coreWidth = 3;
		int corelength = 1;
		EnumFacing.Axis axis;

		BlockPos coreMin = pos.add(-MBU.detectEdge(this, EnumFacing.WEST, core), MBU.detectEdge(this, EnumFacing.DOWN, core), -MBU.detectEdge(this, EnumFacing.NORTH, core));
		BlockPos coreMax = pos.add(MBU.detectEdge(this, EnumFacing.EAST, core), MBU.detectEdge(this, EnumFacing.UP, core), MBU.detectEdge(this, EnumFacing.SOUTH, core));
		
		//checks turbine
		if(Math.abs(coreMin.getY() - coreMax.getY()) + 1 == coreHight
				&& Math.abs(coreMin.getX() - coreMax.getX()) + 1 == coreWidth
				&& Math.abs(coreMin.getZ() - coreMax.getZ()) + 1 == corelength)
		{
			axis = EnumFacing.Axis.X;
		}
		else if(Math.abs(coreMin.getY() - coreMax.getY()) + 1 == coreHight
				&& Math.abs(coreMin.getX() - coreMax.getX()) + 1 == corelength
				&& Math.abs(coreMin.getZ() - coreMax.getZ()) + 1 == coreWidth)
		{
			axis = EnumFacing.Axis.Z;
		}
		else
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "turbine wrong size"));
			}
			return false;
		}

		if(!MBU.checkBlocks(this, coreMin, coreMax, core))
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "turbine incomplete"));
			}
			return false;
		}

		BlockPos min = coreMin.add(-1, -1, -1);
		BlockPos max = coreMax.add(1, 1, 1);

		// checks case
		if(!MBU.checkBlocksBoxCylinder(this, min, max, mCase, axis, 1, mCaseList))
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "case incomplete"));
			}
			return false;
		}

		EnumFacing front;

		front = EnumFacing.getFacingFromAxis(EnumFacing.AxisDirection.POSITIVE, axis);
		
		// checks fluid input
		if(MBU.isBlock(this, coreMax.offset(front), inFluid))
		{

			fluidInput = coreMax.offset(front);
		}
		else if(MBU.isBlock(this, coreMin.offset(front.getOpposite()), inFluid))
		{
			front = EnumFacing.getFacingFromAxis(EnumFacing.AxisDirection.NEGATIVE, axis);
			fluidInput = coreMin.offset(front);
		}
		else
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "there needs to be a Fluid input on the front"));
			}
			return false;
		}

		
		//checks motors
		BlockPos motorBack = fluidInput.offset(front.getOpposite(), 5);
	
		if(!(MBU.checkBlocks(this, motorBack, motorBack.offset(front), motor.withProperty(BlockComponentDirectional.FACING, front)) || MBU.checkBlocks(this, motorBack, motorBack.offset(front), motor.withProperty(BlockComponentDirectional.FACING, front.getOpposite()))))
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "there needs to be 2 motors behind the turbines"));
			}
			return false;
		}

		if(!MBU.isBlock(this, motorBack.offset(front.getOpposite()), power))
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "there needs to be a power Output on the back"));
			}
			return false;
		}
		powerOutput = motorBack.offset(front.getOpposite());

		BlockPos motorfront = motorBack.offset(front);

		BlockPos side1 = motorfront.add(front.rotateAround(EnumFacing.Axis.Y).getFrontOffsetX(), -1, front.rotateAround(EnumFacing.Axis.Y).getFrontOffsetZ());
		BlockPos side2 = motorfront.add(front.rotateAround(EnumFacing.Axis.Y).rotateAround(EnumFacing.Axis.Y).rotateAround(EnumFacing.Axis.Y).getFrontOffsetX(), 1, front.rotateAround(EnumFacing.Axis.Y).rotateAround(EnumFacing.Axis.Y).rotateAround(EnumFacing.Axis.Y).getFrontOffsetZ());

		Set<BlockPos> cashedblocks = MBU.findBlocksBoxCylinder(this, side1, side2, outFluid, axis, 1);

		if(cashedblocks.size() > 1 || cashedblocks.isEmpty())
		{
			if(player != null)
			{

				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "there needs to be ONE Fluid output on the around the front motor"));
			}
			return false;
		}
		fluidOutput = (BlockPos) cashedblocks.toArray()[0];

		if(!MBU.checkBlocksBoxCylinder(this, side1.offset(front.getOpposite(), 2), side2.offset(front.getOpposite()), radiator, axis, 1))
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID
						+ ".multiblockNotFormed", "radiator incomplete"));
			}
			return false;
		}

		if(player != null)
		{
			player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID
					+ ".multiblockFormed"));
		}

		return true;
	}

	
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		
		if(this.isMasterTilePos())
		{
			
			this.isMultiblockFormed = compound.getBoolean("formed");
			
			List<BlockPos> io = MBU.readBlockPostions(compound.getCompoundTag("io"));
			if(!io.isEmpty())
			{
				this.fluidInput = io.get(0);
				this.fluidOutput = io.get(1);
				this.powerOutput = io.get(2);
				
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{

		super.writeToNBT(compound);
		if(this.isMasterTile())
		{
			compound.setBoolean("formed", this.isMultiBlockFormed());
	
			List<BlockPos> io = new ArrayList<BlockPos>();
			if(!(fluidInput == null || fluidOutput == null || powerOutput == null))
			{
			
				io.add(fluidInput);
				io.add(fluidOutput);
				io.add(powerOutput);
				
				compound.setTag("io", MBU.writeBlockPostions(new NBTTagCompound(), io));
			}
		}

		return compound;
	}
}
