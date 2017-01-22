package shane2482.deadlyworld.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import shane2482.deadlyworld.init.ModItems;
import shane2482.deadlyworld.init.ModTools;

public class ToolBuilderCraftingManager {
	/** The static instance of this class */
	private static final ToolBuilderCraftingManager INSTANCE = new ToolBuilderCraftingManager();
	private List<ToolBuilderShapedRecipes> recipes = Lists.<ToolBuilderShapedRecipes>newArrayList();

	/**
	 * Returns the static instance of this class
	 */
	public static ToolBuilderCraftingManager getInstance() {
		/** The static instance of this class */
		return INSTANCE;
	}

	private ToolBuilderCraftingManager() {
		recipes = new ArrayList<ToolBuilderShapedRecipes>();
		GameRegistry.	addRecipe(new ItemStack(ModTools.Basalt_Pickaxe, 1), new Object[] { "s", "s", "s", 's',ModItems.Dirt_Clump });
		
		
	}

	/**
	 * Adds a shaped recipe to the games recipe list.
	 */
	public ToolBuilderShapedRecipes addRecipe(ItemStack stack, Object... recipeComponents) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;

		if (recipeComponents[i] instanceof String[]) {
			String[] astring = (String[]) ((String[]) recipeComponents[i++]);

			for (String s2 : astring) {
				++k;
				j = s2.length();
				s = s + s2;
			}
		} else {
			while (recipeComponents[i] instanceof String) {
				String s1 = (String) recipeComponents[i++];
				++k;
				j = s1.length();
				s = s + s1;
			}
		}

		Map<Character, ItemStack> map;

		for (map = Maps.<Character, ItemStack>newHashMap(); i < recipeComponents.length; i += 2) {
			Character character = (Character) recipeComponents[i];
			ItemStack itemstack = null;

			if (recipeComponents[i + 1] instanceof Item) {
				itemstack = new ItemStack((Item) recipeComponents[i + 1]);
			} else if (recipeComponents[i + 1] instanceof Block) {
				itemstack = new ItemStack((Block) recipeComponents[i + 1], 1, 32767);
			} else if (recipeComponents[i + 1] instanceof ItemStack) {
				itemstack = (ItemStack) recipeComponents[i + 1];
			}

			map.put(character, itemstack);
		}

		ItemStack[] aitemstack = new ItemStack[j * k];

		for (int l = 0; l < j * k; ++l) {
			char c0 = s.charAt(l);

			if (map.containsKey(Character.valueOf(c0))) {
				aitemstack[l] = ((ItemStack) map.get(Character.valueOf(c0))).copy();
			} else {
				aitemstack[l] = null;
			}
		}
		ToolBuilderShapedRecipes shapedrecipes = new ToolBuilderShapedRecipes(j, k, aitemstack, stack);
		this.recipes.add(shapedrecipes);
		return shapedrecipes;
	}

	/**
	

	/**
	 * Adds an IRecipe to the list of crafting recipes.
	 */
	public void addRecipe(IRecipe recipe) {
		this.recipes.add((ToolBuilderShapedRecipes) recipe);
	}

	/**
	 * Retrieves an ItemStack that has multiple recipes for it.
	 */
	@Nullable
	public ItemStack findMatchingRecipe(InventoryCrafting craftMatrix, World worldIn) {
		for (IRecipe irecipe : this.recipes) {
			if (irecipe.matches(craftMatrix, worldIn)) {
				return irecipe.getCraftingResult(craftMatrix);
			}
		}

		return null;
	}

	public ItemStack[] getRemainingItems(InventoryCrafting craftMatrix, World worldIn) {
		for (IRecipe irecipe : this.recipes) {
			if (irecipe.matches(craftMatrix, worldIn)) {
				return irecipe.getRemainingItems(craftMatrix);
			}
		}

		ItemStack[] aitemstack = new ItemStack[craftMatrix.getSizeInventory()];

		for (int i = 0; i < aitemstack.length; ++i) {
			aitemstack[i] = craftMatrix.getStackInSlot(i);
		}

		return aitemstack;
	}

	public List<ToolBuilderShapedRecipes> getRecipeList() {
		return this.recipes;
	}
}