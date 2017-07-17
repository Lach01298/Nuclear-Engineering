package lach_01298.nuclear_engineering.block.multiblockPart;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.fluid.EnumTankType;
import lach_01298.nuclear_engineering.inventory.EnumSlotType;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityFluidHatch;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityItemHatch;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityPowerHatch;
import lach_01298.nuclear_engineering.util.Constants;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPowerHatch extends BlockMultiblockPart implements ItemModelProvider
{
	public static final PropertyEnum<BlockItemHatch.HatchType> TYPE =  BlockItemHatch.TYPE;
    public static String name;
	
	public BlockPowerHatch(String name)
	{
		 super(Material.IRON);
	        this.name=name;
	        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockItemHatch.HatchType.INPUT));
	        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	        setRegistryName(name);
			setCreativeTab(NuclearEngineering.NETab);
			setUnlocalizedName(NuclearEngineering.MOD_ID+"."+name);
			setHardness(Constants.HARDNESS_METAL);
			setResistance(Constants.RESISTANCE_METAL);
	}

	 @Nullable
	    public Item getItemDropped(IBlockState state, Random rand, int fortune)
	    {
	        return  Item.getItemFromBlock(RegisterBlocks.fluidHatch);
	    }

	    @Override
	    public int damageDropped(IBlockState state)
	    {
	        return state.getValue(TYPE).getMetadata();
	    }
	   
	  
	    @SideOnly(Side.CLIENT)
	    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	    {
	        for (BlockItemHatch.HatchType item : BlockItemHatch.HatchType.values())
	        {
	            list.add(new ItemStack(itemIn, 1, item.getMetadata()));
	        }
	    }

	    @Override
	    public IBlockState getStateFromMeta(int meta)
	    {
	    	return this.getDefaultState().withProperty(TYPE, BlockItemHatch.HatchType.META_LOOKUP[meta]);
	    }

	    @Override
	    public int getMetaFromState(IBlockState state)
	    {
	        return ((BlockItemHatch.HatchType)state.getValue(TYPE)).getMetadata();
	    }

	   
	    @Override
	    protected BlockStateContainer createBlockState()
	    {
	        return new BlockStateContainer(this, TYPE);
	    }

	   
	    @Override
		public void registerItemModel(Item itemblock)
		{
	    	
	    	for (BlockItemHatch.HatchType item : BlockItemHatch.HatchType.values())
	        {
	    		NuclearEngineering.proxy.registerItemRenderer(itemblock,item.ordinal() , this.name+item.getName());
	        }
		}
	    
	    
	    @Override
		public TileEntity createNewTileEntity(World worldIn, int meta)
		{
			if(meta == 0)
			{
				return new TileEntityPowerHatch(true);
			}
			else
			{
				return new TileEntityPowerHatch(false);
			}
			
		}


}