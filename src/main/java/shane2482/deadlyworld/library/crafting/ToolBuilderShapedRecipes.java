package shane2482.deadlyworld.library.crafting;

import javax.annotation.Nullable;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ToolBuilderShapedRecipes implements IRecipe {

	public final int recipeWidth;
	public final int recipeHeight;
	public final ItemStack[] recipeItems;
	private final ItemStack recipeOutput;
	private boolean copyIngredientNBT;

	public ToolBuilderShapedRecipes(int width, int height, ItemStack[] recipeItems, ItemStack output) {
		this.recipeWidth = width;
		this.recipeHeight = height;
		this.recipeItems = recipeItems;
		this.recipeOutput = output;
	}

	@Nullable
	public ItemStack getRecipeOutput() {
		return this.recipeOutput;
	}

	public ItemStack[] getRemainingItems(InventoryCrafting inventoryCraft) {
		ItemStack[] aitemstack = new ItemStack[inventoryCraft.getSizeInventory()];

		for (int i = 0; i < aitemstack.length; ++i) {
			ItemStack itemstack = inventoryCraft.getStackInSlot(i);
			aitemstack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
		}

		return aitemstack;
	}

	// Used to check if a recipe matches current crafting inventory
	public boolean matches(InventoryCrafting inventoryCraft, World world) {
		for (int y = 0; y <= 1 - this.recipeWidth; ++y) {
			for (int x = 0; x <= 3 - this.recipeHeight; ++x) {
				if (this.checkMatch(inventoryCraft, y, x, true)) {
					return true;
				}

				if (this.checkMatch(inventoryCraft, y, x, false)) {
					return true;
				}
			}
		}

		return false;
	}

	// Checks if the region of a crafting inventory is match for the recipe.
	private boolean checkMatch(InventoryCrafting inventoryCraft, int width, int height, boolean bool) {
		for (int y = 0; y < 1; ++y) {
			for (int x = 0; x < 3; ++x) {
				int k = y - width;
				int l = x - height;
				ItemStack itemstack = null;

				if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
					if (bool) {
						itemstack = this.recipeItems[this.recipeWidth - k - 1 + l * this.recipeWidth];
					} else {
						itemstack = this.recipeItems[k + l * this.recipeWidth];
					}
				}

				ItemStack itemstack1 = inventoryCraft.getStackInRowAndColumn(y, x);

				if (itemstack1 != null || itemstack != null) {
					if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null) {
						return false;
					}

					if (itemstack.getItem() != itemstack1.getItem()) {
						return false;
					}

					if (itemstack.getMetadata() != 32767 && itemstack.getMetadata() != itemstack1.getMetadata()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	// Returns an Item that is the result of this recipe
	@Nullable
	public ItemStack getCraftingResult(InventoryCrafting inventoryCraft) {
		ItemStack itemstack = this.getRecipeOutput().copy();

		if (this.copyIngredientNBT) {
			for (int i = 0; i < inventoryCraft.getSizeInventory(); ++i) {
				ItemStack itemstack1 = inventoryCraft.getStackInSlot(i);

				if (itemstack1 != null && itemstack1.hasTagCompound()) {
					itemstack.setTagCompound(itemstack1.getTagCompound().copy());
				}
			}
		}

		return itemstack;
	}

	// Returns the size of the recipe area
	public int getRecipeSize() {
		return this.recipeWidth * this.recipeHeight;
	}
}