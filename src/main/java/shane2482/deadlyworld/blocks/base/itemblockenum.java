package shane2482.deadlyworld.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class itemblockenum extends ItemBlock {

	public itemblockenum(Block block) {
		super(block);
		if (!(block instanceof IMetaBlockName)) {
			throw new IllegalArgumentException(String.format("The given Block %s is not an instance of IMetaBlockName",	block.getUnlocalizedName()));
		}
		setMaxDamage(0);
		setHasSubtypes(true);

	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		return super.getUnlocalizedName() + "." + ((IMetaBlockName) block).getSpecialName(stack);
	}

}
