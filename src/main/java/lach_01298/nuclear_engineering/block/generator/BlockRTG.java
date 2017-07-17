package lach_01298.nuclear_engineering.block.generator;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.tile.generator.TileEntityRTG;
import lach_01298.nuclear_engineering.util.Constants;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLever;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRTG extends BlockContainer implements ItemModelProvider
{
	public final String name;
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	protected static final AxisAlignedBB VERTICAL_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
	protected static final AxisAlignedBB NS_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.0D, 0.75D, 0.75D, 1.0D);
	protected static final AxisAlignedBB EW_AABB = new AxisAlignedBB(0.0D, 0.25D, 0.25D, 1.0D, 0.75D, 0.75D);
	
	public BlockRTG(String name)
	{
		super(Material.IRON);
		this.name = "generator"+name+"RTG";
		
		setHardness(Constants.HARDNESS_ORE);
		setResistance(Constants.RESISTANCE_ORE);
		setHarvestLevel("pickaxe", Constants.MINING_LEVEL_IRON);
		setCreativeTab(NuclearEngineering.NETab);
		setRegistryName(this.name);
		setUnlocalizedName(NuclearEngineering.MOD_ID + "." + this.name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
	}

	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		
		return new TileEntityRTG(20);
	}

	@Override
	public void registerItemModel(Item item)
	{
		NuclearEngineering.proxy.registerItemRenderer(item, 0, this.name);
		
	}

	public EnumBlockRenderType getRenderType(IBlockState state)
	{
			return EnumBlockRenderType.MODEL;
	}
	
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withProperty(FACING, mirrorIn.mirror((EnumFacing) state.getValue(FACING)));
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		switch(((EnumFacing) state.getValue(FACING)).getAxis())
		{
			case X:
			default:
				return EW_AABB;
			case Z:
				return NS_AABB;
			case Y:
				return VERTICAL_AABB;
		}
	}
	
	
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos.offset(facing.getOpposite()));

		if(iblockstate.getBlock() == Blocks.END_ROD)
		{
			EnumFacing enumfacing = (EnumFacing) iblockstate.getValue(FACING);

			if(enumfacing == facing)
			{
				return this.getDefaultState().withProperty(FACING, facing.getOpposite());
			}
		}

		return this.getDefaultState().withProperty(FACING, facing);
	}

	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState();
		iblockstate = iblockstate.withProperty(FACING, EnumFacing.getFront(meta));
		return iblockstate;
	}

	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
}
