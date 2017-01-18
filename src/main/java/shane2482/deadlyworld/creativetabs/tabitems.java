package shane2482.deadlyworld.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import shane2482.deadlyworld.init.ModItems;

public class tabitems extends CreativeTabs {

	public tabitems(int index, String label) {
		super(13, "dpitems");

	}

	@Override
	public Item getTabIconItem() {

		return ModItems.Circuits;
	}

}