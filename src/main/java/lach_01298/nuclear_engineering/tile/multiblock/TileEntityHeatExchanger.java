package lach_01298.nuclear_engineering.tile.multiblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockItemHatch;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockReactorController;
import lach_01298.nuclear_engineering.fission.FissionFuel;
import lach_01298.nuclear_engineering.fission.NeutronReflectors;
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

public class TileEntityHeatExchanger extends TileEntityMultiblockController implements ITickable
{

	
	
	private BlockPos fluidInput;
	private BlockPos fluidOutput;
	//reactor coolant ie heavy water
	private BlockPos coolantInput;
	private BlockPos coolantOutput;
	
	private final int waterUsagePerTick = 10;
	
	
	private int water = 0;
	private int coolant = 0;

	
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
		TileEntityFluidHatch inputC = (TileEntityFluidHatch) this.getWorld().getTileEntity(coolantInput);
		TileEntityFluidHatch outputC = (TileEntityFluidHatch) this.getWorld().getTileEntity(coolantOutput);
		if(input == null || output == null|| inputC == null ||outputC == null)
		{
			return;
		}
		
		if(input.getTank(0).getFluid() != null &&inputC.getTank(0).getFluid() != null)
		{
			if(input.getTank(0).getFluid().getFluid() == FluidRegistry.WATER && inputC.getTank(0).getFluid().getFluid() == NEFluids.superHeatedHeavyWater)
			{
				FluidStack w = input.getTank(0).drainInternal(waterUsagePerTick, true);
				FluidStack c = inputC.getTank(0).drainInternal(waterUsagePerTick, true);
				
				if(water ==0 && coolant ==0)
				{
					if(w != null && c != null)
					{
						if(w.amount != c.amount)
						{
							input.getTank(0).fillInternal(new FluidStack(input.getTank(0).getFluid().getFluid(), waterUsagePerTick), true);
							inputC.getTank(0).fillInternal(new FluidStack(inputC.getTank(0).getFluid().getFluid(), waterUsagePerTick), true);

						}
						else
						{
							water = output.getTank(0).fillInternal(new FluidStack(NEFluids.steam, waterUsagePerTick), true);
							coolant = outputC.getTank(0).fillInternal(new FluidStack(NEFluids.heavyWater, waterUsagePerTick), true);
						}
					}

				}
				else
				{
					water = output.getTank(0).fillInternal(new FluidStack(NEFluids.steam, waterUsagePerTick), true);
					coolant = outputC.getTank(0).fillInternal(new FluidStack(NEFluids.heavyWater, waterUsagePerTick), true);
				}
			}
		}
	}
	
	public boolean isValidMultiBlock(EntityPlayer player)
	{
		
		
		World world = this.getWorld();
		BlockPos pos = this.getPos();
		IBlockState core = RegisterBlocks.heatExchanger.getDefaultState();
		IBlockState mCase = RegisterBlocks.machineCase.getDefaultState();
	

		IBlockState inFluid = RegisterBlocks.fluidHatch.getStateFromMeta(BlockItemHatch.HatchType.INPUT.ordinal());
		IBlockState outFluid = RegisterBlocks.fluidHatch.getStateFromMeta(BlockItemHatch.HatchType.OUTPUT.ordinal());
		List<IBlockState> mCaseList = new ArrayList();
		mCaseList.add(inFluid);
		mCaseList.add(outFluid);
		
		int coreHight = 2;
		int coreWidth = 1;
		

		BlockPos coreMin = pos.add(-MBU.detectEdge(this, EnumFacing.WEST, core), MBU.detectEdge(this, EnumFacing.DOWN, core), -MBU.detectEdge(this, EnumFacing.NORTH, core));
		BlockPos coreMax = pos.add(MBU.detectEdge(this, EnumFacing.EAST, core), MBU.detectEdge(this, EnumFacing.UP, core), MBU.detectEdge(this, EnumFacing.SOUTH, core));

		
		if(Math.abs(coreMin.getX()-coreMax.getX())+1 != coreWidth ||Math.abs(coreMin.getY()-coreMax.getY())+1 != coreHight||Math.abs(coreMin.getZ()-coreMax.getZ())+1 != coreWidth)
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "core wrong size"));
			}
			return false;
		}
		
		
		if(!MBU.checkBlocks(this, coreMin, coreMax, core))
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "core incomplete"));
			}
			return false;
		}

		BlockPos min = coreMin.add(-1, -1, -1);
		BlockPos max = coreMax.add(1, 1, 1);

			// checks case
			if(!MBU.checkBlocksBoxCylinder(this, min, max, mCase, EnumFacing.Axis.Y, 1, mCaseList))
			{
				if(player != null)
				{
					player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "case incomplete"));
				}
				return false;
			}
		
			// checks and add Fluid input
			Set<BlockPos> cashedblocks = MBU.findBlocksBoxCylinder(this, min, max, inFluid, EnumFacing.Axis.Y, 1);
			if(cashedblocks.size() > 1 || cashedblocks.isEmpty())
			{
				if(player != null)
				{
					System.out.println("?");
					player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "there needs to be ONE Fluid Input on the side"));
				}
				return false;
			}
			fluidInput = (BlockPos) cashedblocks.toArray()[0];

			// checks and add Fluid output
			cashedblocks = MBU.findBlocksBoxCylinder(this, min, max, outFluid,EnumFacing.Axis.Y, 1);
			if(cashedblocks.size() > 1 || cashedblocks.isEmpty())
			{
				if(player != null)
				{
					player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "there needs to be ONE Fluid Output on the side"));
				}
				return false;
			}
			fluidOutput = (BlockPos) cashedblocks.toArray()[0];

			//checks coolant input
			
			if(!MBU.isBlock(this, coreMin.down(), inFluid))
			{
				if(player != null)
				{
					player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "there needs to be a Fluid Input on the bottom"));
				}
				return false;
			}
			coolantInput = coreMin.down();

			//checks coolant output
			if(!MBU.isBlock(this, coreMax.up(), outFluid))
			{
				if(player != null)
				{
					player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "there needs to be a Fluid Output on the top"));
				}
				return false;
			}
			coolantOutput = coreMax.up();

			
			
			
			
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockFormed"));
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
				this.coolantInput = io.get(2);
				this.coolantOutput = io.get(3);
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
			if(!(fluidInput == null || fluidOutput == null || coolantInput == null || coolantOutput == null))
			{
			
				io.add(fluidInput);
				io.add(fluidOutput);
				io.add(coolantInput);
				io.add(coolantOutput);

				compound.setTag("io", MBU.writeBlockPostions(new NBTTagCompound(), io));
			}
		}

		return compound;
	}
}
