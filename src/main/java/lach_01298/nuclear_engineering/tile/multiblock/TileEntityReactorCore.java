package lach_01298.nuclear_engineering.tile.multiblock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockItemHatch;
import lach_01298.nuclear_engineering.block.multiblockPart.BlockReactorController;
import lach_01298.nuclear_engineering.fission.FissionFuel;
import lach_01298.nuclear_engineering.fission.Moderators;
import lach_01298.nuclear_engineering.fission.NeutronReflectors;
import lach_01298.nuclear_engineering.fluid.NEFluids;
import lach_01298.nuclear_engineering.network.MessageMultiblock;
import lach_01298.nuclear_engineering.network.MessageReactor;
import lach_01298.nuclear_engineering.network.NEPacketHandler;
import lach_01298.nuclear_engineering.util.Maths;
import lach_01298.nuclear_engineering.util.MultiblockUtil;
import lach_01298.nuclear_engineering.util.UtilItem;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityReactorCore extends TileEntityMultiblockController implements ITickable
{

	

	private static final int GenerationTime = 5;
	private int timer = GenerationTime;

	private BlockPos itemInput;
	private BlockPos itemOutput;
	private BlockPos fluidInput;
	private BlockPos fluidOutput;
	private Set<BlockPos> controllers = new HashSet<BlockPos>();
	private Set<BlockPos> sensors = new HashSet<BlockPos>();

	// reactor design variables
	private double controlRodLevel; // the level of insertion 1 = 100% in
	private double moderatorFactor; // higher = more fission occurs
	private double reflectorFactor; // % of neutrons not reflected
	private int size; // amount of cores
	private int heatLossFactor; // determines the amount of heat lost from the
								// core to the coolant
	private FissionFuel fuel;

	// config
	private static double powerScalar = 2.8;
	private static final int meltDownTemp = 3000;
	// constants
	private static final double baseProbOfAbsorb = 0.2;
	private static final double baseProbOfEscape = 0.5;
	private static final int neutronLifetime = 7;
	private static final double tempratureScalar = 2;
	private static final int roomTemp = 298; // 25 C in Kelvin
	private static final int heatCapacity = 2500;
	private static final int addedNuetrons = 1;
	private static final double fuelBurnUprate = 0.00005;
	

	// Variables
	private int NeutronNumber = 0;
	private int temprature = roomTemp;
	private double fuelDensity;
	private double fuelUsed;
	


	// Actual the amount of steam
	private int power;

	public TileEntityReactorCore()
	{
		super();
		this.controlRodLevel = 1;
		this.moderatorFactor = Moderators.defualtModeratorFactor;
		this.reflectorFactor = NeutronReflectors.defualtReflectorFactor;
		this.fuelDensity = 0;
		this.fuel = null;
		this.size = 1;
		this.heatLossFactor = NeutronReflectors.defualtHeatLossFactor;
		this.fuelUsed = 0;

	}

	@Override
	public void update()
	{
		if(this.isMasterTile() && this.isMultiBlockFormed()&& !this.getWorld().isRemote)
		{
			inputFuel();
			
			if(timer < 0)
			{
				if(this.fuel != null)
				{
					run();
				}
				timer = GenerationTime;
				meltDown();
				syncData();
			}
			else
			{
				timer--;
			}

		}
	}

	private void meltDown()
	{
		if(temprature > meltDownTemp)
		{
			//TODO better explosions
			//this.getWorld().createExplosion(null, this.pos.getX(), this.pos.getY(), this.pos.getZ(), 100, true);
		}

	}

	private void run()
	{
		TileEntityFluidHatch inF = (TileEntityFluidHatch) this.getWorld().getTileEntity(fluidInput);
		TileEntityFluidHatch outF = (TileEntityFluidHatch) this.getWorld().getTileEntity(fluidOutput);
		
		if(inF.getTank(0).getFluid() == null)
		{
			moderatorFactor = Moderators.defualtModeratorFactor;
		}
		else
		{
			moderatorFactor = Moderators.getModeratorFactor(inF.getTank(0).getFluid().getFluid());
		}
		
		
		// MATHS
		double probOfAbsorb = baseProbOfAbsorb * controlRodLevel + 0.1;
		double probOfEscape = baseProbOfEscape * reflectorFactor * Maths.log(temprature, 100);
		double probOfFission = moderatorFactor * fuel.getFuelCrossSection() * fuelDensity;
		double ProbOfImpact = 1 - probOfAbsorb - probOfEscape;
		double alpha = ProbOfImpact * probOfFission * fuel.getNeutronsRelased() - probOfAbsorb - probOfEscape;

		int ChangeInNeutrons = (int) (((alpha * NeutronNumber) / (neutronLifetime)) + addedNuetrons);
		int heatProduced = (int) (NeutronNumber * fuel.getFissionEnergy());
		
		int heatloss;
		if(inF.getTank(0).getFluid() == null)
		{
			heatloss = (int)((temprature - roomTemp) * Math.pow(size, (2.0 / 3.0)));
		}
		else
		{
			heatloss = (int) ((temprature - roomTemp) * Math.pow(size, (2.0 / 3.0)) * heatLossFactor);
		}
		

		
		NeutronNumber += ChangeInNeutrons;
		double intialFuelDensity = fuelDensity;
		
		fuelDensity *= Math.pow(Math.E, -fuelBurnUprate * Maths.log(NeutronNumber, 100));
		
		fuelUsed += intialFuelDensity-fuelDensity;
		
		
		power = (int) (heatloss * powerScalar);
		
		if(inF.getTank(0).getFluid() != null)
		{
			
			int before = inF.getTank(0).getFluidAmount();
			FluidStack coolant = inF.getTank(0).drainInternal(power/1000, true);
			int fluidAmount;
			
			if(coolant == null)
			{
				fluidAmount = 0;
			}
			else
			{
				if(inF.getTank(0).getFluid() != null)
				{
					fluidAmount	=inF.getTank(0).drainInternal(power/1000, true).amount;
					
				}
				else
				{
					fluidAmount = 0;
				}
				
			}
			 
			fluidAmount = outF.getTank(0).fillInternal(new FluidStack(NEFluids.superHeatedHeavyWater, fluidAmount), true);
			
			if(inF.getTank(0).getFluid() != null)
			{
				inF.getTank(0).fillInternal(new FluidStack(inF.getTank(0).getFluid().getFluid(), inF.getTank(0).getFluidAmount() + fluidAmount), true);
			}
			int after = inF.getTank(0).getFluidAmount();
			if(power <=0)
			{
				heatloss = (int) ((temprature - roomTemp) * Math.pow(size, (2.0 / 3.0)));
				
			}
			else
			{
				heatloss = (int) ((temprature - roomTemp) * Math.pow(size, (2.0 / 3.0)) * heatLossFactor*((before-after)*1000/power));
				
			}

			if(heatloss <(int) ((temprature - roomTemp) * Math.pow(size, (2.0 / 3.0))))
			{
				heatloss = (int) ((temprature - roomTemp) * Math.pow(size, (2.0 / 3.0)));
			}
			
		}
		int changeInTemprature = (heatProduced - heatloss) / (size * heatCapacity);

		temprature += changeInTemprature;
		
		output();
		

	}

	private void inputFuel()
	{
		TileEntityItemHatch input = (TileEntityItemHatch) this.getWorld().getTileEntity(itemInput);
		ItemStack item = input.getSlotHandler().getStackInSlot(0);

		if(this.fuel == null)
		{
			if(item != null)
			{
				if(FissionFuel.getFuel(item) != null)
				{
					fuelDensity += FissionFuel.getFuel(item).getStartingFuelDensity() / (10.0*(double)(size));
					this.fuel = FissionFuel.getFuel(item);
					item.stackSize--;
					
					if(item.stackSize < 1)
					{
						input.getSlotHandler().setStackInSlot(0, null);
					}
				}
			}
		}
		else if(fuelDensity / fuel.getStartingFuelDensity() < 1-(0.1/(double)(size)))
		{
			if(FissionFuel.getFuel(item) == this.fuel)
			{
				
				fuelDensity += this.fuel.getStartingFuelDensity() / (10.0*(double)(size));
				item.stackSize--;
				
				if(item.stackSize < 1)
				{
					input.getSlotHandler().setStackInSlot(0, null);
				}
			}
		}
	}

	private void output()
	{
		TileEntityItemHatch output = (TileEntityItemHatch) this.getWorld().getTileEntity(itemOutput);
		ItemStack item = output.getSlotHandler().getStackInSlot(0);

		if(fuel != null)
		{
			if(item != null)
			{
				if(item.stackSize >= item.getMaxStackSize())
				{
					return;
				}
			}
			if(fuelUsed > this.fuel.getStartingFuelDensity()/(10.0*(double)(size)))
			{
				if(item != null)
				{
					item.stackSize += fuel.getOutput().stackSize;
				}
				else
				{
					output.getSlotHandler().setStackInSlot(0, fuel.getOutput());
				}
				fuelUsed = 0;
			}	
		}
	}

	@Override
	public void checkMultiBlock(EntityPlayer player)
	{
		this.isMultiblockFormed = this.isValidMultiBlock(player);

		if(!isMultiBlockFormed())
		{
			updateperipherals();
		}
		checkIO();
		syncData();

	}

	private void checkIO()
	{

		int redstone = 0;
		for(BlockPos controller : controllers)
		{
			if(this.getWorld().getStrongPower(controller) > redstone)
			{
				redstone = this.getWorld().getStrongPower(controller);
			}
		}
		if(redstone > 0)
		{
			
			controlRodLevel = 1.0 - redstone / 15.0;
		}
		else
		{
			
			controlRodLevel = 1;
		}

	}

	public boolean isMultiBlockFormed()
	{

		return this.isMultiblockFormed;
	}

	@Override
	public boolean isValidMultiBlock(EntityPlayer player)
	{

		World world = this.getWorld();
		BlockPos pos = this.getPos();
		IBlockState core = RegisterBlocks.reactorCore.getDefaultState();
		IBlockState rCase = RegisterBlocks.reactorCase.getDefaultState();
		List<IBlockState> reactorController = new ArrayList();
		for(int i = 0; i < 8; i++)
		{
			reactorController.add(RegisterBlocks.reactorController.getStateFromMeta(i));
		}

		IBlockState inItem = RegisterBlocks.itemHatch.getStateFromMeta(BlockItemHatch.HatchType.INPUT.ordinal());
		IBlockState outItem = RegisterBlocks.itemHatch.getStateFromMeta(BlockItemHatch.HatchType.OUTPUT.ordinal());
		IBlockState inFluid = RegisterBlocks.fluidHatch.getStateFromMeta(BlockItemHatch.HatchType.INPUT.ordinal());
		IBlockState outFluid = RegisterBlocks.fluidHatch.getStateFromMeta(BlockItemHatch.HatchType.OUTPUT.ordinal());

		List<IBlockState> rCaseList = new ArrayList();
		rCaseList.addAll(reactorController);
		rCaseList.add(inItem);
		rCaseList.add(outItem);
		rCaseList.add(inFluid);
		rCaseList.add(outFluid);

		BlockPos coreMin = pos.add(-MBU.detectEdge(this, EnumFacing.WEST, core), MBU.detectEdge(this, EnumFacing.DOWN, core), -MBU.detectEdge(this, EnumFacing.NORTH, core));
		BlockPos coreMax = pos.add(MBU.detectEdge(this, EnumFacing.EAST, core), MBU.detectEdge(this, EnumFacing.UP, core), MBU.detectEdge(this, EnumFacing.SOUTH, core));

		// checks core
		if(!MBU.checkBlocks(this, coreMin, coreMax, core))
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "core incomplete"));
			}
			return false;
		}
		size = (coreMax.getX()- coreMin.getX()+1)*(coreMax.getY()- coreMin.getY()+1)*(coreMax.getZ()- coreMin.getZ()+1);
		
		
		BlockPos min = coreMin.add(-1, -1, -1);
		BlockPos max = coreMax.add(1, 1, 1);

		// checks reflector
		if(NeutronReflectors.isNeutronReflector(world.getBlockState(min)))
		{
			IBlockState reflector = world.getBlockState(min);
			reflectorFactor = NeutronReflectors.getRefectorFactor(reflector);
			heatLossFactor = NeutronReflectors.getHeatFactor(reflector);

			if(!MBU.checkBlocksBox(this, min, max, reflector, 1))
			{
				if(player != null)
				{
					player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "reflector incomplete"));
				}
				return false;
			}

			min = min.add(-1, -1, -1);
			max = max.add(1, 1, 1);
			// checks case
			if(!MBU.checkBlocksBox(this, min, max, rCase, 1, rCaseList))
			{
				if(player != null)
				{
					player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "case incomplete"));
				}

				return false;
			}

		}
		else
		{
			// checks case
			if(!MBU.checkBlocksBox(this, min, max, rCase, 1, rCaseList))
			{
				if(player != null)
				{
					player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "case incomplete"));
				}
				return false;
			}
		}

		// checks and adds controllers
		controllers = MBU.findBlocksBox(this, min, max, reactorController, 1);
		if(controllers.isEmpty())
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "no controllers"));
			}
			return false;
		}

		// adds sensors
		// sensors = findBlocksBox(min, max, reactorController, 1);

		// checks and add Item input
		Set<BlockPos> cashedblocks = MBU.findBlocksBox(this, min, max, inItem, 1);
		if(cashedblocks.size() > 1 || cashedblocks.isEmpty())
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "there needs to be ONE Item Input"));
			}
			return false;
		}
		itemInput = (BlockPos) cashedblocks.toArray()[0];

		// checks and add Item output
		cashedblocks = MBU.findBlocksBox(this, min, max, outItem, 1);
		if(cashedblocks.size() > 1 || cashedblocks.isEmpty())
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "there needs to be ONE Item Output"));
			}
			return false;
		}
		itemOutput = (BlockPos) cashedblocks.toArray()[0];

		// checks and add Fluid input
		cashedblocks = MBU.findBlocksBox(this, min, max, inFluid, 1);
		if(cashedblocks.size() > 1 || cashedblocks.isEmpty())
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "there needs to be ONE Fluid Input"));
			}
			return false;
		}
		fluidInput = (BlockPos) cashedblocks.toArray()[0];

		// checks and add Fluid output
		cashedblocks = MBU.findBlocksBox(this, min, max, outFluid, 1);
		if(cashedblocks.size() > 1 || cashedblocks.isEmpty())
		{
			if(player != null)
			{
				player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockNotFormed", "there needs to be ONE Fluid Output"));
			}
			return false;
		}
		fluidOutput = (BlockPos) cashedblocks.toArray()[0];

		if(player != null)
		{
			player.addChatComponentMessage(new TextComponentTranslation(NuclearEngineering.MOD_ID + ".multiblockFormed"));
		}
		for(BlockPos controller : controllers)
		{
			IBlockState state = world.getBlockState(controller);
			world.setBlockState(controller, state.withProperty(BlockReactorController.ON, true));
			// TileEntityReactorController tile =(TileEntityReactorController)
			// world.getTileEntity(controller);
			// tile.syncData();
		}

		return true;
	}

	private void updateperipherals()
	{
		
		Set<BlockPos> peripheral = new HashSet<BlockPos>();

		peripheral.addAll(controllers);
		peripheral.addAll(sensors);
		peripheral.add(itemInput);
		peripheral.add(itemOutput);
		peripheral.add(fluidInput);
		peripheral.add(fluidOutput);
		if(!peripheral.isEmpty())
		{
			for(BlockPos per : peripheral)
			{
				if(per != null)
				{
					if(this.getWorld().getTileEntity(per) instanceof TileEntityMultiblock)
					{
						TileEntityMultiblock tile = (TileEntityMultiblock) this.getWorld().getTileEntity(per);
						tile.updateTile();
					}

				}

			}
		}

	}

	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		
		if(this.isMasterTilePos())
		{
			
			this.isMultiblockFormed = compound.getBoolean("formed");
			this.controlRodLevel = compound.getDouble("controlRod");
			this.fuelDensity = compound.getDouble("fuelDesity");
			this.moderatorFactor = compound.getDouble("moderatorFactor");
			this.reflectorFactor = compound.getDouble("reflectorFactor");
			this.heatLossFactor = compound.getInteger("heatFactor");
			this.size = compound.getInteger("size");
			this.temprature = compound.getInteger("temprature");
			this.NeutronNumber = compound.getInteger("neutronFlux");
			this.fuel = FissionFuel.getFuel(compound.getString("fuel"));
			this.fuelUsed = compound.getDouble("fuelUsed");
			this.controllers.addAll(MBU.readBlockPostions(compound.getCompoundTag("controllers")));
			this.sensors.addAll(MBU.readBlockPostions(compound.getCompoundTag("sensors")));
			List<BlockPos> io = MBU.readBlockPostions(compound.getCompoundTag("io"));
			if(!io.isEmpty())
			{
				this.itemInput = io.get(0);
				this.itemOutput = io.get(1);
				this.fluidInput = io.get(2);
				this.fluidOutput = io.get(3);
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
			compound.setDouble("controlRod", this.controlRodLevel);
			compound.setDouble("fuelDesity", this.fuelDensity);
			compound.setDouble("moderatorFactor", this.moderatorFactor);
			compound.setDouble("reflectorFactor", this.reflectorFactor);
			compound.setInteger("heatFactor", this.heatLossFactor);
			compound.setInteger("size", this.size);
			compound.setInteger("temprature", this.temprature);
			compound.setInteger("neutronFlux", this.NeutronNumber);
			compound.setDouble("fuelUsed",this.fuelUsed);
			if(this.fuel != null)
			{
				compound.setString("fuel", this.fuel.getName());
			}
			
			compound.setTag("controllers", MBU.writeBlockPostions(new NBTTagCompound(), this.controllers));
			compound.setTag("sensors", MBU.writeBlockPostions(new NBTTagCompound(), this.sensors));

			List<BlockPos> io = new ArrayList<BlockPos>();
			if(!(itemInput == null || itemOutput == null || fluidInput == null || fluidOutput == null))
			{
				io.add(itemInput);
				io.add(itemOutput);
				io.add(fluidInput);
				io.add(fluidOutput);

				compound.setTag("io", MBU.writeBlockPostions(new NBTTagCompound(), io));
			}

		}

		return compound;
	}

	public int getNeutronNumber()
	{
		return this.NeutronNumber;
	}

	public int getTemprature()
	{
		return this.temprature;
	}

	public int getSize()
	{
		return this.size;
	}

	public double getReflectorFactor()
	{
		return this.reflectorFactor;
	}

	public double getModeratorFactor()
	{
		return this.moderatorFactor;
	}

	public double getControlrodLevel()
	{
		return this.controlRodLevel;
	}

	public int getFuelLeft()
	{
		if(this.fuel != null)
		{
			return (int) ((this.fuelDensity / this.fuel.getStartingFuelDensity()) * 100.0);
		}
		return 0;
	}

	public String getFuelName()
	{
		if(this.fuel != null)
		{
			return this.fuel.getName();
		}
		return "no fuel";
	}

	public FissionFuel getFuel()
	{
		return this.fuel;
	}

	
	
	public void set(boolean formed, int tempratue, int fuelLeft, int neutronflux, double controlRod, double reflector, double moderator, String fuelName)
	{
		this.isMultiblockFormed = formed;
		this.temprature = tempratue;
		this.NeutronNumber = neutronflux;
		this.controlRodLevel = controlRod;
		this.reflectorFactor = reflector;
		this.moderatorFactor = moderator;
		this.fuel = FissionFuel.getFuel(fuelName);
		if(this.fuel != null)
		{
			this.fuelDensity = (double) (fuelLeft) / 100.0 * this.fuel.getStartingFuelDensity();
		}
	}

	public void syncData()
	{

		MessageReactor message = new MessageReactor(this);
		NEPacketHandler.INSTANCE.sendToAll(message);
		super.syncData();
	}

}
