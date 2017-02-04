package shane2482.deadlyworld.library.crafting;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.reflect.internal.Trees.Return;
import shane2482.deadlyworld.init.ModItems;

public class AlloyRecipes {

	public AlloyRecipes() {

	}

	

	public static ItemStack getAlloyResult(Item item, Item item1) {
		return getOutput(item, item1);
	}

	private static ItemStack getOutput(Item item, Item item1) {

		
		if (item == ModItems.Ingot_Copper && item1 == ModItems.Ingot_Tin
				|| item == ModItems.Ingot_Tin && item1 == ModItems.Ingot_Copper) {
			return new ItemStack(ModItems.Ingot_Bronze, 2);
		}
		return null;

	}

}
