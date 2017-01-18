package shane2482.deadlyworld.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import shane2482.deadlyworld.init.ModBlocks;

public class tabblocks extends CreativeTabs {

	public tabblocks(int index, String label) {
		super(12, "dpblocks");

	}

	@Override
	public Item getTabIconItem() {

		return Item.getItemFromBlock(ModBlocks.Basalt_Brick);
	}

}
