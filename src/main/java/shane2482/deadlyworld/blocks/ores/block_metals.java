package shane2482.deadlyworld.blocks.ores;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.blocks.base.IMetaBlockName;
import shane2482.deadlyworld.blocks.base.itemblockenum;

public class block_metals extends Block implements IMetaBlockName {
	public static final PropertyEnum TYPE = PropertyEnum.create("type", metalType.class);

	public block_metals(String name, String Regname) {
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(Regname);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, metalType.Tin));
		this.setCreativeTab(DeadlyWorld.block);
		this.register();
		setHardness(5.0f);
		setResistance(20.0f);
		setHarvestLevel("pickaxe", 1);
	}

	public void register() {
		registerBlock(this, new itemblockenum(this));
		for (int i = 0; i < metalType.values().length; ++i) {
			registerRendering();
		}
	}

    protected void registerRendering(){
        for(int i = 0; i < metalType.values().length; i++){
            DeadlyWorld.proxy.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(), TYPE.getName()+"="+ metalType.values()[i].name);
        }
    }

	public static void registerBlock(Block block, itemblockenum itemblock) {
		GameRegistry.register(block);
		GameRegistry.register(itemblock.setRegistryName(block.getRegistryName()));

	}
	
	
	@Override
	public int damageDropped(IBlockState state) {
	    return getMetaFromState(state);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		metalType type = (metalType) state.getValue(TYPE);
		return type.ID;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, metalType.values()[meta]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < metalType.values().length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getSpecialName(ItemStack stack) {
		return metalType.values()[stack.getItemDamage()].getName();
	}

	public static enum metalType implements IStringSerializable {
		//Ore Blocks
		Tin("tin", 0), 
		Copper("copper", 1), 
		Aluminum("aluminum", 2), 
		Nickel("nickel", 3),
		Tungsten("tungsten", 4),
		
		//Alloys
		Titanium("titanium", 5);

		private String name;
		private int ID;
		

		private metalType(String name, int ID) {
			this.name = name;
			this.ID = ID;
			
		}
		

		@Override
		public String getName() {
			return name;
		}

		public int getID() {
			return ID;
		}

		@Override
		public String toString() {
			return getName();
		}
		
	}
}
