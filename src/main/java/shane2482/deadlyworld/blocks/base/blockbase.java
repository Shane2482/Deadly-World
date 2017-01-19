package shane2482.deadlyworld.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import shane2482.deadlyworld.DeadlyWorld;

public class blockbase extends Block {

	public blockbase(Material material, String name, String Regname, float hardness, float resistance, String tool, int level) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(Regname);
		setHarvestLevel(tool, level);
		setHardness(hardness);
		setResistance(resistance);
		this.setCreativeTab(DeadlyWorld.block);
		this.register();
	}

	
	private void register() {
		registerBlock(this, this.getItemBlocks());

		this.registerRendering();
	}

	public static void registerBlock(Block block, itemblocks itemBlock) {

		GameRegistry.register(block);
		itemBlock.setRegistryName(block.getRegistryName());
		GameRegistry.register(itemBlock);
	}

	

	protected itemblocks getItemBlocks() {
		return new itemblocks(this);
	}

	protected void registerRendering() {
		DeadlyWorld.proxy.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

}