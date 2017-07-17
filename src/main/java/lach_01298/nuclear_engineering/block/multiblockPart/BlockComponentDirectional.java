package lach_01298.nuclear_engineering.block.multiblockPart;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.util.Constants;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockComponentDirectional extends BlockMultiblockPart implements ItemModelProvider
{
	

		private String name;
		public static final PropertyDirection FACING = PropertyDirection.create("facing");
		public BlockComponentDirectional(String name, Material material)
		{
		
			super(material);
			this.name = name;
			setRegistryName(name);
			setCreativeTab(NuclearEngineering.NETab);
			setUnlocalizedName(NuclearEngineering.MOD_ID+"."+name);
			setHardness(Constants.HARDNESS_METAL);
			setResistance(Constants.RESISTANCE_METAL);
			this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
		}

		@Override
		public void registerItemModel(Item itemBlock)
		{
			NuclearEngineering.proxy.registerItemRenderer(itemBlock,0 , this.name);
		}

		public IBlockState withRotation(IBlockState state, Rotation rot)
		{
			return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
		}

		public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
		{
			return state.withProperty(FACING, mirrorIn.mirror((EnumFacing) state.getValue(FACING)));
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
		
		
}
