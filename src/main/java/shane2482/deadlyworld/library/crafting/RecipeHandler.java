package shane2482.deadlyworld.library.crafting;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import shane2482.deadlyworld.init.ModArmor;
import shane2482.deadlyworld.init.ModBlocks;
import shane2482.deadlyworld.init.ModItems;
import shane2482.deadlyworld.init.ModTools;

public class RecipeHandler {
	
	//Crafting Table
	public static void registerCraftingRecipes() {
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.Plywood), new ItemStack(ModItems.Wood_Dust,1,1), new ItemStack(ModItems.Wood_Dust), new ItemStack(ModItems.Wood_Dust), new ItemStack(ModItems.Wood_Dust));
		registerTools(ModTools.Basalt_Pickaxe, ModTools.Basalt_Axe, ModTools.Basalt_Hoe, ModTools.Basalt_Shovel, ModTools.Basalt_Sword, new ItemStack(ModBlocks.Basalt_Rough));
		registerArmourRecipe(ModArmor.Basalt_Helmet, ModArmor.Basalt_Chestplate, ModArmor.Basalt_Leggins, ModArmor.Basalt_Boots, new ItemStack(ModBlocks.Basalt_Rough));
		
	}

	//Furnace

	public static void registerSmeltingRecipes() {
		GameRegistry.addSmelting(new ItemStack(ModBlocks.Basalt_Rough), new ItemStack(ModBlocks.Basalt_Smooth), 0.7f);
		
	}

	//Tools&Armor

	private static void registerTools(Item pickaxe, Item axe, Item hoe, Item shovel, Item sword, ItemStack item) {
		GameRegistry.addRecipe(new ItemStack(pickaxe), new Object[] { "III", " S ", " S ", 'I', item, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(axe), new Object[] { " II", " SI", " S ", 'I', item, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(axe), new Object[] { "II ", "IS ", " S ", 'I', item, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(hoe), new Object[] { "II ", " S ", " S ", 'I', item, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(hoe), new Object[] { " II", " S ", " S ", 'I', item, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(shovel), new Object[] { " I ", " S ", " S ", 'I', item, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(shovel), new Object[] { "I  ", "S  ", "S  ", 'I', item, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(shovel), new Object[] { "  I", "  S", "  S", 'I', item, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(sword), new Object[] { " I ", " I ", " S ", 'I', item, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(sword), new Object[] { "I  ", "I  ", "S  ", 'I', item, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(sword), new Object[] { "  I", "  I", "  S", 'I', item, 'S', Items.STICK });
	}

	public static void registerArmourRecipe(Item helmet, Item chestplate, Item leggings, Item boots, ItemStack item) {
		GameRegistry.addRecipe(new ItemStack(helmet), new Object[] { "III", "I I", "   ", 'I', item });
		GameRegistry.addRecipe(new ItemStack(helmet), new Object[] { "   ", "III", "I I", 'I', item });
		GameRegistry.addRecipe(new ItemStack(chestplate), new Object[] { "I I", "III", "III", 'I', item });
		GameRegistry.addRecipe(new ItemStack(leggings), new Object[] { "III", "I I", "I I", 'I', item });
		GameRegistry.addRecipe(new ItemStack(boots), new Object[] { "I I", "I I", "   ", 'I', item });
		GameRegistry.addRecipe(new ItemStack(boots), new Object[] { "   ", "I I", "I I", 'I', item });
	}

}
