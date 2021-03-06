package shane2482.deadlyworld.blocks.ores;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.blocks.base.IMetaBlockName;
import shane2482.deadlyworld.blocks.base.itemblockenum;
import shane2482.deadlyworld.library.Reference;

public class block_ore extends Block implements IMetaBlockName {
	public static final PropertyEnum TYPE = PropertyEnum.create("type", oreType.class);

	public block_ore(String name, String Regname) {
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(Regname);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, oreType.Tin));
		this.setCreativeTab(DeadlyWorld.block);
		this.register();
		setHardness(5.0f);
		setResistance(20.0f);
		setHarvestLevel("pickaxe", 2);
	}

	public void register() {
		registerBlock(this, new itemblockenum(this));
		for (int i = 0; i < oreType.values().length; ++i) {
			registerRendering();
		}
	}

	protected void registerRendering() {
		for (int i = 0; i < oreType.values().length; i++) {
			DeadlyWorld.proxy.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(),
					TYPE.getName() + "=" + oreType.values()[i].name);
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
		oreType type = (oreType) state.getValue(TYPE);
		return type.ID;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, oreType.values()[meta]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < oreType.values().length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getSpecialName(ItemStack stack) {
		return oreType.values()[stack.getItemDamage()].getName();
	}

	public static enum oreType implements IStringSerializable {
		Tin("tin", 0, 5.0f, 20.0f, "pickaxe", 2), 
		Copper("copper", 1, 5.0f, 20.0f, "pickaxe", 2), 
		Aluminum("aluminum", 2, 5.0f, 20.0f, "pickaxe",	2), 
		Nickel("nickel", 3, 5.0f, 20.0f, "pickaxe", 2), 
		Tungsten("tungsten", 4, 5.0f, 20.0f, "pickaxe", 2);

		private String name;
		private int ID;
		private float hardness;
		private float resistance;
		private String tool;
		private int level;

		private oreType(String name, int ID, float hardness, float resistance, String tool, int level) {
			this.name = name;
			this.ID = ID;
			this.hardness = hardness;
			this.resistance = resistance;
			this.tool = tool;
			this.level = level;
		}

		public float getHardness() {
			return hardness;
		}

		public float getResistance() {
			return resistance;
		}

		public String getTool() {
			return tool;
		}

		public int getLevel() {
			return level;
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
