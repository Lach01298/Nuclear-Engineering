package lach_01298.nuclear_engineering.block.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;





import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.block.RegisterBlocks;
import lach_01298.nuclear_engineering.item.render.ItemModelProvider;
import lach_01298.nuclear_engineering.util.Constants;
import lach_01298.nuclear_engineering.world.radiation.ManagerRadiation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOre extends Block implements ItemModelProvider
{
    public static final PropertyEnum<BlockMetal.MetalType> TYPE = BlockMetal.TYPE;
    public static String name;
    
    public BlockOre(String name)
    {
        super(Material.ROCK);
        this.name=name;
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockMetal.MetalType.COPPER));
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setRegistryName(name);
		setCreativeTab(NuclearEngineering.NETab);
		setUnlocalizedName(NuclearEngineering.MOD_ID+"."+name);
		setHardness(Constants.HARDNESS_ORE);
		setResistance(Constants.RESISTANCE_ORE);
		
		
    }
    
    
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return  Item.getItemFromBlock(RegisterBlocks.ore);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).getMetadata();
    }
   
  
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for (BlockMetal.MetalType oreAmount : BlockMetal.MetalType.values())
        {
            list.add(new ItemStack(itemIn, 1, oreAmount.getMetadata()));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
    	return this.getDefaultState().withProperty(TYPE, BlockMetal.MetalType.META_LOOKUP[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((BlockMetal.MetalType)state.getValue(TYPE)).getMetadata();
    }

   
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE);
    }

   
    @Override
	public void registerItemModel(Item itemblock)
	{
    	
    	for (BlockMetal.MetalType oreAmount : BlockMetal.MetalType.values())
        {
    		NuclearEngineering.proxy.registerItemRenderer(itemblock,oreAmount.ordinal() , this.name+oreAmount.getName());
        }
	}
    
    
    public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		if(state.getValue(TYPE) == BlockMetal.MetalType.URANIUM)
		{
			ManagerRadiation radManager = ManagerRadiation.getManager(world);
			radManager.removeRadiationAt(pos, world);
		}
    	super.breakBlock(world, pos, state);
	}
	
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		if(state.getValue(TYPE) == BlockMetal.MetalType.URANIUM)
		{
			ManagerRadiation radManager = ManagerRadiation.getManager(world);
			radManager.addRadiationAt(pos, world, 800f, 400f, 800f, 0);
		}
	}
    
    
    
    
    

	
}