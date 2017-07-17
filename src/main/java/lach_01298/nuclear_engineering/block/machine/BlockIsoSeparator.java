package lach_01298.nuclear_engineering.block.machine;

import java.util.Random;

import javax.annotation.Nullable;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.gui.GUI_ID;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.tile.machine.TileEntityIso;
import lach_01298.nuclear_engineering.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockIsoSeparator extends BlockContainer implements ItemModelProvider
{
	public final String name = "machineIsoSeparator";

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool MASTER = PropertyBool.create("master");

	// General block stuff
	public BlockIsoSeparator()
	{
		super(Material.IRON);
		setHardness(Constants.HARDNESS_ORE);
		setResistance(Constants.RESISTANCE_ORE);
		setHarvestLevel("pickaxe", Constants.MINING_LEVEL_STONE);
		setCreativeTab(NuclearEngineering.NETab);
		setRegistryName(name);
		setUnlocalizedName(NuclearEngineering.MOD_ID + "." + name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(MASTER, true));
	}

	public EnumBlockRenderType getRenderType(IBlockState state)
	{

		if(state.getValue(MASTER) == true)
		{
			return EnumBlockRenderType.MODEL;
		}
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public void registerItemModel(Item itemblock)
	{
		NuclearEngineering.proxy.registerItemRenderer(itemblock, 0, this.name);
	}

	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(RegisterBlocks.isoSeparator);
	}

	// special block stuff

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		if(meta < 4)
		{
		//	return new TileEntityIso();
		}
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{

		if(world.isRemote)
		{

			return true;
		}

		if(world.getBlockState(pos).getBlock() != this)
		{

			return false;
		}
		else
		{
			if(state.getValue(MASTER))
			{
				TileEntity te = world.getTileEntity(pos);
				if(!(te instanceof TileEntityIso))
				{

					return false;

				}

				player.openGui(NuclearEngineering.instance, GUI_ID.ISOTOPE, world, pos.getX(), pos.getY(), pos.getZ());

				return true;
			}
			else
			{
				TileEntity te = world.getTileEntity(pos.down());
				if(!(te instanceof TileEntityIso))
				{
					return false;
				}

				player.openGui(NuclearEngineering.instance, GUI_ID.ISOTOPE, world, pos.down().getX(), pos.down().getY(), pos.down().getZ());

				return true;
			}

		}
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if(hasTileEntity(state) && !(this instanceof BlockContainer))
		{
			worldIn.removeTileEntity(pos);
		}
		if(state.getValue(MASTER)== true)
		{
			worldIn.setBlockToAir(pos.up());
		}
		else
		{
			worldIn.setBlockToAir(pos.down());
			worldIn.removeTileEntity(pos.down());
		}
	}

	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		this.setDefaultFacing(worldIn, pos, state);
	}

	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
		if(state.getValue(MASTER) == true)
		{
			BlockPos slavePos = pos.add(0, 1, 0);
			worldIn.setBlockState(slavePos, state.withProperty(MASTER, false));
		}
	}

	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return super.canPlaceBlockAt(worldIn, pos)
				&& worldIn.isAirBlock(pos.up());
	}

	// blockState stuff
	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
	{
		if(!worldIn.isRemote)
		{
			IBlockState iblockstate = worldIn.getBlockState(pos.north());
			IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
			IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
			IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if(enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock()
					&& !iblockstate1.isFullBlock())
			{
				enumfacing = EnumFacing.SOUTH;
			}
			else if(enumfacing == EnumFacing.SOUTH
					&& iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
			{
				enumfacing = EnumFacing.NORTH;
			}
			else if(enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock()
					&& !iblockstate3.isFullBlock())
			{
				enumfacing = EnumFacing.EAST;
			}
			else if(enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock()
					&& !iblockstate2.isFullBlock())
			{
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	public IBlockState getStateFromMeta(int meta)
	{

		if(meta < 4)
		{
			return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(MASTER, true);
		}
		else
		{
			return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta - 4)).withProperty(MASTER, false);
		}
	}

	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getHorizontalIndex()
				+ (state.getValue(MASTER) ? 0 : 4);
	}

	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, MASTER });
	}

}
