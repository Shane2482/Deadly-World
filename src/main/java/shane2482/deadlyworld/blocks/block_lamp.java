package shane2482.deadlyworld.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.reflect.internal.Trees.New;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.blocks.base.IMetaBlockName;
import shane2482.deadlyworld.blocks.base.blockbase;
import shane2482.deadlyworld.blocks.base.itemblocks;
import shane2482.deadlyworld.library.Reference;

public class block_lamp extends Block implements IMetaBlockName{
	private static final PropertyBool isOn = PropertyBool.create("ison");
	public static final PropertyEnum TYPE = PropertyEnum.create("type", LampType.class);
	
	public block_lamp(Material material, String name, float hardness, float resistance, String tool,
			int level) {
		super(material);
		setSoundType(SoundType.GLASS);
		setDefaultState(blockState.getBaseState().withProperty(isOn, Boolean.valueOf(false)));
		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
		setHarvestLevel(tool, level);
		setHardness(hardness);
		setResistance(resistance);
		this.setCreativeTab(DeadlyWorld.block);
		
	}
	
	
	 @Override
	  protected BlockStateContainer createBlockState()
	  {
	    return new BlockStateContainer(this, new IProperty[] {isOn, TYPE});
	  }

	 @Override
		public int getMetaFromState(IBlockState state) {
			LampType type = (LampType) state.getValue(TYPE);
			return type.getMetadata();
		}
	 
	 @Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, LampType.values()[meta]);
	}
	 
	 @Override
		@SideOnly(Side.CLIENT)
		public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
			for(int i = 0; i < LampType.values().length; ++i){
				list.add(new ItemStack(itemIn, 1, i));
			}
		}
	 
	 
	 
	 
	 
	 
	 
	 @Override
		public String getSpecialName(ItemStack stack) {
			
			return LampType.values()[stack.getItemDamage()].getName();
		}
	 
	 
	 
	 
	 
	 
	 
	 
	


	

	

	protected void registerRendering() {
		DeadlyWorld.proxy.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public int damageDropped(IBlockState state) {
		LampType enumColour = (LampType) state.getValue(TYPE);
		blockState.getBaseState().withProperty(isOn, Boolean.valueOf(false));

		return enumColour.getMetadata();
	}

	

	
	
	@Override
	  public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing blockFaceClickedOn, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	  {
	    LampType colour = LampType.byMetadata(meta);
	    // find the quadrant the player is facing
	   return this.getDefaultState().withProperty(TYPE, colour);
	  }
	
	
	public static enum LampType implements IStringSerializable {
		BLUE(0, "blue"), RED(1, "red"), GREEN(2, "green"), YELLOW(3, "yellow");

		public int getMetadata() {
			return this.meta;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public static LampType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		public String getName() {
			return this.name;
		}

		private final int meta;
		private final String name;
		private static final LampType[] META_LOOKUP = new LampType[values().length];

		private LampType(int i_meta, String i_name) {
			this.meta = i_meta;
			this.name = i_name;
		}

		static {
			for (LampType colour : values()) {
				META_LOOKUP[colour.getMetadata()] = colour;
			}
		}
	}


	
}
