package shane2482.deadlyworld.library.crafting;

import java.util.Comparator;

import net.minecraft.item.crafting.IRecipe;

public class ToolBuilderRecipeSorter implements Comparator {
	final ToolBuilderCraftingManager toolbuilder;

	public ToolBuilderRecipeSorter(ToolBuilderCraftingManager toolBuilderCraftingManager) {
		this.toolbuilder = toolBuilderCraftingManager;
	}

	public int compareRecipes(IRecipe irecipe1, IRecipe irecipe2) {
		return irecipe1 instanceof ToolBuilderShapelessRecipes && irecipe2 instanceof ToolBuilderShapedRecipes ? 1
				: (irecipe2 instanceof ToolBuilderShapelessRecipes && irecipe1 instanceof ToolBuilderShapedRecipes ? -1
						: (irecipe2.getRecipeSize() < irecipe1.getRecipeSize() ? -1
								: (irecipe2.getRecipeSize() > irecipe1.getRecipeSize() ? 1 : 0)));
	}

	@Override
	public int compare(Object o1, Object o2) {
		return this.compareRecipes((IRecipe) o1, (IRecipe) o2);
	}
}
